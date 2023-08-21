package com.namnp.androidjetpack.compose.sharing_data

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.namnp.androidjetpack.compose.sharing_data.util.SharedViewModel

// NOTE
//1. Benefit:
//+ share stateful params -> live data, can change and observe data changes
//+ easy to pass in nested navigation/screen (Ex: pass level 1 directly to level 7, not have to pass 1->2->3...->7)
//+ easy to restore state/data from process death using SavedStateHandle
//2. Disadvantage:
//+ hard to debug because data can be changed or updated from anywhere

@Composable
fun SharedViewModelComponent() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        navigation(
            startDestination = "splash_screen",
            route = "onboarding",
        ) {
            composable("splash_screen") { entry ->
                val viewModel = entry.sharedViewModel<SharedViewModel>(navController) // shared view model
                val sharedState by viewModel.sharedState.collectAsStateWithLifecycle()

                PersonalDetailsScreen(
                    sharedState = sharedState,
                    onNavigate = {
                        viewModel.updateState()
                        navController.navigate("terms_and_conditions")
                    }
                )
            }
            composable("terms_and_conditions") { entry ->
                val viewModel = entry.sharedViewModel<SharedViewModel>(navController) // shared view model
                val state by viewModel.sharedState.collectAsStateWithLifecycle()

                TermsAndConditionsScreen(
                    sharedState = state,
                    onOnboardingFinished = {
                        navController.navigate(route = "home_screen") {
                            popUpTo("onboarding") {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable("home_screen") {
            Text(text = "Home Screen")
        }
    }
}

// scope view model to a graph -> child components get the same view model by its parent route -> share view model in a graph
// NavBackStackEntry implement VMStoreOwner, viewModel(parentEntry: VMStoreOwner)
@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
) : T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)

}

@Composable
private fun PersonalDetailsScreen(
    sharedState: Int,
    onNavigate: () -> Unit
) {
    Button(onClick = onNavigate) {
        Text(text = "Click me")
    }
}

@Composable
private fun TermsAndConditionsScreen(
    sharedState: Int,
    onOnboardingFinished: () -> Unit
) {
    Button(onClick = onOnboardingFinished) {
        Text(text = "State: $sharedState")
    }
}