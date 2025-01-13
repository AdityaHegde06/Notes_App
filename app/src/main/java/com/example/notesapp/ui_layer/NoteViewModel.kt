package com.example.notesapp.ui_layer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.NoteEvent
import com.example.notesapp.NoteState
import com.example.notesapp.data_layer.Note
import com.example.notesapp.data_layer.NoteDatabase

import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


//The NoteViewModel manages the app's data and logic for the Notes screen.
// It interacts with the database and handles user actions like saving, deleting, and sorting notes.

 class NoteViewModel @Inject constructor(noteDatabase: NoteDatabase): ViewModel() {
     //Accessing DAO
     //Retrieves the Data Access Object (DAO) to perform database operations (insert, delete, query).
     val dao = noteDatabase.noteDao
     //A MutableStateFlow that tracks whether notes are sorted by date (true) or by title (false).
      private val isSortedByDateAdded = MutableStateFlow(true)
      private val notes = isSortedByDateAdded.flatMapLatest{

          //flatMapLatest: Switches between fetching notes by date or title depending sSortedByDateAdded.
          //stateIn: Keeps the data updated and active while the UI is subscribed.
             if(it){
            dao.getAllOrderedDateAdded()
             }else{
             dao.getAllOrderedTitle()
        }


    }.stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(), emptyList())

 //Application state
     //_state: Internal state holding the current UI data
  val _state = MutableStateFlow(NoteState())
     //state: Exposed combined state that updates the UI when notes or sorting change
     val state= combine(_state,isSortedByDateAdded,notes) {state, isSortedByDateAdded, notes ->
    state.copy(
        notes = notes
    )

}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

  fun onEvent(event  : NoteEvent){
      when(event){
          is NoteEvent.DeleteNote -> {
               viewModelScope.launch {
                   dao.delete(event.note)

               }
          }
          is NoteEvent.SaveNote -> {

              viewModelScope.launch{
                  val note = Note(
                      title = state.value.title.value,
                      description = state.value.description.value,
                      dateCreated = System.currentTimeMillis().toString(),

                  )
                  viewModelScope.launch{ dao .upsert(note)}
              }
          }

          NoteEvent.SortNotes -> {

              isSortedByDateAdded.value = !isSortedByDateAdded.value
          }
      }
  }
    
}

