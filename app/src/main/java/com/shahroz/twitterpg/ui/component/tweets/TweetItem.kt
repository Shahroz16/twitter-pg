package com.shahroz.twitterpg.ui.component.tweets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guru.composecookbook.twitter.components.icons.IconCounterBar
import com.shahroz.twitterpg.extensions.toTimeAgo
import com.shahroz.twitterpg.ui.component.profiles.ProfileInfo
import com.shahroz.twitterpg.ui.component.profiles.ProfilePicture
import com.shahroz.twitterpg.ui.component.profiles.ProfilePictureSizes
import twitter4j.Status

@Composable
fun TweetItem(
    status: Status,
    onMessagesClick: () -> Unit,
    onRetweetClick: () -> Unit,
    onLikesClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(8.dp)) {
        ProfilePicture(
            profileImageUrl = status.user.profileImageURL,
            size = ProfilePictureSizes.medium
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
        ) {
            ProfileInfo(
                profileName = status.user.name,
                profilePing = status.user.screenName,
                time = status.createdAt.time.toTimeAgo()
            )
            Text(text = status.text, style = typography.body1)
            TweetImage(
                imageUrl = status.mediaEntities.firstOrNull()?.mediaURL,
                modifier = Modifier.padding(top = 8.dp)
            )
            IconCounterBar(
                commentCount = status.contributors.size,
                retweetCount = status.retweetCount,
                likesCount = status.favoriteCount,
                onMessagesClick = onMessagesClick,
                onRetweetClick = onRetweetClick,
                onLikesClick = onLikesClick,
                onShareClick = onShareClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            )
            Divider(thickness = 0.5.dp)
        }
    }
}

//@Preview
//@Composable
//fun PreviewTwitterItem() {
//    val tweet = StubDataProvider.tweet
//    TweetItem(
//        tweet = tweet,
//        onMessagesClick = { /*TODO*/ },
//        onRetweetClick = { /*TODO*/ },
//        onLikesClick = { /*TODO*/ },
//        onShareClick = { /*TODO*/ },
//    )
//}
