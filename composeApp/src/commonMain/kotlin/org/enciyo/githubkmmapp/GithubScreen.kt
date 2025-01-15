package org.enciyo.githubkmmapp

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable


@Serializable
sealed class GithubScreen(val title: String) {

    @Serializable
    data object Home : GithubScreen("Home")

    @Serializable
    data object Favorite : GithubScreen("Favorite")

    @Serializable
    data object Profile : GithubScreen("Profile")

    @Serializable
    data object Search : GithubScreen(String.Empty)

    @Serializable
    data class Detail(val username: String) : GithubScreen(username)


}

fun NavBackStackEntry.toGithubScreen(): GithubScreen? {
    return when {
        destination.hasRoute<GithubScreen.Home>() -> toRoute<GithubScreen.Home>()
        destination.hasRoute<GithubScreen.Favorite>() -> toRoute<GithubScreen.Favorite>()
        destination.hasRoute<GithubScreen.Profile>() -> toRoute<GithubScreen.Profile>()
        destination.hasRoute<GithubScreen.Search>() -> toRoute<GithubScreen.Search>()
        destination.hasRoute<GithubScreen.Detail>() -> toRoute<GithubScreen.Detail>()
        else -> null
    }
}


val String.Companion.Empty: String
    get() = ""