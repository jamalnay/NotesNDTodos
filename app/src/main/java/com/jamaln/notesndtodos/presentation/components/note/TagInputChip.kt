package com.jamaln.notesndtodos.presentation.components.note

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme

@Composable
fun TagInputChip(
    modifier: Modifier = Modifier,
    tagName:String,
    selected: Boolean,
    onClick:(String) -> Unit,
){
    FilterChip(
        modifier = modifier.padding(end = 16.dp),
        selected = selected,
        onClick = { onClick(tagName) },
        label = {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = tagName,
                style = MaterialTheme.typography.labelLarge
            )
        },
        shape = RoundedCornerShape(0.dp),
        border = null,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            selectedContainerColor = MaterialTheme.colorScheme.secondary,
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedLabelColor = MaterialTheme.colorScheme.onSurface,
            selectedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
        ),
        trailingIcon = {
            if(selected){
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "tag selected",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}


@PreviewDarkLight
@Composable
fun TagInputChipPreview(){
    NotesNDTodosTheme{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .horizontalScroll(rememberScrollState())
        ){
            TagInputChip(tagName = "#Personl", selected = false, onClick = {})
            TagInputChip(tagName = "#Vecation", selected = true, onClick = {})
            TagInputChip(tagName = "#Work", selected = false, onClick = {})
        }
    }
}