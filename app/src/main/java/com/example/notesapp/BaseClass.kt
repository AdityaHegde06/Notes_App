package com.example.notesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


//This annotation tells Hilt to generate the required components for dependency injection in the app.
//It needs to be placed on the Application class, which is the entry point for your application.
@HiltAndroidApp
class BaseClass : Application() {
}