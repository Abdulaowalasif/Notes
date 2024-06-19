package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.models.Notes


@Database(entities = [Notes::class], version = 2)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createdatabase(context).also {
                instance = it
            }
        }


        private fun createdatabase(context: Context) =
            Room.databaseBuilder(
                context, NoteDatabase::class.java,
                "notes_db"
            ).build()

    }
}