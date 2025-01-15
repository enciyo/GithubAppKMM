@file:OptIn(ExperimentalMaterialApi::class)

package org.enciyo.githubkmmapp.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.enciyo.githubkmmapp.GithubScreen
import org.enciyo.githubkmmapp.HandleSideEffect
import org.enciyo.githubkmmapp.composeResources.Res
import org.enciyo.githubkmmapp.composeResources.vc_arrow_insert
import org.enciyo.githubkmmapp.composeResources.vc_schedule
import org.enciyo.githubkmmapp.data.model.SearchItemResponse
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SearchRoute(
    vm: SearchViewModel = koinViewModel(),
    navController: NavController
) {
    val state by vm.state.collectAsStateWithLifecycle()

    HandleSideEffect(vm.sideEffect) {
        when (it) {
            is SearchViewModel.SideEffect.Back -> navController.popBackStack()
            is SearchViewModel.SideEffect.NavigateToDetail -> navController.navigate(GithubScreen.Detail(it.result))
        }
    }

    SearchContent(
        onInteractions = vm::onInteraction,
        state = state
    )
}

@Composable
fun SearchContent(
    onInteractions: (SearchViewModel.Interaction) -> Unit = {},
    state: SearchViewModel.SearchState
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            SearchView(
                search = state.keyword,
                onInteractions = onInteractions
            )
            AnimatedVisibility(visible = state.error.isNotEmpty()) {
                ErrorView(message = state.error)
            }
            AnimatedVisibility(visible = state.isLoading) {
                HorizontalProgressLoading()
            }
            SearchResults(results = state.result, onInteractions = onInteractions)
        }
    }
}

@Composable
fun HorizontalProgressLoading(
    modifier: Modifier = Modifier
) {
    LinearProgressIndicator(
        color = colors.primary,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    message: String
) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(colors.error, shape = RoundedCornerShape(12.dp))
            .padding(4.dp)
    ) {
        Text(
            text = message,
            color = colors.onError,
            style = typography.body2
        )
    }
}


@Composable
@Preview
fun SearchView(
    modifier: Modifier = Modifier,
    search: String = "",
    onInteractions: (SearchViewModel.Interaction) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(modifier = modifier.background(colors.primary)) {
        TextField(
            value = search,
            onValueChange = {
                onInteractions(SearchViewModel.Interaction.OnSearch(it))
            },
            placeholder = {
                Text(
                    "Search on Github",
                    color = colors.onPrimary.copy(alpha = 0.9f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Search",
                    tint = colors.onPrimary,
                    modifier = Modifier.clickable {
                        onInteractions(SearchViewModel.Interaction.OnBack)
                    }
                )
            },
            trailingIcon = {
                if (search.isNotEmpty())
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Search",
                        tint = colors.onPrimary,
                        modifier = Modifier.clickable {
                            onInteractions(SearchViewModel.Interaction.OnClear)
                        }
                    )
            },
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colors.onPrimary,
                cursorColor = colors.onPrimary,
                focusedIndicatorColor = colors.onPrimary,
                unfocusedIndicatorColor = colors.onPrimary
            )
        )
    }
}


@Composable
fun SearchResults(
    results: List<SearchItemResponse>,
    onInteractions: (SearchViewModel.Interaction) -> Unit,
) {
    LazyColumn {
        items(results) {
            SearchResultItem(result = it, onInteractions = onInteractions)
        }
    }
}

@Composable
fun SearchResultItem(
    result: SearchItemResponse,
    onInteractions: (SearchViewModel.Interaction) -> Unit
) {
    ListItem(
        text = { Text(text = result.login, color = colors.primary) },
        icon = {
            Icon(
                painter = painterResource(Res.drawable.vc_schedule),
                contentDescription = "Search",
                tint = colors.primary
            )
        },
        trailing = {
            Icon(
                painter = painterResource(Res.drawable.vc_arrow_insert),
                contentDescription = "Search",
                tint = colors.primary
            )
        },
        modifier = Modifier.clickable {
            onInteractions(SearchViewModel.Interaction.OnResultClick(result))
        }
    )
}