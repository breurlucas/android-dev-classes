package com.example.noteapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.noteapp.R
import com.example.noteapp.models.NoteSingleton
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

        constructNotes()
    }

    fun constructNotes() {

        noteContainer.removeAllViews()

        for (note in NoteSingleton.list) {
            val card =
                layoutInflater.inflate(R.layout.note_card, noteContainer, false)

            card.txtTitle.text = note.title
            card.txtDescription.text = note.description
            card.txtUser.text = note.user

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

        return super.onOptionsItemSelected(item)

    }
}