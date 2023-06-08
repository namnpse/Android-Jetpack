package com.namnp.androidjetpack.compose.state

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.namnp.androidjetpack.compose.ui.theme.AndroidJetpackTheme

class StateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidJetpackTheme {
                val viewModel = viewModel<StateViewModel>() // can be injected if using Hilt
                val context = LocalContext.current
//                var count by rememberSaveable { // remember cannot handle config changes -> use rememberSavable
//                    to preserve such configuration change, use rememberSaveable instead of remember
//                    Shouldn't be using rememberSaveable to store large amounts of data or complex data structures that require lengthy serialization or deserialization.
//                    Following the architectural best practices -> should use a view model for that.
//                    mutableStateOf(0)
//                }
                val count = viewModel.count
                MyButton(
                    currentCount = count,
                    updateCount = {
                        // if not using view model
//                        count = it+1
//                        Toast.makeText(context, "$count", Toast.LENGTH_LONG).show()
                        // if using view model, auto handle config change
                        viewModel.increaseCount()
                    }
                )
            }
        }

    }
}

@Composable
fun MyButton(currentCount: Int, updateCount: (Int) -> Unit) {
    // NOTE
//    1. remember -> remember state from previous recompose
//    -> for instance, if you  randomize color at initial run. The randomized color is going to be calculated only once and later reused whenever re-compose is necessary.
//    remember = store the value just in case recompose is called.
//    so now when reCompose should actually be triggered? And there the mutable states come to help.
//    2. mutableState = store the value and in case I update value trigger, recompose for all elements using this data.
    // Stateful composable cause it has own state -> need to make it Stateless by remote its state
//    var count by remember {
//        mutableStateOf(0)
//    }
//    3. sending the state from top to bottom.
//     sending the event up, from bottom to the top.
//    -> This concept where the state goes down and events go up called unidirectional data flow
//    -> UDF: unidirectional data flow
//    4. State Hoisting: move the state up
//    To apply state hoisting, usually need to add two parameters:
//    + The current value to display
//    + An event that request current value to change.
//    5. Benefit of UDF and State Hoisting:
//    + Decouples the state from the composable. That allows us to store the state somewhere like local/remote source and update the composable when required.
//    + State hoisting provides a single data source -> single source of truth.
//    + It helps to avoid bugs and it makes maintain of the code much easier.
//    + It also makes our states more secure by encapsulating states.
//    + State hoisting makes states shareable between different composable.
//    + Make Unit testing composables easier.
//    6. rememberSavable
//    to preserve such configuration change, use rememberSaveable instead of remember
//    Shouldn't be using rememberSaveable to store large amounts of data or complex data structures that require lengthy serialization or deserialization.
//    Following the architectural best practices -> should use a view model for that.
    Button(
        onClick = {
            updateCount(currentCount)
        },
        contentPadding = PaddingValues(16.dp),
        border = BorderStroke(8.dp, Color.Black),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.DarkGray,
            contentColor = Color.White,
        )
    ) {
        Text(text = "Count is: $currentCount")
    }
}