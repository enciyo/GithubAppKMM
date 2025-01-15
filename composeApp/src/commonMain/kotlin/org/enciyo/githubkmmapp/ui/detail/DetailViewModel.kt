package org.enciyo.githubkmmapp.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.enciyo.githubkmmapp.GithubScreen
import org.enciyo.githubkmmapp.data.RemoteDataSource
import org.enciyo.githubkmmapp.data.model.UserItemResponse

class DetailViewModel(
    private val remoteDataSource: RemoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route by lazy { savedStateHandle.toRoute<GithubScreen.Detail>() }

    private val _state = MutableStateFlow(State())
    val state = _state
        .onStart {
            fetchDetail(route.username)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = State()
        )


    private val _sideEffect = MutableSharedFlow<SideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()


    fun onInteraction(interaction: Interaction) {
        when (interaction) {
            is Interaction.GoToProfile -> {
                _sideEffect.tryEmit(SideEffect.NavigateToWeb(interaction.link))
            }
        }
    }


    private fun fetchDetail(username: String) {
        viewModelScope.launch {
            remoteDataSource.userDetail(username)
                .onSuccess(::onHandleSuccess)
                .onFailure {
                    throw it
                }
        }

    }

    private fun onHandleSuccess(detail: UserItemResponse) {
        _state.update {
            it.copy(
                image = detail.avatarUrl,
                name = detail.name,
                link = detail.htmlUrl,
                bio = detail.bio,
                company = detail.company
            )
        }
    }


    data class State(
        val image: String = "",
        val name: String = "",
        val link: String = "",
        val bio: String = "",
        val company: String = ""
    )


    sealed interface SideEffect {
        data class NavigateToWeb(val url: String) : SideEffect
    }

    sealed interface Interaction {
        data class GoToProfile(val link: String) : Interaction
    }

}