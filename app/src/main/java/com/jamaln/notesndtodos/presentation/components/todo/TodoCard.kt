package com.jamaln.notesndtodos.presentation.components.todo

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jamaln.notesndtodos.data.model.Todo
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoCard(
    modifier: Modifier = Modifier,
    todo: Todo,
    onTodoChecked: (Int) -> Unit,
    onClick: (Int) -> Unit,
    activateDeleteMode: () -> Unit,
    isInDeleteMode: Boolean = false,
    isSelectedForDelete: Boolean = false,
    setSelectedForDelete: (Todo) -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .combinedClickable(
                onClick = { onClick(todo.todoId) },
                onLongClick = { activateDeleteMode() }
            ),
        shape = RectangleShape,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier.weight(1f)

            ) {
                TodoTitle(
                    todo = todo,
                    onChecked = {todoId -> onTodoChecked(todoId)}
                )

                Text(
                    modifier = Modifier.padding(start = 14.dp, end = 28.dp, bottom = 12.dp),
                    text = todo.todoDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = if (todo.isChecked) 0.5f else 1f),
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (isInDeleteMode){
                IconButton(
                    modifier = Modifier.padding(end = 12.dp).size(28.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    onClick = { setSelectedForDelete(todo) }
                ) {
                    if (isSelectedForDelete)
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                }
            }
        }

    }
}

@PreviewDarkLight
@Composable
fun TodoCardPreview(){
    NotesNDTodosTheme {
        Column {
            TodoCard(
                todo = Todo(
                    todoId = 1,
                    todoTitle = "Todo Title",
                    todoDescription = "Research and plan the itinerary for the upcoming vacation trip.",
                    isChecked = false),
                onTodoChecked = {},
                onClick = {},
                activateDeleteMode = {},
                isInDeleteMode = false,
                isSelectedForDelete = false,
                setSelectedForDelete = {}
            )
            TodoCard(
                todo = Todo(
                    todoId = 1,
                    todoTitle = "Todo Title",
                    todoDescription = "You can use this list to populate your data for testing purposes. Each Todo object has a unique title and description, with a mix of checked and unchecked items.",
                    isChecked = true),
                onTodoChecked = {},
                onClick = {},
                activateDeleteMode = {},
                isInDeleteMode = true,
                isSelectedForDelete = true,
                setSelectedForDelete = {}
            )
        }
    }
}