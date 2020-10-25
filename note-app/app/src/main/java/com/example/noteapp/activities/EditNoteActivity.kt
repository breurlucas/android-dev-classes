package com.example.noteapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.noteapp.R
import com.example.noteapp.db.AppDatabase
import com.example.noteapp.models.Note
import kotlinx.android.synthetic.main.activity_edit_note.*

class EditNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        // Get the note passed through the intent
        val note = intent.getSerializableExtra("note") as Note
        etTitle.setText(note.title)
        etDescription.setText(note.description)

        btnSaveEdit.setOnClickListener {
            // Assign updated field values to the note
            note.title = etTitle.text.toString()
            note.description = etDescription.text.toString()

            Thread {
                val db =
                    Room.databaseBuilder(this, AppDatabase::class.java, "AppDb").build()

                db.noteDao().update(note)

                val intent = Intent(this, ListNotesActivity::class.java)

                runOnUiThread {
                    startActivity(intent)
                }

            }.start()
        }

    }
}