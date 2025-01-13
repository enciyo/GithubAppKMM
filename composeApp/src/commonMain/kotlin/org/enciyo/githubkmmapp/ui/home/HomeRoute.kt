package org.enciyo.githubkmmapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.enciyo.githubkmmapp.GithubScreen
import org.enciyo.githubkmmapp.HandleSideEffect
import org.enciyo.githubkmmapp.composeResources.Res
import org.enciyo.githubkmmapp.composeResources.github_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun HomeRoute(
    vm: HomeViewModel = koinViewModel(),
    navController: NavController
) {

    HandleSideEffect(vm.sideEffect) {
        when (it) {
            is HomeViewModel.SideEffect.NavigateToSearch -> navController.navigate(GithubScreen.Search)
        }
    }

    HomeContent(interactions = vm::onInteraction)
}


@Composable
fun HomeContent(interactions: (HomeViewModel.Interaction) -> Unit = {}) {
    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
                .align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(Res.drawable.github_logo),
                contentDescription = "Github Logo",
                modifier = Modifier.size(120.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
            )
            Spacer(Modifier.height(4.dp))
            DummySearchView(interactions = interactions)
        }
    }
}

@Preview
@Composable
fun DummySearchView(
    modifier: Modifier = Modifier,
    interactions: (HomeViewModel.Interaction) -> Unit = {}
) {
    val onInteractions by rememberUpdatedState(interactions)
    val tint = MaterialTheme.colors.onPrimary

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .clickable(onClick = {
                onInteractions(HomeViewModel.Interaction.OnSearchClick)
            })
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = tint
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Search",
                maxLines = 1,
                color = tint
            )
        }
    }

}
