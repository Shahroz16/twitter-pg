package com.shahroz.twitterpg.ui.component.tweets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun TweetImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    if (imageUrl != null) {
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Inside
        )
    }
}
