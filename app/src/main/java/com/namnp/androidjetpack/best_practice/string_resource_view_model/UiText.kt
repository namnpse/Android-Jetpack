package com.namnp.androidjetpack.best_practice.string_resource_view_model

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed class UiText {

    data class DynamicString(val value: String) : UiText()

    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }

    // use for Compose
    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> LocalContext.current.getString(id, *args)
        }
    }
}