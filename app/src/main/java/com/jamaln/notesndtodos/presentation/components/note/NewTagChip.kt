package com.jamaln.notesndtodos.presentation.components.note

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme

@Composable
fun NewTagChip(
    modifier: Modifier = Modifier,
    onClick:() -> Unit,
){
    FilterChip(
        modifier = modifier.padding(end = 16.dp),
        selected = false,
        onClick = { onClick() },
        label = {},
        shape = RoundedCornerShape(0.dp),
        border = null,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            selectedContainerColor = MaterialTheme.colorScheme.secondary,
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedLabelColor = MaterialTheme.colorScheme.onSurface
        ),
        trailingIcon = {
            Icon(
                modifier = Modifier.padding(
                    start = 0.dp,
                    end = 12.dp,
                    top = 6.dp,
                    bottom = 6.dp
                ),
                imageVector = Icons.Default.Add,
                contentDescription = "new tag",
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        elevation = FilterChipDefaults.filterChipElevation(elevation = 1.dp)
    )
}

@PreviewDarkLight
@Composable
fun NewTagButtonPreview(){
    NotesNDTodosTheme {
        NewTagChip(onClick = {})
    }
}