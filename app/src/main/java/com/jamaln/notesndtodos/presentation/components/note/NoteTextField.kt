package com.jamaln.notesndtodos.presentation.components.note

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.ContainerBox
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.jamaln.notesndtodos.presentation.preview.PreviewDarkLight
import com.jamaln.notesndtodos.presentation.theme.NotesNDTodosTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTextField(
    modifier: Modifier = Modifier,
    text: String,
    isTitle: Boolean,
    onValueChange: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val placeholderText = if (isTitle) "Note title" else "Note text"

    val textStyle = if (isTitle) MaterialTheme.typography.displayMedium.copy(
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Medium
    ) else MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)

    val placeHolderTextStyle = if (isTitle) MaterialTheme.typography.displayMedium.copy(
        color = MaterialTheme.colorScheme.onTertiaryContainer,
        fontWeight = FontWeight.Medium
    ) else MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onTertiaryContainer
    )

    //Wrapping text fields with rows with height(IntrinsicSize.Min)
    //a quick fix to resolve the cursor placement issue in textfields
    Row(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        BasicTextField(
            modifier = modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = textStyle,
            maxLines = if (isTitle) 3 else 30,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = text,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = false,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    isError = false,
                    label = null,
                    placeholder = {
                        Text(
                            text = placeholderText,
                            style = placeHolderTextStyle
                        )
                    },
                    leadingIcon = null,
                    trailingIcon = null,
                    prefix = null,
                    suffix = null,
                    supportingText = null,
                    shape = TextFieldDefaults.shape,
                    contentPadding = PaddingValues(0.dp),
                    container = {
                        ContainerBox(
                            enabled = true, isError = false, interactionSource, colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ), TextFieldDefaults.shape
                        )
                    },
                )
            }
        )
    }
}

@PreviewDarkLight
@Composable
fun NoteTextFieldPreview() {
    NotesNDTodosTheme {
        NoteTextField(text = "", isTitle = false) {}
    }
}