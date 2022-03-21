package com.shahroz.twitterpg.ui.component.profiles

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun ProfilePicture(
    profileImageUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = ProfilePictureSizes.small
) {
    Image(
        painter = rememberImagePainter(profileImageUrl),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
    )
}

object ProfilePictureSizes {
    val small = 32.dp
    val medium = 50.dp
}
