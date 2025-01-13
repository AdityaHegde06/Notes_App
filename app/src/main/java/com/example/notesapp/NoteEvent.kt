package com.example.notesapp

sealed interface NoteEvent {

     object SortNotes : NoteEvent
     data  class DeleteNote(val note: Int) :NoteEvent
    data class SaveNote(val title : String ,var description : String) : NoteEvent
}