package com.example.noteapp.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.noteapp.R
import com.example.noteapp.db.AppDatabase
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
                val note = Note(title = etTitle.text.toString(), description = etDescription.text.toString(), user = it)

                Thread {
                    insertNote(note)
                    finish()
                }.start()
            }
        }
    }

    fun insertNote(note: Note) {
        val db =
            Room.databaseBuilder(this, AppDatabase::class.java, "AppDb").build()

        db.noteDao().insert(note)
    }
}