package org.enciyo.githubkmmapp.ui.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun DetailRoute(
    vm: DetailViewModel = koinViewModel(),
    navController: NavController
) {
    DetailContent()
}

@Composable
fun DetailContent() {

}

