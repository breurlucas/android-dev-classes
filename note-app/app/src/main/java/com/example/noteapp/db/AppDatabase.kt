package com.example.noteapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.models.Note

@Database(entities = arrayOf(Note::class), version = 3)
abstract class AppDatabase : RoomDatabase() { // In Kotlin we extend the constructor directly

    // We need a function for each DAO we have
    abstract fun noteDao(): NoteDao
}