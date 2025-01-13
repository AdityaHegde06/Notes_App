package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.Screen.AddNoteScreen
import com.example.notesapp.Screen.NoteScreen
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.ui_layer.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

//This annotation is necessary for the Hilt dependency injection framework.
// It tells Hilt to inject the dependencies in this activity (like NoteViewModel)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //enables the use of the full screen on devices that support
        // it (edge-to-edge UI) for a more immersive experience.
        enableEdgeToEdge()
        //sets the UI of the activity using Jetpack Compose. It defines the structure of the app's UI,
        setContent{
            NotesAppTheme { // Applies custom theme for the app.

                //Scaffold is a layout component that provides basic material design structure, such as a top app bar, floating action button,
               // and handling of padding when the system UI changes (like status bars or navigation bars).
                Scaffold (modifier = Modifier. fillMaxSize()){  innerPadding ->
                //    hiltViewModel is used to get the instance of the NoteViewModel provided by Hilt.
                    //    This ViewModel contains the app’s logic for handling notes.

                    val viewModel = hiltViewModel<NoteViewModel>()
                    //rememberNavController() creates and remembers a navigation controller used to manage navigation
                    // between composables (screens).
                    val navController = rememberNavController()

                    //NavHost:
                    //
                    //NavHost defines the navigation structure. It declares which composables (screens)
                    // can be navigated to and specifies the starting screen (NoteScreen).
                         NavHost(
                             navController = navController,
                             startDestination = NoteScreen,

                             //Added inner padding
                             //The innerPadding value is provided by Scaffold and is passed down to the navigation host. It ensures that the navigation content respects the screen's insets,
                             // such as the status bar, navigation bar, or other UI elements.
                             modifier = Modifier.padding(innerPadding)
                             ) {
                          // composable<NoteScreen> defines the UI for the main screen where notes are listed.
                             // It takes navController, state, and onEvent as parameters.
                             composable<NoteScreen> {
                                 NoteScreen(navController = navController, state = viewModel.state.value, onEvent =viewModel::onEvent)
                             }

                          composable<AddNoteScreen>{
                              AddNoteScreen(navController = navController,state = viewModel.state.value, onEvent = viewModel::onEvent)
                         }
                         }

                     }
                }
            }
















            }

        }


      sealed   class Screen(){
          @Serializable
          object NoteScreen

        @Serializable
        object AddNoteScreen

      }