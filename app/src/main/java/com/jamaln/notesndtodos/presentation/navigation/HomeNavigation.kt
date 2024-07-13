package com.jamaln.notesndtodos.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jamaln.notesndtodos.presentation.screens.HomeScreen
import com.jamaln.notesndtodos.utils.AppRoutes

fun NavGraphBuilder.home(
    navController: NavController,
    isDarTheme: Boolean,
    onDarkModeToggle: ()->Unit
) {
    composable(AppRoutes.HomeScreen.route) {
        HomeScreen(
            isDarTheme = isDarTheme,
            onDarkModeToggle = onDarkModeToggle,
            onNoteClick = {noteId -> navController.navigate(AppRoutes.NoteScreen.route + "/$noteId")}
        )
    }
}