package org.enciyo.githubkmmapp

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import org.enciyo.githubkmmapp.ui.App

fun main() = application {
    val state = rememberWindowState(
        width = 428.dp,
        height = 926.dp,
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
        state = state,
    ) {
        App()
    }
}