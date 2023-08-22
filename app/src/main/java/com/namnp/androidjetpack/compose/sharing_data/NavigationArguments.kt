package com.namnp.androidjetpack.compose.sharing_data

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

const val ARG_KEY_1 = "KEY_1"
const val EXERCISE_ID = "EXERCISE_ID"
const val WORKOUT_ID = "WORKOUT_ID"
const val SET_NUMBER = "SET_NUMBER"
const val REP_NUMBER = "REP_NUMBER"
//createExercise/{exerciseId}/{workoutId}?setNumber={setNumber}&repNumber={repNumber}

// NOTE
// BENEFIT:
//+ easy, straightforward
// DISADVANTAGE:
//+ hard to pass in nested level/graph (have to pass level 1->2->3...->7)
//+ hard to pass complex object, parcelable data
//+ only pass stateless param (only pass value, cannot update or change)

// WAY 1
@Composable
fun NavigationArgs() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "screen1"
    ) {
        composable("screen1") {
            Screen1(onNavigateToScreen2 = {
//                navController.navigate("screen2/$it")
//                navController.navigate("createExercise/Ex1/Work1")
                navController.navigate("createExercise/Ex1/Work1?$SET_NUMBER")
//                navController.navigate("createExercise/Ex1/Work1?$SET_NUMBER=1&$REP_NUMBER=2")
            })
        }
        composable(
            route = "screen2/{$ARG_KEY_1}",
            arguments = listOf(
                navArgument(ARG_KEY_1) {
                    type = NavType.StringType
                },
            )
        ) {
            val param = it.arguments?.getString(ARG_KEY_1) ?: ""
            Screen2(param = param)
        }
        composable(
            route = "createExercise/{$EXERCISE_ID}/{$WORKOUT_ID}?$SET_NUMBER={$SET_NUMBER}&$REP_NUMBER={$REP_NUMBER}",
            arguments = listOf(
                navArgument(EXERCISE_ID) {
                    type = NavType.StringType
                },
                navArgument(WORKOUT_ID) {
                    type = NavType.StringType
                },
                navArgument(SET_NUMBER) {
                    type = NavType.IntType
                    defaultValue = 123
                },
                navArgument(REP_NUMBER) {
                    type = NavType.IntType
                    defaultValue = 234
                }
            )
        ) {
            val exerciseId = it.arguments?.getString(EXERCISE_ID) ?: ""
            val workoutId = it.arguments?.getString(WORKOUT_ID) ?: ""
            val setNumber = it.arguments?.getInt(SET_NUMBER)
            val repNumber = it.arguments?.getInt(REP_NUMBER)
            CreateExercise(
                exerciseId = exerciseId,
                workoutId = workoutId,
                setNumber = setNumber,
                repNumber = repNumber,
            )
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

@Composable
private fun CreateExercise(
    exerciseId: String,
    workoutId: String,
    setNumber: Int? = null,
    repNumber: Int? = null,
) {
    Column {
        Text(text = "CreateExercise")
        Text(text = "exerciseId: $exerciseId")
        Text(text = "workoutId: $workoutId")
        Text(text = "setNumber: ${setNumber ?: "No setNumber"}")
        Text(text = "repNumber: ${repNumber ?: "No repNumber"}")
    }
}