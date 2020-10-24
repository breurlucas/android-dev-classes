package com.example.pdm_p1

import android.content.Context
import androidx.appcompat.app.AlertDialog

// Global alert creation function. This function can be used by any activity in order to show a dialog box
fun alert(title: String, msg: String, context: Context) {
    val builder = AlertDialog.Builder(context)
    builder
        .setTitle(title) // Sets the title based on the title input
        .setMessage(msg) // Sets the message based on the message input
        .setPositiveButton("OK", null) // Sets a confirmation button with the "OK" label
        .create()
        .show()
}