package com.jamaln.notesndtodos.presentation.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview


@Preview(
    name = "Preview Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0A1120
)

@Preview(
    name = "Preview Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF9FBFF
)


annotation class PreviewDarkLight