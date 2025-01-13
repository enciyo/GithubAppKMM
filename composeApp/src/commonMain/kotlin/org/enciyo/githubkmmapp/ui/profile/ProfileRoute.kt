package org.enciyo.githubkmmapp.ui.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ProfileRoute(
    vm: ProfileViewModel = koinViewModel(),
    navController: NavController
) {
    ProfileContent()
}

@Composable
fun ProfileContent() {

}

