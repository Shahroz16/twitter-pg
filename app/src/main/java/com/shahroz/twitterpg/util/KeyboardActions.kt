package com.shahroz.twitterpg.util

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView

enum class Keyboard {
    Opened, Closed, Default
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottonSheetKeyboardAsState(
    state: ModalBottomSheetState
): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Default) }
    val view = LocalView.current
    if (state.currentValue != ModalBottomSheetValue.Expanded) {
        keyboardState.value = Keyboard.Default
    }
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (state.currentValue == ModalBottomSheetValue.Expanded) {
                keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                    Keyboard.Opened
                } else {
                    Keyboard.Closed
                }
            } else {
                keyboardState.value = Keyboard.Default
            }

        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}