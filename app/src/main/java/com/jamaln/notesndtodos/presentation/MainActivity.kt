package com.jamaln.notesndtodos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val navController = rememberNavController()
            val darkModeState by mainViewModel.darkModeState.collectAsStateWithLifecycle()
            NotesNDTodosTheme(
                darkTheme = darkModeState.isInDarkMode
            ) {
                NavGraph(
                    navController = navController,
                    darkModeState.isInDarkMode,
                    mainViewModel::onDarkModeToggle
                )
            }
        }
    }
}