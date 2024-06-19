package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notes.models.Notes


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(notes: Notes)

    @Update
    suspend fun updateNote(notes: Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)

    @Query("Select * from Notes order by id desc")
    fun getAllNote(): LiveData<List<Notes>>


    @Query("Select * from Notes where noteTitle like :query or noteDesc like :query")
    fun searchNote(query: String?): LiveData<List<Notes>>

}