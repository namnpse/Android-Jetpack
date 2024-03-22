package com.namnp.androidjetpack.best_practice.error_handling.presentation

import com.namnp.androidjetpack.R
import com.namnp.androidjetpack.best_practice.error_handling.domain.DataError
import com.namnp.androidjetpack.best_practice.string_resource_view_model.UiText
import com.namnp.androidjetpack.best_practice.error_handling.domain.Result

fun DataError.asUiText(): UiText {

    return when (this) {

        DataError.Network.NOT_FOUND -> UiText.StringResource(
            R.string.not_found
        )

        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.the_request_timed_out
        )

        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.youve_hit_your_rate_limit
        )

        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.no_internet
        )

        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.file_too_large
        )

        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.server_error
        )

        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        DataError.Network.INTERNAL_SERVER -> UiText.StringResource(
            R.string.internal_server_error
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(
            R.string.unknown_error
        )

        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )

        else -> UiText.StringResource(
            R.string.internal_server_error
        )

    }
}

fun Result.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}