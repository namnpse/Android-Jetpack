package com.namnp.androidjetpack.compose.best_pratice.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(onNavigateToSecondScreen : (String)->Unit , modifier:Modifier = Modifier){
    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(60.dp)
        ,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,

    ){

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.DarkGray,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

        )

        Button(
            onClick = {
                if(text!="") {
                    onNavigateToSecondScreen(text)
                }
            },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = "Submit",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



