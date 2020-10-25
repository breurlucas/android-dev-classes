package com.example.noteapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.noteapp.models.Note

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Query(value = "SELECT * FROM Note")
    fun getAll(): List<Note>
}