package com.jamaln.notesndtodos.presentation.components.todo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    todoTitle: String ="",
    todoDescription: String = "",
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onTodoSave: () -> Unit,
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }

    ModalBottomSheet(
        sheetState = sheetState,
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = {
            onDismiss()
            showBottomSheet = false
        },
        shape = RectangleShape,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        dragHandle = {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
            ) {
                TodoTextField(
                    text = todoTitle,
                    isTitle = true,
                    onValueChange = {onTitleChange(it)}
                )
                TodoTextField(
                    modifier = Modifier.padding(top = 8.dp),
                    text = todoDescription,
                    isTitle = false,
                    onValueChange = {onDescriptionChange(it)}
                )
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                onClick = {
                    scope.launch {
                        onTodoSave()
                        onDismiss()
                        sheetState.hide()
                    }
                          },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "todo save button"
                )
            }
        }
    }
}


@PreviewDarkLight
@Composable
fun TodoBottomSheetPreview(){
    NotesNDTodosTheme {
        TodoBottomSheet(
            onDismiss = {},
            todoTitle = "",
            todoDescription = "",
            onTitleChange = {},
            onDescriptionChange = {},
            onTodoSave = {}
        )
    }
}