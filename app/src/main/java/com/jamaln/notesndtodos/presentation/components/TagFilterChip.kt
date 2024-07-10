package com.jamaln.notesndtodos.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme

@Composable
fun TagFilterChip(
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
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal
            )
        },
        shape = RoundedCornerShape(0.dp),
        border = null,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedLabelColor = if (!isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.onSurface
        ),
        elevation = FilterChipDefaults.filterChipElevation(elevation = 1.dp)
    )
}

@Composable
fun NewInputShipButton(){

}

@PreviewDarkLight
@Composable
fun TagFilterChipPreview(){
    NotesNDTodosTheme{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .horizontalScroll(rememberScrollState())
        ){
            TagFilterChip(tagName = "All Notes", selected = true, onClick = {})
            TagFilterChip(tagName = "#Personl", selected = false, onClick = {})
            TagFilterChip(tagName = "#Vecation", selected = false, onClick = {})
            TagFilterChip(tagName = "#Work", selected = false, onClick = {})
        }
    }
}