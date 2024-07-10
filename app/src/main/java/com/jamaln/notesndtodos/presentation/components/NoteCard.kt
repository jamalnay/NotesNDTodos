package com.jamaln.notesndtodos.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: (Int) -> Unit
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = RoundedCornerShape(0.dp),
        onClick = { onClick(note.noteId) }
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp,top = 12.dp, end = 12.dp, bottom = 4.dp),
            text = note.title,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            modifier = Modifier.padding(start = 12.dp,top = 0.dp, end = 12.dp, bottom = 20.dp),
            text = note.contentText,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 4
        )
    }
}

@Composable
fun LargeNoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: (Int) -> Unit
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = RoundedCornerShape(0.dp),
        onClick = { onClick(note.noteId) },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp,top = 20.dp, end = 12.dp, bottom = 4.dp),
            text = note.title,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            modifier = Modifier.padding(start = 12.dp,top = 0.dp, end = 12.dp, bottom = 20.dp),
            text = note.contentText,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 4
        )
    }
}

@PreviewDarkLight
@Composable
fun NoteCardPreview(){
    NotesNDTodosTheme{
        val notes = listOf(
            Note(0, "Work Note", "Integrate features such as due dates, priority levels, and categories to help users organize their tasks efficiently.",
                listOf("work_image1.jpg", "work_image2.jpg"),
                listOf("work_audio1.mp3", "work_audio2.mp3"), 1625140800000),
            Note(0, "Recipe of the french gatto", "Consider how you might use a chip like this.",
                listOf("recipe_image1.jpg", "recipe_image2.jpg"),
                listOf("recipe_audio1.mp3", "recipe_audio2.mp3"), 1625486400000),
            Note(0, "Shopping List", "Implement a simple, interactive onboarding process to guide new users.",
                listOf("shopping_image1.jpg", "shopping_image2.jpg"),
                listOf("shopping_audio1.mp3", "shopping_audio2.mp3"), 1625227200000),
            Note(0, "Travel Plans", "In designing a Todos app, the primary goal is to create a user-friendly and intuitive interface.",
                listOf("travel_image1.jpg", "travel_image2.jpg"),
                listOf("travel_audio1.mp3", "travel_audio2.mp3"), 1625313600000),
            Note(0, "Recipe", "Consider how you might use a chip.",
                listOf("recipe_image1.jpg", "recipe_image2.jpg"),
                listOf("recipe_audio1.mp3", "recipe_audio2.mp3"), 1625486400000),
            Note(0, "Meeting Notes", "The following implementation demonstrates an input chip that is already in a selected state." ,
                listOf("meeting_image1.jpg", "meeting_image2.jpg"),
                listOf("meeting_audio1.mp3", "meeting_audio2.mp3"), 1625400000000),
            Note(0, "First Ever Written Note", "Consider how you might use a chip",
                listOf("recipe_image1.jpg", "recipe_image2.jpg"),
                listOf("recipe_audio1.mp3", "recipe_audio2.mp3"), 1625486400000),
        )
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            columns = StaggeredGridCells.Adaptive(120.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(notes.size){ index->
                NoteCard(note = notes[index], onClick = {})
            }
        }
    }
}