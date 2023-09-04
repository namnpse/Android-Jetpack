package com.namnp.androidjetpack.design_pattern.builder

import android.content.Intent
import android.net.Uri
import android.os.Bundle

class IntentBuilder {

    private var action: String? = null
    private var uri: Uri? = null
    private var bundle: Bundle? = null

    fun setAction(action: String): IntentBuilder {
        this.action = action
        return this
    }

    fun setUri(uri: Uri): IntentBuilder {
        this.uri = uri
        return this
    }

    fun setBundle(bundle: Bundle): IntentBuilder {
        this.bundle = bundle
        return this
    }

    fun build(): Intent {
        val intent = Intent()
        action?.let {
            intent.action = it
        }
        uri?.let {
            intent.data = it
        }
        bundle?.let {
            intent.putExtras(it)
        }
        return intent
    }
}

fun main() {
    val intent = IntentBuilder()
        .setAction(Intent.ACTION_VIEW)
        .setUri(Uri.parse("https://www.example.com"))
        .setBundle(Bundle())
        .build()
}