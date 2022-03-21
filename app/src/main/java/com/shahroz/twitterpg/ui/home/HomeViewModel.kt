package com.shahroz.twitterpg.ui.home

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahroz.twitterpg.data.repositories.PreferenceRepository
import com.shahroz.twitterpg.data.repositories.TwitterRepository
import com.shahroz.twitterpg.data.source.TimeLineDateSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import twitter4j.Status
import twitter4j.User
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
    private val twitterRepository: TwitterRepository
) : ViewModel() {

    private var timeLineDateSource: TimeLineDateSource = TimeLineDateSource(twitterRepository)

    val tweets: Flow<PagingData<Status>> = Pager(PagingConfig(pageSize = 10)) {
        timeLineDateSource
    }.flow.cachedIn(viewModelScope)

    private val _isLoggedInStateFlow = MutableStateFlow<User?>(null)
    val isLoggedInStateFlow = _isLoggedInStateFlow

    private val _displayTwitterLogin = MutableStateFlow<String?>(null)
    val displayTwitterLogin = _displayTwitterLogin

    init {
        viewModelScope.launch {
            preferenceRepository.getAccessTokens()
                .collect { pair ->
                    val token = pair.first
                    val secret = pair.second
                    val user = getLoggedInUser(
                        token = token,
                        secret = secret
                    )
                    if (user != null) {
                        _isLoggedInStateFlow.value = user
                    } else {
                        oAuthRequestToken()
                    }
                }
        }
    }

    private suspend fun getLoggedInUser(token: String?, secret: String?): User? {
        if (token == null || secret == null) return null
        if (!twitterRepository.isAuthenticated()) {
            twitterRepository.setAuthentication(token, secret)
        }
        return try {
            val user = withContext(Dispatchers.IO) {
                twitterRepository.verifyCredentials()
            }
            user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private suspend fun oAuthRequestToken() = withContext(Dispatchers.IO) {
        val requestToken = twitterRepository.getOAuthRequestToken()
        _displayTwitterLogin.value = requestToken.authorizationURL
    }

    fun getOAuthAccessToken(oauthVerifier: String) {
        viewModelScope.launch {
            val accessToken =
                withContext(Dispatchers.IO) { twitterRepository.getOAuthAccessToken(oauthVerifier) }
            preferenceRepository.saveTokens(
                accessToken = accessToken.token,
                secret = accessToken.tokenSecret
            )
        }
    }

    fun sendTweet(tweetText: String, uri: Uri?, context: Context): Status {
        return runBlocking(Dispatchers.IO) {
            if (uri == null) {
                twitterRepository.updateTextTweet(tweetText)
            } else {
                val returnCursor: Cursor? =
                    context.contentResolver.query(uri, null, null, null, null)
                val columnIndex = returnCursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                returnCursor?.moveToFirst();
                val path = columnIndex?.let { returnCursor.getString(it) } ?: uri.path ?: ""

                twitterRepository.updateTextWithImageTweet(tweetText, path)
            }
        }
    }

    fun refresh(refresh: () -> Unit) {
        timeLineDateSource = TimeLineDateSource(twitterRepository = twitterRepository)
        refresh()
    }
}