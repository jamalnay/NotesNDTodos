package com.jamaln.notesndtodos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jamaln.notesndtodos.presentation.screens.HomeScreen
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        setContent {
            var darkTheme by remember { mutableStateOf(false) }
            NotesNDTodosTheme(
                darkTheme = darkTheme
            ) {
                HomeScreen(
                    toggleDarkMode = { darkTheme = !darkTheme }
                )
            }

        }
    }
}