package com.jamaln.notesndtodos.presentation.components.todo

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jamaln.notesndtodos.data.model.Todo
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme

@Composable
fun TodoCard(
    modifier: Modifier = Modifier,
    todo: Todo,
    onTodoChecked: (Int) -> Unit,
    onClick: (Int) -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .animateContentSize(),
        shape = RectangleShape,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        onClick = { onClick(todo.todoId) }
    ) {
        TodoTitle(
            todo = todo,
            onChecked = {todoId -> onTodoChecked(todoId)}
        )
        
        Text(
            modifier = Modifier.padding(start = 14.dp, end = 12.dp, bottom = 12.dp),
            text = todo.todoDescription,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = if (todo.isChecked) 0.5f else 1f),
            overflow = TextOverflow.Ellipsis
        )
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
                onClick = {}
            )
            TodoCard(
                todo = Todo(
                    todoId = 1,
                    todoTitle = "Todo Title",
                    todoDescription = "You can use this list to populate your data for testing purposes. Each Todo object has a unique title and description, with a mix of checked and unchecked items.",
                    isChecked = true),
                onTodoChecked = {},
                onClick = {}
            )
        }
    }
}