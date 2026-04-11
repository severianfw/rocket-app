package com.severianfw.rocketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.severianfw.rocketapp.di.AppModule
import com.severianfw.rocketapp.ui.screen.RocketDetailScreen
import com.severianfw.rocketapp.ui.screen.RocketLaunchScreen
import com.severianfw.rocketapp.ui.screen.RocketListScreen
import com.severianfw.rocketapp.ui.theme.RocketAppTheme
import com.severianfw.rocketapp.ui.theme.navigationBarColor
import com.severianfw.rocketapp.ui.viewmodel.RocketViewModel
import com.severianfw.rocketapp.ui.viewmodel.RocketViewModelFactory

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object RocketList : Screen("rocket_list", "Rockets", Icons.AutoMirrored.Filled.List)
    data object RocketLaunch : Screen("rocket_launch", "Launch", Icons.AutoMirrored.Filled.Send)
}

class MainActivity : ComponentActivity() {

    private val viewModel: RocketViewModel by viewModels<RocketViewModel> {
        RocketViewModelFactory(
            AppModule.getRocketsUseCase,
            AppModule.getRocketDetailUseCase,
            AppModule.getUpcomingLaunchesUseCase
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RocketAppTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: RocketViewModel) {
    val navController = rememberNavController()
    val bottomNavItems = listOf(Screen.RocketList, Screen.RocketLaunch)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Hide bottom bar on detail screen or any screen not in bottomNavItems
    val showBottomBar = bottomNavItems.any { it.route == currentDestination?.route }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.tertiary
                ) {
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = {
                                Text(
                                    text = screen.label,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            colors = navigationBarColor,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        RocketAppNavHost(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RocketAppNavHost(
    navController: NavHostController,
    viewModel: RocketViewModel,
    modifier: Modifier = Modifier
) {
    SharedTransitionLayout(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = Screen.RocketList.route
        ) {
            composable(Screen.RocketList.route) {
                RocketListScreen(
                    viewModel = viewModel,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    onRocketClick = { rocketId ->
                        navController.navigate("rocket_detail/$rocketId")
                    }
                )
            }
            composable(Screen.RocketLaunch.route) {
                RocketLaunchScreen(viewModel = viewModel)
            }
            composable(
                route = "rocket_detail/{rocketId}",
                arguments = listOf(navArgument("rocketId") { type = NavType.IntType })
            ) { backStackEntry ->
                val rocketId = backStackEntry.arguments?.getInt("rocketId") ?: 0
                RocketDetailScreen(
                    rocketId = rocketId,
                    viewModel = viewModel,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}
