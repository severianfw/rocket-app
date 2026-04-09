package com.severianfw.rocketapp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.severianfw.rocketapp.domain.model.Rocket
import com.severianfw.rocketapp.R
import com.severianfw.rocketapp.ui.state.RocketUiState
import com.severianfw.rocketapp.ui.viewmodel.RocketViewModel
import com.severianfw.rocketapp.ui.theme.RocketAppTheme
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RocketListScreen(
    viewModel: RocketViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onRocketClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    RocketListContent(
        state = state,
        searchQuery = searchQuery,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onRocketClick = onRocketClick,
        onRetry = viewModel::getRockets,
        modifier = modifier
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RocketListContent(
    state: RocketUiState,
    searchQuery: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onSearchQueryChange: (String) -> Unit,
    onRocketClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Rocket List",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = {
                Text(
                    text = "Input rocket name...",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                focusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f)
            )
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            when (state) {
                is RocketUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is RocketUiState.Success -> {
                    if (state.rockets.isEmpty()) {
                        Text(
                            text = "No rockets found",
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                items = state.rockets,
                                key = { it.id }
                            ) { rocket ->
                                RocketItem(
                                    rocket = rocket,
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    onClick = { onRocketClick(rocket.id) }
                                )
                            }
                        }
                    }
                }

                is RocketUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = onRetry) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun RocketItem(
    rocket: Rocket,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: () -> Unit
) {
    val imageModel = rocket.image.ifBlank { R.drawable.rocket_list_placeholder }

    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.tertiary,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .sharedElement(
                            state = rememberSharedContentState(key = "image/${rocket.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    model = imageModel,
                    loading = placeholder(R.drawable.rocket_list_placeholder),
                    failure = placeholder(R.drawable.rocket_list_placeholder),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Column(modifier = Modifier.weight(1F)) {
                    Text(
                        text = rocket.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "name/${rocket.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = rocket.fullName,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun RocketListContentSuccessPreview() {
    RocketAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    RocketListContent(
                        state = RocketUiState.Success(
                            rockets = listOf(
                                Rocket(
                                    1,
                                    "Falcon 1",
                                    "The first orbital rocket built by SpaceX.",
                                    "",
                                    "",
                                    "",
                                    ""
                                ),
                                Rocket(
                                    2,
                                    "Falcon 9",
                                    "A reusable, two-stage rocket designed and manufactured by SpaceX.",
                                    "",
                                    "",
                                    "",
                                    ""
                                ),
                            )
                        ),
                        searchQuery = "",
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedVisibility,
                        onSearchQueryChange = {},
                        onRocketClick = {},
                        onRetry = {}
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun RocketListContentLoadingPreview() {
    RocketAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    RocketListContent(
                        state = RocketUiState.Loading,
                        searchQuery = "",
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedVisibility,
                        onSearchQueryChange = {},
                        onRocketClick = {},
                        onRetry = {}
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun RocketListContentErrorPreview() {
    RocketAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    RocketListContent(
                        state = RocketUiState.Error("An unexpected error occurred"),
                        searchQuery = "",
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedVisibility,
                        onSearchQueryChange = {},
                        onRocketClick = {},
                        onRetry = {}
                    )
                }
            }
        }
    }
}
