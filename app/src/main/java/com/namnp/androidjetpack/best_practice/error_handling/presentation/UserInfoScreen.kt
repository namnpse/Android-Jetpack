package com.namnp.androidjetpack.best_practice.error_handling.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.namnp.androidjetpack.best_practice.error_handling.presentation.UserContract.*
import com.namnp.androidjetpack.flow.CollectSideEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun UserProfileScreen() {
    val viewModel: UserViewModel = hiltViewModel<UserViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    UserProfileInfo(
        uiState = uiState,
        sideEffect = viewModel.sideEffect,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun UserProfileInfo(
    uiState: UserState,
    sideEffect: Flow<UserEffect>,
    onEvent: (UserEvent) -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(sideEffect) {
        sideEffect.collect { effect ->
            when (effect) {
                is UserEffect.ShowError -> {
                    Toast.makeText(context, effect.error.asString(context), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // extract to common extension func to reuse everywhere
    CollectSideEffect(sideEffect) { effect ->
        when (effect) {
            is UserEffect.ShowError -> {
                Toast.makeText(context, effect.error.asString(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Username: ${uiState.user.name}",
            color = Color.Black,
        )
        Text(
            text = "Email: ${uiState.user.email}",
            color = Color.Black,
        )
        Text(
            text = "Phone number: ${uiState.user.phoneNumber}",
            color = Color.Black,
        )
        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    onEvent(UserEvent.OnRegisterClick("Nam Jr"))
                }
            ) {
                Text("Register")
            }
        }
    }
}