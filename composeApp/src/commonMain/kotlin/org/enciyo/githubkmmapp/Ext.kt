package org.enciyo.githubkmmapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil3.compose.LocalPlatformContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun <T> HandleSideEffect(
    sideEffectFlow: Flow<T>,
    onSideEffect: (T) -> Unit
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = sideEffectFlow, key2 = lifecycleOwner) {
        sideEffectFlow
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .onEach { sideEffect ->
                onSideEffect(sideEffect)
            }
            .launchIn(this)
    }
}


fun NavController.navigateTopLevelRoute(topLevelRoute: TopLevelRoute<*>) {
    navigate(topLevelRoute.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
