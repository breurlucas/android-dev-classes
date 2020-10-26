package com.example.noteapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import kotlinx.android.synthetic.main.activity_user.*

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

            val username = sharedPrefs.getString("username", "Guest")

            val background = sharedPrefs.getInt("background", 0)


            username?.let {
                // If the background is 0 no color was picked. The default color will be applied in the ListNotesActivity
                var note = Note(title = etTitle.text.toString(), description = etDescription.text.toString(), user = it)

                if (background != 0) {
                    note = Note(title = etTitle.text.toString(), description = etDescription.text.toString(), user = it, background = background)
                }

                resetBackground(sharedPrefs)
                insertNote(note)
            }
        }
    }

    fun insertNote(note: Note) {
        Thread {
            val db =
                Room.databaseBuilder(this, AppDatabase::class.java, "AppDb").build()

            db.noteDao().insert(note)

            finish()
        }.start()
    }

    fun resetBackground(sharedPrefs : SharedPreferences) {
        val editor = sharedPrefs.edit()

        // Resets to 0 for the next insertion
        editor.putInt("background", 0)

        editor.commit() // Writes on and closes file (Apply keeps the file open)
    }

    // Returns selection from the color picker
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