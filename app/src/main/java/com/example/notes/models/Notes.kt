package com.example.notes.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var noteTitle: String,
    var noteDesc: String,
)