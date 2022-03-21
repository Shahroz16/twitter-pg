package com.shahroz.twitterpg.data.repositories

import android.util.Log
import com.shahroz.twitterpg.data.mapper.TweetMapper
import com.shahroz.twitterpg.data.mapper.UserMapper
import com.shahroz.twitterpg.data.model.Person
import com.shahroz.twitterpg.data.model.Tweet
import twitter4j.Paging
import twitter4j.StatusUpdate
import twitter4j.Twitter
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken
import java.io.File
import javax.inject.Inject


interface TwitterRepository {
    suspend fun getTimeLine(page: Int): List<Tweet>
    fun isAuthenticated(): Boolean
    fun setAuthentication(token: String, secret: String)
    suspend fun verifyCredentials(): Person
    suspend fun getOAuthRequestToken(): RequestToken
    suspend fun getOAuthAccessToken(oauthVerifier: String): AccessToken
    suspend fun updateTextTweet(tweetText: String): Tweet
    suspend fun updateTextWithImageTweet(status: String, path: String): Tweet
}

class TwitterRepositoryImp @Inject constructor(
    val twitter: Twitter,
    val userMapper: UserMapper,
    val tweetMapper: TweetMapper
) : TwitterRepository {

    override suspend fun getTimeLine(page: Int): List<Tweet> {
        Log.v("paging", "$page")
        val paging = Paging(page)

        val result = kotlin.runCatching {
            twitter.getHomeTimeline(paging)
        }
        val list = if (result.isSuccess) {
            tweetMapper.toDomainList(result.getOrNull() ?: emptyList())
        } else {
            tweetMapper.toDomainList(emptyList())
        }
        return list
    }

    override fun isAuthenticated(): Boolean {
        return twitter.authorization.isEnabled
    }

    override fun setAuthentication(token: String, secret: String) {
        twitter.oAuthAccessToken = AccessToken(token, secret)
    }

    override suspend fun verifyCredentials(): Person {
        return userMapper.mapToDomainModel(twitter.verifyCredentials())

    }

    override suspend fun getOAuthRequestToken(): RequestToken {
        return twitter.oAuthRequestToken
    }

    override suspend fun getOAuthAccessToken(oauthVerifier: String): AccessToken {
        return twitter.getOAuthAccessToken(oauthVerifier)
    }

    override suspend fun updateTextTweet(tweetText: String): Tweet {
        return tweetMapper.mapToDomainModel(twitter.updateStatus(tweetText))
    }

    override suspend fun updateTextWithImageTweet(status: String, path: String): Tweet {
        val media = twitter.uploadMedia(File(path))
        val update = StatusUpdate(status)
        update.setMediaIds(media.mediaId)
        return tweetMapper.mapToDomainModel(twitter.updateStatus(update))
    }

}