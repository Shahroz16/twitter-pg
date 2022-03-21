package com.shahroz.twitterpg.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.shahroz.twitterpg.R
import com.shahroz.twitterpg.data.model.Person
import com.shahroz.twitterpg.data.model.Tweet
import com.shahroz.twitterpg.ui.component.profiles.ProfilePicture
import com.shahroz.twitterpg.ui.component.profiles.ProfilePictureSizes
import com.shahroz.twitterpg.ui.component.tweets.TweetItem
import com.shahroz.twitterpg.ui.theme.twitterColor

@Composable
fun TwitterHome(
    modifier: Modifier = Modifier,
    user: Person,
    tweets: LazyPagingItems<Tweet>,
    onMessagesClick: () -> Unit,
    onRetweetClick: () -> Unit,
    onLikesClick: () -> Unit,
    onShareClick: () -> Unit,
    onNewTweetClicked: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_twitter),
                        contentDescription = null,
                        tint = twitterColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface,
                elevation = 8.dp,
                navigationIcon = {
                    ProfilePicture(
                        profileImageUrl = user.image,
                        size = ProfilePictureSizes.small,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                },
                actions = {
                    if (user.isValid()) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Tweet") },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_twitter),
                        contentDescription = null
                    )
                },
                onClick = onNewTweetClicked,
                contentColor = twitterColor
            )
        },
        content = {
            LazyColumn {
                items(tweets) {
                    it?.let { status ->
                        TweetItem(
                            status = status,
                            onMessagesClick = onMessagesClick,
                            onRetweetClick = onRetweetClick,
                            onLikesClick = onLikesClick,
                            onShareClick = onShareClick,
                        )
                    }
                }
            }
        }
    )

}

//@Preview
//@Composable
//fun ShowTwitterScreen() {
//    TwitterHome(
//        tweets = StubDataProvider.tweetList,
//        onMessagesClick = { /*TODO*/ },
//        onRetweetClick = { /*TODO*/ },
//        onLikesClick = { /*TODO*/ },
//        onShareClick = { /*TODO*/ },
//        onNewTweetClicked = { /*TODO*/ }
//    )
//}