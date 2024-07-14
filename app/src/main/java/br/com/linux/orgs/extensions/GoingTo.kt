package br.com.linux.orgs.extensions

import android.content.Context
import android.content.Intent

fun Context.goingTo(clazz: Class<*>, intent: Intent.() -> Unit = {}) {
    Intent(this, clazz)
        .apply {
            intent()
            startActivity(this)
        }
}