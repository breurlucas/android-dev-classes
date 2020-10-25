package com.example.noteapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.noteapp.R
import com.example.noteapp.models.NoteSingleton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_list_notes.*
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

        val username = sharedPrefs.getString("username", "")

        Toast.makeText(this, "Logged in as $username", Toast.LENGTH_LONG).show()

        // 'Fab' is a placeholder button, it is being triggered by "on resume"
        // Snackbar.make(fab, "Logged in as $username", Snackbar.LENGTH_LONG).show()

        constructNotes()
    }

    fun constructNotes() {

        noteContainer.removeAllViews()

        // Get default preferences file for the app
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val color = prefs.getInt("noteColor", R.color.noteDefaultColor)

        for (note in NoteSingleton.list) {
            val card =
                layoutInflater.inflate(R.layout.note_card, noteContainer, false)

            card.txtTitle.text = note.title
            card.txtDescription.text = note.description
            card.txtUser.text = note.user
            card.setBackgroundColor(color)

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