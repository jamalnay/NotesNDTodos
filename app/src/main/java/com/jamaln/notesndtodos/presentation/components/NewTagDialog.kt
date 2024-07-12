package com.jamaln.notesndtodos.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme

@Composable
fun NewTagDialog(
    onCancelCreatingNewTag: () -> Unit,
    onTagNameChange: (String) -> Unit,
    tagName:String,
    onSaveNewTag: () -> Unit,
){
    AlertDialog(
        shape = RectangleShape,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "New #Tag",
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        text = {
            NewTagTextField(
                onTagNameChange = onTagNameChange,
                tagName = tagName
            )

        },
        onDismissRequest = {
            onCancelCreatingNewTag()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (tagName.isNotEmpty() && tagName.all { it.isLetterOrDigit() }) onSaveNewTag()
                    else onCancelCreatingNewTag()
                }
            ) {
                Text(
                    text = "Save",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancelCreatingNewTag()
                }
            ) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    )
}


@Composable
fun NewTagTextField(
    modifier: Modifier = Modifier,
    tagName:String ,
    onTagNameChange: (String) -> Unit,
){
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp),
        value = tagName,
        onValueChange = {onTagNameChange(it)},
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiaryContainer,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        shape = RoundedCornerShape(0.dp),
        textStyle = MaterialTheme.typography.titleMedium,
        placeholder = { Text(text = "Tag name", style = MaterialTheme.typography.titleMedium,color = MaterialTheme.colorScheme.onTertiaryContainer)}
    )
}

@PreviewDarkLight
@Composable
fun NewTagDialogPreview(){
    NotesNDTodosTheme {
        NewTagDialog(
            onCancelCreatingNewTag = {},
            onSaveNewTag = {},
            onTagNameChange = {},
            tagName = "Tag name"
        )
    }
}

@PreviewDarkLight
@Composable
fun NewTagTextFieldPreview(){
    NotesNDTodosTheme {
        NewTagTextField(
            onTagNameChange = {},
            tagName = "Tag name"
        )
    }
}