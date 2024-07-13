package com.jamaln.notesndtodos.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jamaln.notesndtodos.R

@Composable
fun DarkModeToggle(
    isDarkTheme: Boolean,
    onDarkModeToggle: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(start = 4.dp, end = 16.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        onClick = {
            onDarkModeToggle()
        }
    ) {
        Icon(
            painter = painterResource(
                id = if (isDarkTheme) R.drawable.dark_mode
                else R.drawable.light_mode
            ),
            contentDescription = "Dark/Light mode toggle"
        )
    }
}