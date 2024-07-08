package com.jamaln.notesndtodos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.ColumnInfo
import com.jamaln.notesndtodos.presentation.home.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: NoteViewModel = hiltViewModel()
            val tagWithNotes by viewModel.notes.collectAsStateWithLifecycle()
            val tags by viewModel.tags.collectAsStateWithLifecycle()
            Box(modifier = Modifier.fillMaxSize()){
                Button(modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp),
                    onClick = { viewModel.insertRandomNotes() }) {
                    Text("insert notes")
                }
                FlowRow(
                    Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 80.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (tags.isNotEmpty()) {
                        tags.forEach { tag ->
                            Button(modifier = Modifier.padding(2.dp), onClick = { viewModel.showNotesForTag(tag) }) {
                                Text(tag.tagName)
                            }
                        }
                    }
                }

                if (tagWithNotes.isNotEmpty()) {
                    tagWithNotes.forEach { tagWithNote ->
                        Column(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 320.dp)
                        ) {
                            Text(text = tagWithNote.tag.tagName, fontWeight = FontWeight.Bold)
                            tagWithNote.notes.forEach { note ->
                                Text(text = note.title)
                            }
                        }
                    }
                }
            }
        }
    }
}