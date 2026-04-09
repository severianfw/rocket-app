package com.severianfw.rocketapp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.severianfw.rocketapp.domain.model.Rocket
import com.severianfw.rocketapp.ui.state.RocketDetailUiState
import com.severianfw.rocketapp.ui.theme.RocketAppTheme
import com.severianfw.rocketapp.ui.viewmodel.RocketViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun RocketDetailScreen(
    rocketId: Int,
    viewModel: RocketViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBackClick: () -> Unit
) {
    val cachedRocket = viewModel.getRocketById(rocketId)
    val detailState by viewModel.detailState.collectAsState()

    LaunchedEffect(rocketId) {
        viewModel.getRocketDetail(rocketId)
    }

    val displayName = (detailState as? RocketDetailUiState.Success)?.rocket?.name
        ?: cachedRocket?.name ?: "Detail"
    val displayImage = (detailState as? RocketDetailUiState.Success)?.rocket?.image
        ?: cachedRocket?.image ?: ""

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    with(sharedTransitionScope) {
                        Text(
                            text = displayName,
                            modifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "name/$rocketId"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        )
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary,
                    actionIconContentColor = MaterialTheme.colorScheme.tertiary,
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            RocketDetailImage(
                imageUrl = displayImage,
                rocketId = rocketId,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope
            )

            when (detailState) {
                is RocketDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is RocketDetailUiState.Success -> {
                    RocketDetailInfo(
                        rocket = (detailState as RocketDetailUiState.Success).rocket,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 300.dp)
                    )
                }

                is RocketDetailUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 300.dp)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = (detailState as RocketDetailUiState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = { viewModel.getRocketDetail(rocketId) }) {
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
fun RocketDetailImage(
    imageUrl: String,
    rocketId: Int,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        GlideImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "image/$rocketId"),
                    animatedVisibilityScope = animatedVisibilityScope
                ),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun RocketDetailInfo(
    rocket: Rocket,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (rocket.description.isNotBlank()) {
            InfoSection(label = "Description", value = rocket.description)
        }
        if (rocket.launchCost.isNotBlank()) {
            InfoSection(label = "Launch Cost", value = rocket.launchCost)
        }
        InfoSection(label = "Successful Launches", value = rocket.successLaunches.toString())
        InfoSection(label = "Successful Landings", value = rocket.successLandings.toString())
        InfoSection(label = "Failed Landings", value = rocket.failedLandings.toString())
    }
}

@Composable
fun InfoSection(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun RocketDetailInfoPreview() {
    RocketAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    RocketDetailInfo(
                        rocket = Rocket(
                            id = 1,
                            name = "Falcon 9",
                            fullName = "Falcon 9 Block 5",
                            family = "Falcon",
                            variant = "Block 5",
                            image = "",
                            description = "Falcon 9 is a reusable, two-stage rocket designed and manufactured by SpaceX.",
                            launchCost = "$62,000,000",
                            successLaunches = 200,
                            successLandings = 180,
                            failedLandings = 2
                        )
                    )
                }
            }
        }
    }
}
