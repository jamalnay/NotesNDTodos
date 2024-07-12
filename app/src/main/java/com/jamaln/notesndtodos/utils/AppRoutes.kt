package com.jamaln.notesndtodos.utils

sealed class AppRoutes(val route: String) {
    data object NoteScreen : AppRoutes("note_screen")
    data object HomeScreen : AppRoutes("home_screen")
}