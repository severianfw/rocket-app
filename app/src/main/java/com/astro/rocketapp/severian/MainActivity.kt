package com.astro.rocketapp.severian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.astro.rocketapp.severian.di.AppModule
import com.astro.rocketapp.severian.ui.screen.RocketDetailScreen
import com.astro.rocketapp.severian.ui.screen.RocketListScreen
import com.astro.rocketapp.severian.ui.viewmodel.RocketViewModel
import com.astro.rocketapp.severian.ui.theme.RocketAppTheme
import com.astro.rocketapp.severian.ui.viewmodel.RocketViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: RocketViewModel by viewModels<RocketViewModel> {
        RocketViewModelFactory(AppModule.getRocketsUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RocketAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RocketAppNavHost(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RocketAppNavHost(
    viewModel: RocketViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "rocket_list",
        modifier = modifier
    ) {
        composable("rocket_list") {
            RocketListScreen(
                viewModel = viewModel,
                onRocketClick = { rocketId ->
                    navController.navigate("rocket_detail/$rocketId")
                }
            )
        }
        composable(
            route = "rocket_detail/{rocketId}",
            arguments = listOf(navArgument("rocketId") { type = NavType.StringType })
        ) { backStackEntry ->
            val rocketId = backStackEntry.arguments?.getString("rocketId") ?: ""
            RocketDetailScreen(
                rocketId = rocketId,
                viewModel = viewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}
