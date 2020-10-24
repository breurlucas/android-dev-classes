package com.example.noteapp.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteapp.R
import com.example.noteapp.models.Note
import com.example.noteapp.models.NoteSingleton
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        btnInsert.setOnClickListener {
            val sharedPrefs =
                    getSharedPreferences("Users", Context.MODE_PRIVATE)

            val username = sharedPrefs.getString("username", "")

            username?.let {
                val note = Note(etTitle.text.toString(), etDescription.text.toString(), it)
                NoteSingleton.list.add(note)
                finish()
            }
        }
    }
}