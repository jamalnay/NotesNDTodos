package com.jamaln.notesndtodos.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.data.model.TagWithNotes
import com.jamaln.notesndtodos.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NOTEVIEWMODEL_TAG = "NoteViewModel"
@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel(){

    private val _notes = MutableStateFlow<List<TagWithNotes>>(emptyList())
    val notes: StateFlow<List<TagWithNotes>> = _notes

    private val _tags = MutableStateFlow<List<Tag>>(emptyList())
    val tags: StateFlow<List<Tag>> = _tags


    init {
        viewModelScope.launch {
            noteRepository.getTagsWithNotes()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Log.d(NOTEVIEWMODEL_TAG, "Error fetching tags", e)
                }.collect{
                    _tags.value = it
                }
        }
    }

    fun showNotesForTag(tag: Tag) {
        viewModelScope.launch {
            noteRepository.getTagWithNotes(tag.tagName)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Log.d(NOTEVIEWMODEL_TAG, "Error fetching notes", e)
                }
                .collect{
                _notes.value = it
            }
        }
    }


    fun insertRandomNotes(){
        val randomNotes = listOf(
            Pair(
                Note(0, "Work Note", "Work-related tasks",
                    listOf("work_image1.jpg", "work_image2.jpg"),
                    listOf("work_audio1.mp3", "work_audio2.mp3"), 1625140800000),
                listOf(Tag("work"),Tag("all"))
            ),
            Pair(
                Note(0, "Shopping List", "Groceries to buy",
                    listOf("shopping_image1.jpg", "shopping_image2.jpg"),
                    listOf("shopping_audio1.mp3", "shopping_audio2.mp3"), 1625227200000),
                listOf(Tag("personal"),Tag("all"))
            ),
            Pair(
                Note(0, "Travel Plans", "Plan for the next vacation",
                    listOf("travel_image1.jpg", "travel_image2.jpg"),
                    listOf("travel_audio1.mp3", "travel_audio2.mp3"), 1625313600000),
                listOf(Tag("travel"),Tag("all"))
            ),
            Pair(
                Note(0, "Meeting Notes", "Notes from the meeting",
                    listOf("meeting_image1.jpg", "meeting_image2.jpg"),
                    listOf("meeting_audio1.mp3", "meeting_audio2.mp3"), 1625400000000),
                listOf(Tag("work"), Tag("meetings"),Tag("all"))
            ),
            Pair(
                Note(0, "Recipe", "Chocolate cake recipe",
                    listOf("recipe_image1.jpg", "recipe_image2.jpg"),
                    listOf("recipe_audio1.mp3", "recipe_audio2.mp3"), 1625486400000),
                listOf(Tag("personal"), Tag("cooking"),Tag("all"))
            ),
            Pair(
                Note(0, "Fitness Routine", "Daily workout plan",
                    listOf("fitness_image1.jpg", "fitness_image2.jpg"),
                    listOf("fitness_audio1.mp3", "fitness_audio2.mp3"), 1625572800000),
                listOf(Tag("health"), Tag("fitness"),Tag("all"))
            ),
            Pair(
                Note(0, "Book Summary", "Summary of the book I'm reading",
                    listOf("book_image1.jpg", "book_image2.jpg"),
                    listOf("book_audio1.mp3", "book_audio2.mp3"), 1625659200000),
                listOf(Tag("personal"), Tag("reading"),Tag("all"))
            ),
            Pair(
                Note(0, "Project Ideas", "Ideas for the new project",
                    listOf("project_image1.jpg", "project_image2.jpg"),
                    listOf("project_audio1.mp3", "project_audio2.mp3"), 1625745600000),
                listOf(Tag("work"), Tag("ideas"),Tag("all"))
            ),
            Pair(
                Note(0, "Birthday Plans", "Plan for the birthday party",
                    listOf("birthday_image1.jpg", "birthday_image2.jpg"),
                    listOf("birthday_audio1.mp3", "birthday_audio2.mp3"), 1625832000000),
                listOf(Tag("personal"), Tag("events"),Tag("all"))
            ),
            Pair(
                Note(0, "Meditation Guide", "Steps for daily meditation",
                    listOf("meditation_image1.jpg", "meditation_image2.jpg"),
                    listOf("meditation_audio1.mp3", "meditation_audio2.mp3"), 1625918400000),
                listOf(Tag("health"), Tag("meditation"),Tag("all"))
            )
        )

        randomNotes.forEach { (note, tags) ->
            viewModelScope.launch {
                noteRepository.insertNoteWithTags(note, tags)
            }
        }




    }


}