package com.example.notesapp.data_layer


import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface NoteDao {

    @Upsert
    fun upsert(note: Note)

    @Delete
    fun delete(note: Note)

    //FOR SELECTING THE NOTE  ON BASIS OF  TITLE AS WELL AS  TITLE
    @Query("SELECT * FROM note  ORDER by dateCreated ASC")
    fun getAllOrderedDateAdded(): Flow<List<Note>>

    @Query("Select * FROM note ORDER by title ASC")
    fun getAllOrderedTitle(): Flow<List<Note>>
}