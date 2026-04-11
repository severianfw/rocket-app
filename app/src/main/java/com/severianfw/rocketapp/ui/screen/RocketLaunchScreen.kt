package com.severianfw.rocketapp.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.severianfw.rocketapp.R
import com.severianfw.rocketapp.domain.model.RocketLaunch
import com.severianfw.rocketapp.ui.state.RocketLaunchUiState
import com.severianfw.rocketapp.ui.theme.BlueSecondary
import com.severianfw.rocketapp.ui.theme.BlueTertiary
import com.severianfw.rocketapp.ui.theme.RocketAppTheme
import com.severianfw.rocketapp.ui.viewmodel.RocketViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun RocketLaunchScreen(
    viewModel: RocketViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.launchState.collectAsState()

    RocketLaunchContent(
        state = state,
        onRetry = viewModel::getUpcomingLaunches,
        modifier = modifier
    )
}

@Composable
fun RocketLaunchContent(
    state: RocketLaunchUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Upcoming Launches",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.tertiary
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (state) {
                is RocketLaunchUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is RocketLaunchUiState.Success -> {
                    if (state.launches.isEmpty()) {
                        Text(
                            text = "No upcoming launches",
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(items = state.launches, key = { it.id }) { launch ->
                                LaunchItem(launch = launch)
                            }
                        }
                    }
                }

                is RocketLaunchUiState.Error -> {
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LaunchItem(launch: RocketLaunch) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.tertiary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Column {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                model = launch.image.ifBlank { R.drawable.rocket_list_placeholder },
                loading = placeholder(R.drawable.rocket_list_placeholder),
                failure = placeholder(R.drawable.rocket_list_placeholder),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Column(modifier = Modifier.padding(12.dp)) {
                if (launch.rocketName.isNotBlank()) {
                    Text(
                        text = launch.rocketName,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (launch.missionName.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = launch.missionName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (launch.missionType.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = launch.missionType,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (launch.orbitName.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = launch.orbitName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (launch.statusName.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.secondary
                    ) {
                        Text(
                            text = launch.statusName,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LaunchCountdown(net = launch.net)
            }
        }
    }
}

@Composable
fun LaunchCountdown(net: String) {
    var millisRemaining by remember(net) { mutableLongStateOf(calculateMillisRemaining(net)) }

    LaunchedEffect(net) {
        while (millisRemaining > 0) {
            delay(1000L)
            millisRemaining = calculateMillisRemaining(net)
        }
    }

    if (millisRemaining > 0) {
        val days = millisRemaining / (1000L * 60 * 60 * 24)
        val hours = (millisRemaining % (1000L * 60 * 60 * 24)) / (1000L * 60 * 60)
        val minutes = (millisRemaining % (1000L * 60 * 60)) / (1000L * 60)
        val seconds = (millisRemaining % (1000L * 60)) / 1000L

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CountdownUnit(value = days, label = "DAYS")
            CountdownSeparator()
            CountdownUnit(value = hours, label = "HRS")
            CountdownSeparator()
            CountdownUnit(value = minutes, label = "MIN")
            CountdownSeparator()
            CountdownUnit(value = seconds, label = "SEC")
        }
    }
}

@Composable
fun CountdownUnit(value: Long, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = BlueSecondary)
        ) {
            AnimatedContent(
                targetState = value,
                transitionSpec = {
                    (slideInVertically { it } + fadeIn()) togetherWith
                            (slideOutVertically { -it } + fadeOut())
                },
                label = "countdown_$label"
            ) { count ->
                Text(
                    text = String.format(Locale.US, "%02d", count),
                    style = MaterialTheme.typography.bodyLarge,
                    color = BlueTertiary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(44.dp)
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun CountdownSeparator() {
    Text(
        text = ":",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

private fun calculateMillisRemaining(net: String): Long {
    if (net.isBlank()) return 0L
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val launchTime = sdf.parse(net)?.time ?: return 0L
        maxOf(0L, launchTime - System.currentTimeMillis())
    } catch (_: Exception) {
        0L
    }
}

private val sampleLaunches = listOf(
    RocketLaunch(
        id = "1",
        net = "2026-04-20T10:00:00Z",
        image = "",
        rocketName = "Falcon 9 Block 5",
        missionName = "Starlink Group 6-55",
        missionType = "Communications",
        orbitName = "Low Earth Orbit",
        statusName = "Go for Launch"
    ),
    RocketLaunch(
        id = "2",
        net = "2026-04-25T18:30:00Z",
        image = "",
        rocketName = "Falcon Heavy",
        missionName = "USSF-52",
        missionType = "Government/Top Secret",
        orbitName = "Geostationary Transfer Orbit",
        statusName = "Go for Launch"
    )
)

@Preview(showBackground = true)
@Composable
fun RocketLaunchContentLoadingPreview() {
    RocketAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RocketLaunchContent(
                state = RocketLaunchUiState.Loading,
                onRetry = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RocketLaunchContentSuccessPreview() {
    RocketAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RocketLaunchContent(
                state = RocketLaunchUiState.Success(launches = sampleLaunches),
                onRetry = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RocketLaunchContentErrorPreview() {
    RocketAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RocketLaunchContent(
                state = RocketLaunchUiState.Error("Network error: Please check your internet connection"),
                onRetry = {}
            )
        }
    }
}
