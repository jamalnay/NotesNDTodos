package com.jamaln.notesndtodos.presentation.components.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.jamaln.notesndtodos.data.model.Todo
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme


@Composable
fun TodoTitle(
    modifier: Modifier = Modifier,
    todo: Todo,
    onChecked: (Int) -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = todo.isChecked,
            onCheckedChange = { onChecked(todo.todoId) },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.tertiaryContainer,
                uncheckedColor = MaterialTheme.colorScheme.tertiaryContainer,
                checkmarkColor = MaterialTheme.colorScheme.primary,
                disabledCheckedColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledUncheckedColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledIndeterminateColor = MaterialTheme.colorScheme.primary
            ),
            enabled = enabled
        )
        Text(
            text = todo.todoTitle,
            color = MaterialTheme.colorScheme.primary
                .copy(alpha = if (todo.isChecked) 0.75f else 1f),
            style = MaterialTheme.typography.bodyLarge
                .copy(fontWeight = FontWeight.SemiBold),
            maxLines = 1
        )

    }

}

@PreviewDarkLight
@Composable
fun TodoTitlePreview() {
    NotesNDTodosTheme {
        Column {
            TodoTitle(
                todo = Todo(
                    todoTitle = "checked",
                    todoId = 1,
                    isChecked = true,
                    todoDescription = ""
                ),
                onChecked = {})
            TodoTitle(
                todo = Todo(
                    todoTitle = "unchecked",
                    todoId = 1,
                    isChecked = false,
                    todoDescription = ""
                ),
                onChecked = {})
        }
    }
}