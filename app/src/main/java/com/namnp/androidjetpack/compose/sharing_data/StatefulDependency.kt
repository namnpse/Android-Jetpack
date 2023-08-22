package com.namnp.androidjetpack.compose.sharing_data

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.namnp.androidjetpack.compose.sharing_data.util.Screen1ViewModel

// WAY 3: 2 view models/2 screens using the same object (shared dependencies)
@Composable
fun StatefulDependencySample() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "screen1"
    ) {
        composable("screen1") {
            val viewModel = hiltViewModel<Screen1ViewModel>() // init view model and inject dependencies
//            val viewModel = viewModel<Screen1ViewModel>() // only init view model, not inject if use hilt
            val count by viewModel.count.collectAsStateWithLifecycle()

            Screen1(
                count = count,
                onNavigateToScreen2 = {
                    viewModel.inc()
                    navController.navigate("screen2")
                }
            )
        }
        composable(
            route = "screen2"
        ) {
            val viewModel = hiltViewModel<Screen1ViewModel>()
            val count by viewModel.count.collectAsStateWithLifecycle()

            Screen2(count)
        }
    }
}


@Composable
private fun Screen1(
    count: Int,
    onNavigateToScreen2: () -> Unit
) {
    Button(onClick = {
        onNavigateToScreen2()
    }) {
        Text(text = "Count on screen1: $count")
    }
}

@Composable
private fun Screen2(count: Int) {
    Text(text = "Count on screen2: $count")
}