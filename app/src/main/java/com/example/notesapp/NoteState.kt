package com.example.notesapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.notesapp.data_layer.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    var title: MutableState<String> = mutableStateOf(""),
    var description: MutableState<String> = mutableStateOf(""),
    val isLoading: Boolean = false
 )





