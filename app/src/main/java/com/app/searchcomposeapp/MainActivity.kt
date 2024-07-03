package com.app.searchcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.searchcomposeapp.presentation.ui.view.FruitsListingScreen
import com.app.searchcomposeapp.presentation.ui.view.ItemDetailScreen
import com.app.searchcomposeapp.presentation.ui.view.SelectionContainer
import com.app.searchcomposeapp.presentation.ui.viewmodel.FruitsViewModel
import com.app.searchcomposeapp.ui.theme.SearchComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val itemViewModel by viewModels<FruitsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavHost(navController = navController, startDestination = "fruitList") {
                    composable("fruitList") {
                        FruitsListingScreen(viewModel = itemViewModel, navController)
                    }
                    composable(
                        "itemDetails/{fruit}",
                        arguments = listOf(navArgument("fruit") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val fruit = backStackEntry.arguments?.getString("fruit")
                        fruit?.let {
                            ItemDetailScreen(fruit)
                        }
                    }
                }
            }
        }
    }
}
