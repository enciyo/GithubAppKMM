package org.enciyo.githubkmmapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)


val topLevelRoutes = listOf(
    TopLevelRoute<GithubScreen>("Home", GithubScreen.Home, Icons.Filled.Home),
    TopLevelRoute<GithubScreen>("Favorites", GithubScreen.Favorite, Icons.Filled.Favorite),
    TopLevelRoute<GithubScreen>("Me", GithubScreen.Profile, Icons.Filled.Person),
)
