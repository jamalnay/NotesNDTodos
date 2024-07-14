package com.jamaln.notesndtodos.presentation.screens


import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.presentation.NoteViewModel
import com.jamaln.notesndtodos.presentation.components.DeleteDialog
import com.jamaln.notesndtodos.presentation.components.note.NewTagChip
import com.jamaln.notesndtodos.presentation.components.note.NewTagDialog
import com.jamaln.notesndtodos.presentation.components.note.NoteTextField
import com.jamaln.notesndtodos.presentation.components.note.TagInputChip
import com.jamaln.notesndtodos.presentation.events.NoteEvents
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme
import com.jamaln.notesndtodos.utils.Constants.ALL_NOTES_TAG
import com.jamaln.notesndtodos.utils.NoteUtils.Companion.timeStampToDate


@Composable
fun NoteScreen(
    noteId: Int,
    onNavigateBack: () -> Unit,
) {
    val noteViewModel: NoteViewModel = hiltViewModel()
    LaunchedEffect(key1 = Unit) {
        if (noteId != 0) {
            noteViewModel.onEvent(NoteEvents.OnGetNote(noteId))
        }
    }
    val noteState by noteViewModel.noteState.collectAsStateWithLifecycle()
    val titleState by noteViewModel.titleState.collectAsStateWithLifecycle()
    val contentTextState by noteViewModel.contentTextState.collectAsStateWithLifecycle()
    val editingState by noteViewModel.noteEditingState.collectAsStateWithLifecycle()
    val tagsState by noteViewModel.tagsState.collectAsStateWithLifecycle()
    val newTagState by noteViewModel.newTagState.collectAsStateWithLifecycle()
    val deleteDialogState by noteViewModel.deleteDialogState.collectAsStateWithLifecycle()
    val selectedTagsState by noteViewModel.selectedTagsState.collectAsStateWithLifecycle()

    ViewUpdateNoteContent(
        noteId = noteState.note.noteId,
        updateDate = noteState.note.updateDate,
        title = titleState.title,
        onTitleChange = { noteViewModel.onEvent(NoteEvents.OnTitleChange(it)) },
        contentText = contentTextState.noteContentText,
        onContentTextChange = { noteViewModel.onEvent(NoteEvents.OnContentTextChange(it)) },
        isEditing = editingState.isEditingNote,
        onEditNote = { noteViewModel.onEvent(NoteEvents.OnEditNote(it)) },
        onSaveChanges = { note, noteTags ->
            noteViewModel.onEvent(
                NoteEvents.OnSaveChanges(
                    note,
                    noteTags
                )
            )
        },
        tags = tagsState.tags,
        selectedTags = selectedTagsState.tags,
        onSelectTag = { tag -> noteViewModel.onEvent(NoteEvents.OnSelectTag(tag)) },
        onUnselectTag = { tag -> noteViewModel.onEvent(NoteEvents.OnUnselectTag(tag)) },
        onNavigateBack = onNavigateBack,
        onConfirmDelete = {
            noteViewModel.onEvent(NoteEvents.OnConfirmDeleteNote(noteState.note))
            onNavigateBack()
        },
        showNewTagDialog = newTagState.isNewTagDialogShown,
        newTageName = newTagState.tagName,
        onCreateNewTag = { noteViewModel.onEvent(NoteEvents.OnCreateNewTag) },
        onNewTagNameChange = { noteViewModel.onEvent(NoteEvents.OnNewTagNameChange(it)) },
        onSaveNewTag = { noteViewModel.onEvent(NoteEvents.OnSaveNewTag(it)) },
        onCancelCreatingNewTag = { noteViewModel.onEvent(NoteEvents.OnCancelCreatingNewTag) },
        onDeleteClick = { noteViewModel.onEvent(NoteEvents.OnDeleteClick) },
        onCancelDelete = { noteViewModel.onEvent(NoteEvents.OnCancelDelete) },
        showDeleteDialog = deleteDialogState.isDeleteDialogShown
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ViewUpdateNoteContent(
    noteId: Int,
    updateDate: Long,
    title: String,
    onTitleChange: (String) -> Unit,
    contentText: String,
    onContentTextChange: (String) -> Unit,
    isEditing: Boolean,
    onEditNote: (Boolean) -> Unit,
    onSaveChanges: (Note, List<Tag>) -> Unit,
    tags: List<Tag>,
    selectedTags: List<Tag>,
    onSelectTag: (Tag) -> Unit,
    onUnselectTag: (Tag) -> Unit,
    onNavigateBack: () -> Unit,
    showNewTagDialog: Boolean,
    newTageName: String,
    onCreateNewTag: () -> Unit,
    onNewTagNameChange: (String) -> Unit,
    onSaveNewTag: (String) -> Unit,
    onCancelCreatingNewTag: () -> Unit,
    onDeleteClick: () -> Unit,
    onCancelDelete: () -> Unit,
    onConfirmDelete: () -> Unit,
    showDeleteDialog: Boolean,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (isEditing) {
                        IconButton(
                            modifier = Modifier.padding(end = 16.dp),
                            onClick = {
                                onSaveChanges(
                                    Note(
                                        noteId,
                                        title,
                                        contentText,
                                        emptyList(),
                                        emptyList(),
                                        System.currentTimeMillis()
                                    ),
                                    selectedTags
                                )
                                onEditNote(false)
                                focusManager.clearFocus()
                                Toast.makeText(
                                    context,
                                    "Note saved successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Default.Done,
                                contentDescription = "Save changes",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, title + "\n" + contentText)
                                }
                                context.startActivity(
                                    Intent.createChooser(
                                        intent,
                                        "Share Note"
                                    )
                                )
                            }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share note"
                            )
                        }
                        IconButton(
                            onClick = {
                                onDeleteClick()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete note"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (showNewTagDialog) {
                focusManager.clearFocus()
                NewTagDialog(
                    onCancelCreatingNewTag = onCancelCreatingNewTag,
                    onSaveNewTag = { onSaveNewTag(newTageName) },
                    onTagNameChange = onNewTagNameChange,
                    tagName = newTageName
                )
            }
            if (showDeleteDialog) {
                focusManager.clearFocus()
                DeleteDialog(
                    onCancelDelete = { onCancelDelete() },
                    onDeleteConfirm = {
                        onConfirmDelete()
                        Toast.makeText(context, "Note delete...", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, top = 16.dp, end = 20.dp)
            ) {
                NewTagChip(onClick = { onCreateNewTag() })

                tags.forEach { tag ->
                    if (tag.tagName != ALL_NOTES_TAG.tagName)
                        TagInputChip(
                            tagName = ("#").plus(tag.tagName),
                            selected = tag in selectedTags,
                            onClick = {
                                if (tag in selectedTags) onUnselectTag(tag)
                                else onSelectTag(tag)
                                onEditNote(true)
                            }
                        )
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                text = timeStampToDate(updateDate),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )

            NoteTextField(text = title, isTitle = true) {
                onEditNote(true)
                onTitleChange(it)
            }
            NoteTextField(text = contentText, isTitle = false) {
                onEditNote(true)
                onContentTextChange(it)
            }
        }
    }
}

@PreviewDarkLight
@Composable
fun NoteScreenPreview() {
    NotesNDTodosTheme {
        val sampleNoteId = 1
        val sampleUpdateDate = System.currentTimeMillis()
        var sampleTitle by remember { mutableStateOf("Note Title") }
        var sampleContentText by remember { mutableStateOf("This is a sample content of the note.") }
        val sampleTags = listOf(Tag("Tag1"), Tag("Tag2"))

        ViewUpdateNoteContent(
            noteId = sampleNoteId,
            updateDate = sampleUpdateDate,
            title = sampleTitle,
            onTitleChange = { sampleTitle = it },
            contentText = sampleContentText,
            onContentTextChange = { sampleContentText = it },
            isEditing = false,
            onEditNote = { },
            onSaveChanges = { note, tags -> },
            tags = sampleTags,
            onNavigateBack = { },
            onDeleteClick = { },
            showNewTagDialog = false,
            newTageName = "",
            onCreateNewTag = { },
            onNewTagNameChange = { },
            onSaveNewTag = { },
            onCancelCreatingNewTag = { },
            onCancelDelete = { },
            onConfirmDelete = { },
            showDeleteDialog = false,
            onSelectTag = { },
            onUnselectTag = { },
            selectedTags = emptyList(),
        )
    }
}
