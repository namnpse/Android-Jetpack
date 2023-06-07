package com.namnp.androidjetpack.compose

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.namnp.androidjetpack.compose.ui.theme.AndroidJetpackTheme

class JetpackComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidJetpackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    ScrollableColumn()
//                    LazyColumnCompose() {
//
//                    }
                    DisplayTvShows {
                        Toast.makeText(this, it.name, Toast.LENGTH_LONG).show()
                        startActivity(InfoActivity.intent(this, it))
//                }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ScrollableColumn() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        for (i in 1..20) {
            Text(
                "User Name $i",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(12.dp)
            )
            Divider(color = Color.Black, thickness = 6.dp)
        }
    }
}

@Composable
fun LazyColumnCompose(selectedItem: (String) -> Unit) {
    LazyColumn {
        items(20) {
            Text(
                "User Name $it",
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .padding(12.dp)
                    .clickable { selectedItem("$it Selected") }
            )
            Divider(color = Color.Black, thickness = 6.dp)
        }
    }
}

@Composable
fun DisplayTvShows(selectedItem: (TvShow) -> Unit) {

    val tvShows = remember { TvShowList.tvShows }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = tvShows,
            itemContent = {
                TvShowListItem(tvShow = it, selectedItem)
            }
        )
    }
}