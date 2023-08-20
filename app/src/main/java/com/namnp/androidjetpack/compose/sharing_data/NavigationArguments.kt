package com.namnp.androidjetpack.compose.sharing_data

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

const val ARG_KEY_1 = "KEY_1"

@Composable
fun NavigationArgs() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "screen1"
    ) {
        composable("screen1") {
            Screen1(onNavigateToScreen2 = {
                navController.navigate("screen2/$it")
            })
        }
        composable(
            route = "screen2/{$ARG_KEY_1}",
            arguments = listOf(
                navArgument(ARG_KEY_1) {
                    type = NavType.StringType
                },
                navArgument(ARG_KEY_1) {
                    type = NavType.StringType
                }
            )
        ) {
            val param = it.arguments?.getString(ARG_KEY_1) ?: ""
            Screen2(param = param)
        }
    }
}




@Composable
private fun Screen1(onNavigateToScreen2: (String) -> Unit) {
    Button(onClick = {
        onNavigateToScreen2("Hello world!")
    }) {
        Text(text = "Click me")
    }
}

@Composable
private fun Screen2(param: String) {
    Text(text = param)
}