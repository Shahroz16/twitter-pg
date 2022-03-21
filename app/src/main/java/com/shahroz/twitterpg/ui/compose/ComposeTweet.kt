package com.shahroz.twitterpg.ui.compose

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.himanshoe.pluck.PluckConfiguration
import com.himanshoe.pluck.theme.PluckDimens
import com.himanshoe.pluck.ui.Pluck
import com.himanshoe.pluck.ui.permission.Permission
import com.shahroz.twitterpg.data.model.Person
import com.shahroz.twitterpg.ui.component.profiles.ProfilePicture
import com.shahroz.twitterpg.ui.component.profiles.ProfilePictureSizes
import com.shahroz.twitterpg.ui.home.HomeViewModel
import com.shahroz.twitterpg.util.Keyboard
import com.shahroz.twitterpg.util.ModalBottonSheetKeyboardAsState
import twitter4j.User


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun ComposeTweet(
    user: Person,
    onCloseClicked: () -> Unit,
    onNewTweet: () -> Unit,
    goToSettings: () -> Unit,
    state: ModalBottomSheetState,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    var selectedImage by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        StatusView(
            image = user.image,
            state = state,
            selectedImage = selectedImage,
            onCloseClicked = onCloseClicked,
            sendTweet = { text, uri ->
                homeViewModel.sendTweet(text, uri, context)
                onNewTweet()
            },
            onImageRemoved = {
                selectedImage = null
            },
        )
        if (selectedImage == null) {
            AttachmentView(
                onImageSelected = {
                    selectedImage = it
                },
                goToSettings = goToSettings,
            )
        }
    }
}

@Composable
fun AttachmentView(
    modifier: Modifier = Modifier,
    onImageSelected: (Uri?) -> Unit,
    goToSettings: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(PluckDimens.Sixteen)
    ) {
        Permission(
            permissions = listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            goToAppSettings = goToSettings
        ) {
            Pluck(pluckConfiguration = PluckConfiguration(false),
                onPhotoSelected = {
                    val selectedImage = it.firstOrNull()?.uri
                    onImageSelected(selectedImage)
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatusView(
    modifier: Modifier = Modifier,
    image: String?,
    state: ModalBottomSheetState,
    selectedImage: Uri?,
    onCloseClicked: () -> Unit,
    sendTweet: (String, Uri?) -> Unit,
    onImageRemoved: () -> Unit,
) {
    var tweetText by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            CloseButton(onCloseClicked)
            TweetButton(tweetText, selectedImage) {
                runCatching {
                    sendTweet(tweetText, selectedImage)
                }
            }
        }
        AvatarWithTextField(
            image = image,
            state = state,
            onCloseClicked = onCloseClicked
        ) { tweet ->
            tweetText = tweet
        }
        if (selectedImage != null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Box {
                    Image(
                        painter = rememberImagePainter(selectedImage),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                            .height(160.dp)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Inside
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { onImageRemoved() }
                            .align(Alignment.TopEnd),
                        tint = MaterialTheme.colors.primary
                    )
                }

            }
        }
    }


}

const val tweetCharCount = 280

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AvatarWithTextField(
    image: String?,
    state: ModalBottomSheetState,
    onCloseClicked: () -> Unit,
    onTextChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val isKeyboardOpen by ModalBottonSheetKeyboardAsState(state)

    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        ProfilePicture(
            profileImageUrl = image,
            size = ProfilePictureSizes.small,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        BasicTextField(
            value = text,
            onValueChange = {
                if (it.length <= tweetCharCount) {
                    text = it
                    onTextChange.invoke(it)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { onCloseClicked() }),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = "What's happening?",
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Column() {
                        innerTextField()
                        Spacer(modifier = Modifier.height(16.dp))
                        BasicText(
                            text = "${text.length}/$tweetCharCount",
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            },
        )
    }

    if (state.currentValue == ModalBottomSheetValue.Expanded) {
        if (isKeyboardOpen == Keyboard.Closed) {
            onCloseClicked()
        } else {
            SideEffect {
                focusRequester.requestFocus()
            }
        }
    }

}


@Composable
private fun CloseButton(onCloseClicked: () -> Unit) {
    IconButton(onClick = onCloseClicked) {
        Icon(
            modifier = Modifier.size(32.dp),
            contentDescription = "",
            imageVector = Icons.Filled.Clear,
            tint = MaterialTheme.colors.primary
        )
    }
}

@Composable
private fun TweetButton(
    tweetText: String,
    selectedImage: Uri?,
    onNewTweet: () -> Unit
) {
    val canTweet = tweetText.isNotEmpty() || (selectedImage != null)
    val bgColor = if (canTweet) MaterialTheme.colors.primary else Color(0xFFAAAAAA)
    Button(
        contentPadding = PaddingValues(0.dp),
        onClick = onNewTweet,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = bgColor)

    ) {
        Text(text = "Tweet", color = Color.White, fontSize = 14.sp)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewComposeTweet() {
    ComposeTweet(
        user = Person(),
        onNewTweet = {},
        goToSettings = {},
        onCloseClicked = {},
        state = ModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)
    )
}
