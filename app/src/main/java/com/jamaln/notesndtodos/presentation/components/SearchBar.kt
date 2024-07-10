package com.jamaln.notesndtodos.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onQueryClear:() -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    var placeholder by remember { mutableStateOf(label) }
    var hidePlaceholder by remember { mutableStateOf(false) }
    val placeholderAlpha by animateFloatAsState(
        if (hidePlaceholder) 0.1f else 1f,
        label = "",
        finishedListener = { placeholder = if (hidePlaceholder) "" else label })

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .onFocusChanged {
                if (it.isFocused) {
                    hidePlaceholder = true
                } else {
                    hidePlaceholder = false
                    placeholder = label
                }
            },
        value = value,
        onValueChange = onValueChange,
        trailingIcon = { if (placeholder == "" || value != ""){
            IconButton(
                onClick = {
                onQueryClear()
                focusManager.clearFocus()
            }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = ""
                )
            }
        } },
        leadingIcon = { Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "") },
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
        placeholder = { Text(
            modifier = Modifier.alpha(placeholderAlpha),
            text = placeholder,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Light),
            color = MaterialTheme.colorScheme.onTertiaryContainer
        ) },
        singleLine = true,
        shape = RoundedCornerShape(0.dp),
        textStyle = MaterialTheme.typography.titleMedium
    )
}

@PreviewDarkLight
@Composable
fun SearchBarPreview() {
    var text by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    NotesNDTodosTheme {
        Column {
            SearchBar(
                value = text,
                label = "Search your notes and todos",
                onValueChange = {text = it},
                onQueryClear = {text = ""}
            )
            SearchBar(
                value = text2,
                label = "Search your notes and todos",
                onValueChange = {text2 = it},
                onQueryClear = {text2 = ""}
            )
        }
    }
}
