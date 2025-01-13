package org.enciyo.githubkmmapp.ui.favorite

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun FavoriteRoute(
    vm: FavoriteViewModel = koinViewModel(),
    navController: NavController,
) {
    FavoriteContent()
}

@Composable
fun FavoriteContent() {
    //
}

