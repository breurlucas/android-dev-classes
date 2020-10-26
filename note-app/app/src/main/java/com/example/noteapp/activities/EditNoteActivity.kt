package com.example.noteapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.noteapp.R
import com.example.noteapp.db.AppDatabase
import com.example.noteapp.models.Note
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import kotlinx.android.synthetic.main.activity_edit_note.*
import kotlinx.android.synthetic.main.activity_edit_note.btnColorPicker
import kotlinx.android.synthetic.main.activity_edit_note.etDescription
import kotlinx.android.synthetic.main.activity_edit_note.etTitle
import kotlinx.android.synthetic.main.activity_new_note.*

class EditNoteActivity : AppCompatActivity(), ColorPickerDialogListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        // Get the note passed through the intent
        val note = intent.getSerializableExtra("note") as Note
        etTitle.setText(note.title)
        etDescription.setText(note.description)

        btnColorPicker.setOnClickListener {
            ColorPickerDialog.newBuilder().show(this)
        }

        btnSaveEdit.setOnClickListener {

            val sharedPrefs =
                getSharedPreferences("Users", Context.MODE_PRIVATE)

            val background = sharedPrefs.getInt("background", 0)

            if (background != 0) {
                note.background = background
                resetBackground(sharedPrefs)
            }

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

    fun resetBackground(sharedPrefs : SharedPreferences) {
        val editor = sharedPrefs.edit()

        // Resets to 0 for the next insertion
        editor.putInt("background", 0)

        editor.commit() // Writes on and closes file (Apply keeps the file open)
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        val sharedPrefs =
            getSharedPreferences("Users", Context.MODE_PRIVATE)

        val editor = sharedPrefs.edit()

        // Temporarily saves the picked color to be retrieved and saved in the database when the users concludes the card editing
        editor.putInt("background", color)
//        Toast.makeText(this, "Selected color: " + color.toString(), Toast.LENGTH_LONG).show()
        editor.commit() // Writes on and closes file (Apply keeps the file open)
    }

    override fun onDialogDismissed(dialogId: Int) { }
}