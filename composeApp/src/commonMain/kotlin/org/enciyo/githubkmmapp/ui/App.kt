package org.enciyo.githubkmmapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.enciyo.githubkmmapp.*
import org.enciyo.githubkmmapp.di.NetworkModules
import org.enciyo.githubkmmapp.ui.detail.DetailRoute
import org.enciyo.githubkmmapp.di.ViewModelModules
import org.enciyo.githubkmmapp.ui.favorite.FavoriteRoute
import org.enciyo.githubkmmapp.ui.home.HomeRoute
import org.enciyo.githubkmmapp.ui.profile.ProfileRoute
import org.enciyo.githubkmmapp.ui.search.SearchRoute
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinApplication(application = { modules(ViewModelModules, NetworkModules) }) {
            NavApp()
        }
    }
}

@Composable
fun NavApp(
    vm: AppViewModel = koinViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val route = currentBackStackEntry?.toGithubScreen() ?: GithubScreen.Home

    HandleSideEffect(vm.sideEffect) {
        when (it) {
            is AppViewModel.SideEffect.NavigateToRoute ->
                navController.navigateTopLevelRoute(it.route)

            is AppViewModel.SideEffect.PopToRoot ->
                navController.popBackStack(it.route.route, false)

            is AppViewModel.SideEffect.PopBackStack ->
                navController.popBackStack()
        }
    }


    Scaffold(
        topBar = {
            if (route.title.isNotEmpty()) {
                TopBarContent(
                    title = route.title,
                    isShowBackButton = navController.previousBackStackEntry != null && topLevelRoutes.none { it.route == route },
                    onInteraction = vm::onInteraction
                )
            }
        },
        bottomBar = {
            BottomNavigationContent(state.selectedTabIndex, vm::onInteraction)
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = state.startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<GithubScreen.Home> {
                HomeRoute(navController = navController)
            }
            composable<GithubScreen.Search> {
                SearchRoute(navController = navController)
            }

            composable<GithubScreen.Favorite> {
                FavoriteRoute(navController = navController)
            }

            composable<GithubScreen.Profile> {
                ProfileRoute(navController = navController)
            }

            composable<GithubScreen.Detail> {
                DetailRoute(navController = navController)
            }
        }
    }
}

@Composable
fun TopBarContent(
    title: String,
    isShowBackButton: Boolean,
    onInteraction: (AppViewModel.Interaction) -> Unit
) {
    val w by remember(isShowBackButton, title) { mutableStateOf(title) }

    TopAppBar(
        title = { Text(text = w) },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = if (isShowBackButton) {
            {
                IconButton(onClick = { onInteraction(AppViewModel.Interaction.Back) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        } else null
    )
}


@Composable
fun BottomNavigationContent(
    selectedTabIndex: Int = 0,
    onInteraction: (AppViewModel.Interaction) -> Unit
) {
    BottomNavigation() {
        topLevelRoutes.forEachIndexed { index, topLevelRoute ->
            BottomNavigationItem(
                icon = { Icon(topLevelRoute.icon, contentDescription = topLevelRoute.name) },
                label = { Text(topLevelRoute.name) },
                selected = selectedTabIndex == index,
                onClick = { onInteraction(AppViewModel.Interaction.OnBottomItemClick(index)) },

                )
        }
    }
}

