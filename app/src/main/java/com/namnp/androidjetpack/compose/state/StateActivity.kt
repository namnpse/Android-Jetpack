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
import androidx.compose.runtime.mutableStateOf
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

var count = mutableStateOf(0)
@Composable
fun MyButton() {
    val context = LocalContext.current
    Button(
        onClick = {
            count.value = count.value+1
            Toast.makeText(context, "${count.value}", Toast.LENGTH_LONG).show()
        },
        contentPadding = PaddingValues(16.dp),
        border = BorderStroke(8.dp, Color.Black),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.DarkGray,
            contentColor = Color.White,
        )
    ) {
        Text(text = "Count is: ${count.value}")
    }
}