package com.jamaln.notesndtodos.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.data.model.TagWithNotes
import com.jamaln.notesndtodos.presentation.components.NoteCard
import com.jamaln.notesndtodos.presentation.components.TagFilterChip


@Composable
fun NoteTabContent(
    notesPagingItems: LazyPagingItems<TagWithNotes>,
    tags: List<Tag>,
    selectedTag: Tag,
    onNoteClick: (Int) -> Unit,
    onTagSelected: (Tag) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(start = 20.dp,top = 16.dp,end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ){
            tags.forEach { tag->
                TagFilterChip(
                    tagName = tag.tagName,
                    selected = tag == selectedTag,
                    onClick = { onTagSelected(tag) }
                )
            }
        }
        LazyVerticalStaggeredGrid(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            columns = StaggeredGridCells.Adaptive(120.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(notesPagingItems.itemCount){ index->
                notesPagingItems[index].let { noteWithTags ->
                    noteWithTags?.notes?.let{ notes->
                        notes.forEach { note ->
                            NoteCard(
                                note = note,
                                onClick = {onNoteClick(note.noteId)}
                            )
                        }
                    }
                }
            }
        }
    }
}