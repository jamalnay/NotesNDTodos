package com.jamaln.notesndtodos.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jamaln.notesndtodos.presentation.navigation.home
import com.jamaln.notesndtodos.presentation.navigation.note
import com.jamaln.notesndtodos.utils.AppRoutes

@Composable
fun NavGraph(
    navController: NavHostController,
    isDarTheme: Boolean,
    onDarkModeToggle: ()->Unit
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.HomeScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(400)) },
        exitTransition = { fadeOut(animationSpec = tween(400)) }
    ) {
        home(navController, isDarTheme, onDarkModeToggle)
        note(navController)
    }
}