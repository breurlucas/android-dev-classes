package com.example.noteapp.utilities

import android.app.AlertDialog
import android.content.Context

fun alert(title: String, msg: String, context: Context) {
    val builder = AlertDialog.Builder(context)
    builder
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton("OK", null)
        .create()
        .show()
}