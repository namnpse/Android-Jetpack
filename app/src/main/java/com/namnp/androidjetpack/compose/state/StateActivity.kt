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
                MyButton()
            }
        }

    }
}

@Composable
fun MyButton() {
//    1. remember -> remember state from previous recompose
//    -> for instance, if you  randomize color at initial run. The randomized color is going to be calculated only once and later reused whenever re-compose is necessary.
//    remember = store the value just in case recompose is called.
//    so now when reCompose should actually be triggered? And there the mutable states come to help.
//    2. mutableState = store the value and in case I update value trigger, recompose for all elements using this data.
    var count by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    Button(
        onClick = {
            count = count+1
            Toast.makeText(context, "${count}", Toast.LENGTH_LONG).show()
        },
        contentPadding = PaddingValues(16.dp),
        border = BorderStroke(8.dp, Color.Black),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.DarkGray,
            contentColor = Color.White,
        )
    ) {
        Text(text = "Count is: ${count}")
    }
}