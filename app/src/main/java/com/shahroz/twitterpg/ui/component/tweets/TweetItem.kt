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
import com.shahroz.twitterpg.data.model.Tweet
import com.shahroz.twitterpg.extensions.toTimeAgo
import com.shahroz.twitterpg.ui.component.profiles.ProfileInfo
import com.shahroz.twitterpg.ui.component.profiles.ProfilePicture
import com.shahroz.twitterpg.ui.component.profiles.ProfilePictureSizes

@Composable
fun TweetItem(
    status: Tweet,
    onMessagesClick: () -> Unit,
    onRetweetClick: () -> Unit,
    onLikesClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(8.dp)) {
        ProfilePicture(
            profileImageUrl = status.authorImageId,
            size = ProfilePictureSizes.medium
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
        ) {
            ProfileInfo(
                profileName = status.author,
                isVerified = status.isAuthorVerified,
                handle = status.handle,
                time = status.createdAt.time.toTimeAgo()
            )
            Text(text = status.text, style = typography.body1)
            TweetImage(
                imageUrl = status.tweetImages.firstOrNull(),
                modifier = Modifier.padding(top = 8.dp)
            )
            IconCounterBar(
                commentCount = status.commentsCount,
                retweetCount = status.retweetCount,
                likesCount = status.likesCount,
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

