package com.jamaln.notesndtodos.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun DeleteDialog(
    onCancelDelete: () -> Unit,
    onDeleteConfirm: () -> Unit,
    deleteMessage: String = "Are you sure you want to delete this Note?",
    deleteTitle: String = "Delete Note"
){
    AlertDialog(
        shape = RectangleShape,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        icon = {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "delete note icon",
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = deleteTitle,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = deleteMessage,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
            )

        },
        onDismissRequest = {
            onCancelDelete()
        },
        confirmButton = {
            TextButton(
                onClick = { onDeleteConfirm() }
            ) {
                Text(
                    text = "Delete",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancelDelete()
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