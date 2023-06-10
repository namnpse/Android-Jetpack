package com.namnp.androidjetpack.compose.sife_effect

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.namnp.androidjetpack.compose.ui.theme.AndroidJetpackTheme
import kotlinx.coroutines.launch


class SideEffectActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidJetpackTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {

    var round by remember { mutableStateOf(1) }
    var total by remember { mutableStateOf(0) }
    var input by remember { mutableStateOf("") }

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

//    LaunchedEffect(key1 = true, ){    run once, one time
    LaunchedEffect(key1 = round, ){ // run whenever round value changes
        scaffoldState.snackbarHostState.showSnackbar(
            "Round: $round",
            duration = SnackbarDuration.Long
        )
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
        ) {
            Text(text = "Total is: $total")
            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = {
                    total += input.toInt()
                    if (total > 300) {
                        round++
                        total = 0
                    }
                    // cannot use launchedEffect here cause it's a callback, event,not a @Composable Scope
                    // need to use `coroutineScope`
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            "New input: $input",
                            duration = SnackbarDuration.Long
                        )
                    }
                },
            ) {

            }
        }
    }
}

// NOTE
//+ Side effect: A side-effect is a change to the state of the app that happens outside the scope of a composable function.
//+ Use rememberCoroutineScope() when you need to use coroutines in a composable
//-> cancel and relaunch the coroutine after an event.
//+ Use LaunchedEffect() when you need to use coroutines in a composable
//-> cancel and relaunch the coroutine every time your parameter/key changes.
//+ If it is in an event -> use rememberCoroutineScope,
//+ if it is in another place of the composable and you have a parameter -> use LaunchedEffect.
