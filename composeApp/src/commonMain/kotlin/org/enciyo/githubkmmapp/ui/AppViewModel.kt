package org.enciyo.githubkmmapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.enciyo.githubkmmapp.GithubScreen
import org.enciyo.githubkmmapp.TopLevelRoute
import org.enciyo.githubkmmapp.topLevelRoutes


class AppViewModel : ViewModel() {

    private val _sideEffect = MutableSharedFlow<SideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun onInteraction(interaction: Interaction) {
        when (interaction) {
            is Interaction.OnBottomItemClick -> {
                val currentSelectedIndex = state.value.selectedTabIndex
                if (currentSelectedIndex == interaction.index) {
                    _sideEffect.tryEmit(SideEffect.PopToRoot(topLevelRoutes[interaction.index]))
                    return
                }
                _state.update { it.copy(selectedTabIndex = interaction.index) }
                _sideEffect.tryEmit(SideEffect.NavigateToRoute(topLevelRoutes[interaction.index]))
            }

            is Interaction.Back -> {
                _sideEffect.tryEmit(SideEffect.PopBackStack)
            }
        }
    }

    sealed interface SideEffect {
        data class NavigateToRoute(val route: TopLevelRoute<GithubScreen>) : SideEffect
        data class PopToRoot(val route: TopLevelRoute<GithubScreen>) : SideEffect
        data object PopBackStack : SideEffect

    }

    sealed interface Interaction {
        data class OnBottomItemClick(val index: Int) : Interaction
        data object Back : Interaction
    }


    data class State(
        val selectedTabIndex: Int = 0,
        val startDestination: GithubScreen = GithubScreen.Home
    )

}