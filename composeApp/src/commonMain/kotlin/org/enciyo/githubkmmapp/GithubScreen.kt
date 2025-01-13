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
    data object Detail : GithubScreen(String.Empty)


}

fun NavBackStackEntry.toGithubScreen(): GithubScreen? {
    val routeMap = mapOf(
        GithubScreen.Home::class to GithubScreen.Home,
        GithubScreen.Favorite::class to GithubScreen.Favorite,
        GithubScreen.Profile::class to GithubScreen.Profile,
        GithubScreen.Search::class to GithubScreen.Search,
        GithubScreen.Detail::class to GithubScreen.Detail
    )

    return routeMap.entries.first { destination.hasRoute(it.key) }.value
}


val String.Companion.Empty: String
    get() = ""