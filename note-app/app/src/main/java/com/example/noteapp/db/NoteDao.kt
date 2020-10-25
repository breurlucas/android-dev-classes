package com.example.noteapp.db

import androidx.room.*
import com.example.noteapp.models.Note

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query(value = "SELECT * FROM Note")
    fun getAll(): List<Note>
}