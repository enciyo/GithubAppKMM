package org.enciyo.githubkmmapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.enciyo.githubkmmapp.data.RemoteDataSource

class HomeViewModel() : ViewModel() {

    private val _sideEffect = MutableSharedFlow<SideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()


    fun onInteraction(interaction: Interaction) {
        when (interaction) {
            Interaction.OnSearchClick -> _sideEffect.tryEmit(SideEffect.NavigateToSearch)
        }
    }

    sealed interface Interaction {
        data object OnSearchClick : Interaction
    }

    sealed interface SideEffect {
        data object NavigateToSearch : SideEffect
    }

}