package com.jamaln.notesndtodos.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.domain.repository.NoteRepository
import com.jamaln.notesndtodos.presentation.events.HomeEvents
import com.jamaln.notesndtodos.presentation.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

const val HOME_VIEWMODEL = "NoteViewModel"
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel(){

    private val _notesState = MutableStateFlow(HomeUiState.NotesListState())
    val notesState = _notesState.asStateFlow()

    private val _tagsState = MutableStateFlow(HomeUiState.TagsState())
    val tagsState = _tagsState.asStateFlow()

    private val _darkModeState = MutableStateFlow(HomeUiState.DarkModeState())
    val darkModeState = _darkModeState.asStateFlow()

    private val _searchBarState = MutableStateFlow(HomeUiState.SearchBarState())
    val searchBarState = _searchBarState.asStateFlow()

    private val _tabState = MutableStateFlow(HomeUiState.TabState())
    val tabState = _tabState.asStateFlow()


    init {
//        insertBulkNotes()
        onEvent(HomeEvents.GetAllNotes)
        onEvent(HomeEvents.GetAllTags)
    }

    fun onEvent(event: HomeEvents){
        when(event){
            is HomeEvents.GetAllNotes -> getAllNotes()
            is HomeEvents.GetAllTags -> getTags()
            is HomeEvents.GetNotesForTag -> getNotesForTag(tag = event.tag)
            is HomeEvents.OnSearchQueryChange -> onSearchQueryChange(query = event.query)
            is HomeEvents.OnSearchQueryClear -> onSearchQueryClear()
            is HomeEvents.OnDarkModeToggle -> onDarkModeToggle()
        }
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            noteRepository.getAllNotes().flowOn(Dispatchers.IO).catch { e ->
                Log.d(HOME_VIEWMODEL, "Error fetching notes", e)
            }.collect { notes ->
                _notesState.value = notesState.value.copy(notes = notes)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun onSearchQueryChange(query: String) {
        _searchBarState.value = searchBarState.value.copy(searchQuery = query)
        viewModelScope.launch {
            _searchBarState.debounce(300)
                .map { it.searchQuery }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    noteRepository.searchNotes(query)
                }
                .collect { notes ->
                    if(notes != null){
                        _notesState.value = notesState.value.copy(notes = notes)
                    }
                }
        }
    }
    private fun onSearchQueryClear() {
        _searchBarState.value = searchBarState.value.copy(searchQuery = "")
    }
    private fun onDarkModeToggle() {
        _darkModeState.value = darkModeState.value.copy(isInDarkMode = !darkModeState.value.isInDarkMode)
    }

    private fun getTags(){
        viewModelScope.launch {
            noteRepository.getTagsWithNotes()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Log.d(HOME_VIEWMODEL, "Error fetching tags", e)
                }.collect{
                    _tagsState.value = tagsState.value.copy(tags = it)
                }
        }
    }

    private fun getNotesForTag(tag: Tag) {
        _tagsState.value = tagsState.value.copy(selectedTag = tag)
        viewModelScope.launch {
            val notes = noteRepository.getTagWithNotes(tag.tagName)
            if (notes != null){
                _notesState.value = notesState.value.copy(notes = notes.notes)
            }
        }
    }


    private fun insertBulkNotes(){
        val randomNotes = listOf(
            Pair(
                Note(0, "Work Note", "Complete the project tasks assigned for this week",
                    listOf("work_image1.jpg", "work_image2.jpg"),
                    listOf("work_audio1.mp3", "work_audio2.mp3"), 1625140800000),
                listOf(Tag("Work"), Tag("All Notes"))
            ),
            Pair(
                Note(0, "Shopping List", "Buy milk, bread, eggs, and vegetables from the market",
                    listOf("shopping_image1.jpg", "shopping_image2.jpg"),
                    listOf("shopping_audio1.mp3", "shopping_audio2.mp3"), 1625227200000),
                listOf(Tag("Personal"), Tag("All Notes"))
            ),
            Pair(
                Note(0, "Travel Plans", "Book flights and hotels for the vacation trip next month",
                    listOf("travel_image1.jpg", "travel_image2.jpg"),
                    listOf("travel_audio1.mp3", "travel_audio2.mp3"), 1625313600000),
                listOf(Tag("Travel"), Tag("All Notes"))
            ),
            Pair(
                Note(0, "Meeting Notes", "Key points discussed during the weekly team meeting",
                    listOf("meeting_image1.jpg", "meeting_image2.jpg"),
                    listOf("meeting_audio1.mp3", "meeting_audio2.mp3"), 1625400000000),
                listOf(Tag("Work"), Tag("Meetings"), Tag("All Notes"))
            ),
            Pair(
                Note(0, "Recipe", "Chocolate cake recipe with detailed steps and ingredients",
                    listOf("recipe_image1.jpg", "recipe_image2.jpg"),
                    listOf("recipe_audio1.mp3", "recipe_audio2.mp3"), 1625486400000),
                listOf(Tag("Personal"), Tag("Cooking"), Tag("All Notes"))
            ),
            Pair(
                Note(0, "Fitness Routine", "Daily exercise plan including warm-ups and strength training",
                    listOf("fitness_image1.jpg", "fitness_image2.jpg"),
                    listOf("fitness_audio1.mp3", "fitness_audio2.mp3"), 1625572800000),
                listOf(Tag("Health"), Tag("Fitness"), Tag("All Notes"))
            ),
            Pair(
                Note(0, "Book Summary", "Summary of the current book I am reading",
                    listOf("book_image1.jpg", "book_image2.jpg"),
                    listOf("book_audio1.mp3", "book_audio2.mp3"), 1625659200000),
                listOf(Tag("Personal"), Tag("Reading"), Tag("All Notes"))
            ),
            Pair(
                Note(0, "Project Ideas", "Ideas for the new project proposal to be discussed",
                    listOf("project_image1.jpg", "project_image2.jpg"),
                    listOf("project_audio1.mp3", "project_audio2.mp3"), 1625745600000),
                listOf(Tag("Work"), Tag("Ideas"), Tag("All Notes"))
            ),
            Pair(
                Note(0, "Birthday Plans", "Plan for the birthday party including guests and decorations",
                    listOf("birthday_image1.jpg", "birthday_image2.jpg"),
                    listOf("birthday_audio1.mp3", "birthday_audio2.mp3"), 1625832000000),
                listOf(Tag("Personal"), Tag("Events"), Tag("All Notes"))
            ),
            Pair(
                Note(0, "Meditation Guide", "Steps for daily meditation practice to improve focus",
                    listOf("meditation_image1.jpg", "meditation_image2.jpg"),
                    listOf("meditation_audio1.mp3", "meditation_audio2.mp3"), 1625918400000),
                listOf(Tag("Health"), Tag("Meditation"), Tag("All Notes"))
            )
        )

        randomNotes.forEach { (note, tags) ->
            viewModelScope.launch {
                noteRepository.insertNoteWithTags(note, tags)
            }
        }
    }




}