package com.example.noteapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.example.noteapp.R
import com.example.noteapp.db.AppDatabase
import com.example.noteapp.models.Note
import com.example.noteapp.models.NoteSingleton
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorPickerView
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNoteActivity : AppCompatActivity(), ColorPickerDialogListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        btnColorPicker.setOnClickListener {
            ColorPickerDialog.newBuilder().show(this)
        }

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

    // Returns selection from the color picker
    override fun onColorSelected(dialogId: Int, color: Int) {
        Toast.makeText(this, "Selected color: " + color.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onDialogDismissed(dialogId: Int) { }
}