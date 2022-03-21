package com.shahroz.twitterpg

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.shahroz.twitterpg.data.model.Person
import com.shahroz.twitterpg.data.model.Tweet
import com.shahroz.twitterpg.ui.component.TwitterHome
import com.shahroz.twitterpg.ui.compose.ComposeTweet
import com.shahroz.twitterpg.ui.home.HomeViewModel
import com.shahroz.twitterpg.ui.theme.TwitterPGTheme
import com.shahroz.twitterpg.util.TwitterWebClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            homeViewModel.isLoggedInStateFlow.collect { isLoggedInUser ->
                setContent {
                    val isLoggedIn = isLoggedInUser.isValid()
                    TwitterPGTheme {
                        TwitterScreen(
                            tweetsFlow = if (isLoggedIn) homeViewModel.tweets else flowOf(),
                            userState = isLoggedInUser
                        )
                    }
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            homeViewModel.displayTwitterLogin.collect { url ->
                url?.let {
                    TwitterWebClient(this@MainActivity, it) { verifier ->
                        homeViewModel.getOAuthAccessToken(verifier)
                    }
                }
            }
        }

    }

    @OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
    @Composable
    fun TwitterScreen(
        tweetsFlow: Flow<PagingData<Tweet>>,
        userState: Person,
    ) {
        val modalBottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

        val tweets = tweetsFlow.collectAsLazyPagingItems()
        val scope = rememberCoroutineScope()
        val keyboardController = LocalSoftwareKeyboardController.current

        if (modalBottomSheetState.currentValue == ModalBottomSheetValue.Expanded) {
            keyboardController?.show()
        } else {
            keyboardController?.hide()

        }

        BackHandler(enabled = modalBottomSheetState.isVisible) {
            scope.launch {
                modalBottomSheetState.animateTo(targetValue = ModalBottomSheetValue.Hidden)
            }
        }

        ModalBottomSheetLayout(sheetContent = {
            ComposeTweet(
                user = userState,
                onCloseClicked = {
                    scope.launch {
                        modalBottomSheetState.animateTo(targetValue = ModalBottomSheetValue.Hidden)
                    }
                },
                onNewTweet = {
                    scope.launch {
                        modalBottomSheetState.animateTo(targetValue = ModalBottomSheetValue.Hidden)
                    }
                    homeViewModel.refresh {
                        tweets.refresh()
                    }

                },
                goToSettings = {
                    goToAppSettings()
                },
                state = modalBottomSheetState
            )
        }, sheetState = modalBottomSheetState) {
            TwitterHome(
                user = userState,
                tweets = tweets,
                onMessagesClick = { /*TODO*/ },
                onRetweetClick = { /*TODO*/ },
                onLikesClick = { /*TODO*/ },
                onShareClick = { /*TODO*/ },
                onNewTweetClicked = {
                    scope.launch {
                        modalBottomSheetState.animateTo(targetValue = ModalBottomSheetValue.Expanded)
                    }
                }
            )
        }
    }

    private fun goToAppSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

}