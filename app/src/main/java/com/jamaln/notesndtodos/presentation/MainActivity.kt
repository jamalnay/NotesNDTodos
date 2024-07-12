package com.jamaln.notesndtodos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            var darkTheme by remember { mutableStateOf(false) }
            NotesNDTodosTheme(
                darkTheme = darkTheme
            ) {
                NavGraph(navController = navController,)
            }

        }
    }
}