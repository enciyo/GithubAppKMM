package org.enciyo.githubkmmapp.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.ktor.client.*
import kotlinx.coroutines.Dispatchers
import org.enciyo.githubkmmapp.HandleSideEffect
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun DetailRoute(
    vm: DetailViewModel = koinViewModel(),
    navController: NavController
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val localUriHandler = LocalUriHandler.current

    HandleSideEffect(vm.sideEffect) {
        when (it) {
            is DetailViewModel.SideEffect.NavigateToWeb -> {
                localUriHandler.openUri(it.url)
            }
        }
    }

    DetailContent(
        state = state,
        onInteractions = vm::onInteraction,
    )
}

@Composable
fun DetailContent(
    state: DetailViewModel.State,
    onInteractions: (DetailViewModel.Interaction) -> Unit
) {
    Box {
        Column {
            CircleAvatarImage(state.image)
            Header(
                state.name,
                state.link,
                state.bio,
                state.company,
                onInteractions
            )
        }
    }
}


@Composable
fun CircleAvatarImage(
    imageUrl: String,
) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .coroutineContext(Dispatchers.Default)
            .build()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.clip(CircleShape)
                .size(180.dp)

        )
    }
}

@Composable
fun Header(
    name: String,
    link: String,
    bio: String,
    company: String,
    onInteractions: (DetailViewModel.Interaction) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h5
        )
        Text(
            text = "Open With Browser",
            color = MaterialTheme.colors.primary,
            modifier = Modifier.clickable(onClick = {
                onInteractions(DetailViewModel.Interaction.GoToProfile(link))
            })
        )
        Text(
            text = bio,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = company,
            style = MaterialTheme.typography.body2
        )
    }
}

