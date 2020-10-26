package com.example.noteapp.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteapp.R
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val sharedPrefs =
                getSharedPreferences("Users", Context.MODE_PRIVATE)

        etUsername.setText(sharedPrefs.getString("username", ""))

        btnSave.setOnClickListener {
            val editor = sharedPrefs.edit()

            val username = etUsername.text.toString()

            editor.putString("username", username)

            editor.commit() // Writes on and closes file (Apply keeps the file open)

            finish()
        }
    }
}