package com.example.notesapp

import android.R.attr.description
import android.R.attr.title
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    state: NoteState,
    onEvent: (NoteEvent) -> Unit
) {
    Scaffold(
        topBar = {
            // A Row composable is used for the top bar
            // which contains the title "Notes App" and an icon button for sorting notes.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Notes App",
                    modifier = Modifier.weight(1f),
                    fontSize = 17.sp
                )
                // The IconButton triggers the SortNotes event when clicked.
                IconButton(onClick = { onEvent(NoteEvent.SortNotes) }) {
                    Icon(imageVector = Icons.AutoMirrored.Rounded.Sort, contentDescription = null)
                }
            }
        },
        // When clicked, it navigates to the "add_note_screen" to add a new note.
        // This uses navController.navigate("add_note_screen") to navigate.
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_note_screen") }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) {
        // The LazyColumn is used to display a list of notes efficiently.
        // It ensures that only the visible items are rendered.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = it
        ) {
            items(state.notes) { note ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Column (
                        modifier = Modifier.weight(1f)
                    ){
                        Text(
                            text = title.toString(), // You can customize the text style
                        )
                        Text(
                            text = description.toString(),
                          // Optional: Add spacing between title and description
                        )
                    }
                    IconButton(onClick = {
                        onEvent(NoteEvent.DeleteNote(note))
                    }) {
                        Icon(imageVector = Icons.Rounded.Delete, contentDescription = null)
                    }
                }
            }
        }
    }
}










