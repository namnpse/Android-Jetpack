package com.namnp.androidjetpack.compose.best_pratice

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.namnp.androidjetpack.compose.ui.theme.AndroidJetpackTheme
import kotlinx.coroutines.flow.collectLatest

// Handling events with sealed classes
class ComposeBestPracticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidJetpackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                  MainScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    counterViewModel: CounterViewModel = viewModel()
){

  val screenState = counterViewModel.screenState.value
  val scaffoldState = rememberScaffoldState()

   LaunchedEffect(key1 = true ){
        counterViewModel.uiEventFlow.collectLatest {event->
             when(event){
                 is UIEvent.ShowMessage -> {
                   scaffoldState.snackbarHostState.showSnackbar(
                       message = event.message
                   )
                 }
             }
        }
   }



  Scaffold(scaffoldState = scaffoldState) {
      Column(
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp),
        verticalArrangement = Arrangement.SpaceEvenly
      ) {
         Text(
             modifier = modifier.fillMaxWidth(),
             text = screenState.displayingResult,
             fontWeight = FontWeight.Bold,
             fontSize = 30.sp,
             color = Color.DarkGray
         )
          OutlinedTextField(
              value = screenState.inputValue,
              onValueChange = {
                  counterViewModel.onEvent(CounterEvent.ValueEntered(it))
              },
              modifier = modifier.fillMaxWidth(),
              keyboardOptions = KeyboardOptions(
                  keyboardType = KeyboardType.Number
              ),
              textStyle = TextStyle(
                  color = Color.LightGray,
                  fontSize = 30.sp,
                  fontWeight = FontWeight.Bold
              ),
              label = { Text(text = "New Count")}
          )
          if(screenState.isCountButtonVisible){
              Button(
                  onClick = {
                      counterViewModel.onEvent(CounterEvent.CountButtonClicked)
                  },
                  modifier = modifier.fillMaxWidth()
              )
              {
               Text(
                   text = "Count",
                   fontSize = 30.sp,
                   fontWeight = FontWeight.Bold
               )
              }
          }
          Button(
              onClick = {
                  counterViewModel.onEvent(CounterEvent.ResetButtonClicked)
              },
              modifier = modifier.fillMaxWidth()
          ) {
              Text(
                  text = "Reset",
                  fontSize = 30.sp,
                  fontWeight = FontWeight.Bold
              )
          }

      }
  }

}



