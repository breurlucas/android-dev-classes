package com.example.noteapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.example.noteapp.R
import com.example.noteapp.db.AppDatabase
import com.example.noteapp.models.Note
import com.example.noteapp.models.NoteSingleton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_list_notes.*
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.note_card.*
import kotlinx.android.synthetic.main.note_card.view.*

class ListNotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_notes)

        fab.setOnClickListener {
            val intent = Intent(this, NewNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val sharedPrefs =
                getSharedPreferences("Users", Context.MODE_PRIVATE)

        val username = sharedPrefs.getString("username", "Guest")

        Toast.makeText(this, "Logged in as $username", Toast.LENGTH_LONG).show()

        // 'Fab' is a placeholder button, it is being triggered by "on resume"
        // Snackbar.make(fab, "Logged in as $username", Snackbar.LENGTH_LONG).show()

        refreshNotes()
    }

    fun refreshNotes() {
        Thread {
            val db =
                Room.databaseBuilder(this, AppDatabase::class.java, "AppDb").build()

            val allNotes = db.noteDao().getAll()

            runOnUiThread {
                constructNotes(allNotes)
            }

        }.start()
    }

    fun deleteNote(note: Note) {
        Thread {
            val db =
                    Room.databaseBuilder(this, AppDatabase::class.java, "AppDb").build()

            db.noteDao().delete(note)

            refreshNotes()

        }.start()
    }

    fun editNote(note: Note) {
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra("note", note)
        startActivity(intent)
    }

    fun constructNotes(noteList: List<Note>) {

        noteContainer.removeAllViews()

        // Get default preferences file for the app
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val color = prefs.getInt("noteColor", R.color.white)
        val titleSize = prefs.getString("titleFontSize", "20")?.toFloat()
        val descSize = prefs.getString("descFontSize", "14")?.toFloat()
        val textColor = prefs.getInt("textColor", R.color.black)

        for (note in noteList) {
            val card =
                layoutInflater.inflate(R.layout.note_card, noteContainer, false)

            // Configure title
            card.txtTitle.text = note.title
            card.txtTitle.setTextColor(textColor)
            titleSize?.let {
                card.txtTitle.textSize = it
            }

            // Configure description
            card.txtDescription.text = note.description
            card.txtDescription.setTextColor(textColor)
            descSize?.let {
                card.txtDescription.textSize = it
            }

            // Configure user signature
            card.txtUser.text = note.user
            card.txtUser.setTextColor(textColor)

            // Check if a card background was chosen, if not, fills it with the default color
            if(note.background == null) {
                card.setBackgroundColor(color)
                card.btnCardOpts.setBackgroundColor(color)
            }
            else {
                note.background?.let {
                    card.setBackgroundColor(it)
                    card.btnCardOpts.setBackgroundColor(it)
                }
            }

            // Construct popup menu for each card containing the following actions:
            // 1. Edit card
            // 2. Delete card
            val cardMenu = PopupMenu(this, card.btnCardOpts)
            cardMenu.menuInflater.inflate(R.menu.menu_card, cardMenu.menu)

            // Set listener up for the options selection inside the menu
            cardMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    // On 'Edit' selection call the editNote() function passing the current note
                    R.id.optEdit -> {
                        editNote(note)
//                        Toast.makeText(this, "Edit card: " + card.txtTitle.text.toString(), Toast.LENGTH_LONG).show()
                    }

                    // On 'Delete' selection call the deleteNote() function passing the current note
                    R.id.optDelete -> {
                        deleteNote(note)
//                        Toast.makeText(this, "Delete card: " + card.txtTitle.text.toString(), Toast.LENGTH_LONG).show()
                    }
                }
                true
            })

            // Set listener up for the menu button. Show popup when clicked on
            card.btnCardOpts.setOnClickListener {
                cardMenu.show()
            }

            noteContainer.addView(card)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.user) {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
            return true
        }

        if (item.itemId == R.id.config) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)

    }
}