package com.example.noteapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true) // Will be automatically incremented
    var id: Int? = null, // Optional parameter
    var title: String,
    var description: String,
    var user: String
)