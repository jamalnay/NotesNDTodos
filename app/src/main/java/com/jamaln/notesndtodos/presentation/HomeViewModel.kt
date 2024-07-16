package com.jamaln.notesndtodos.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jamaln.notesndtodos.data.local.datasources.BulkDataSamples.sampleNotesWithTags
import com.jamaln.notesndtodos.data.local.datasources.BulkDataSamples.sampleTodosWithSubTodos
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.data.model.Todo
import com.jamaln.notesndtodos.di.DispatchersModule
import com.jamaln.notesndtodos.domain.repository.NoteRepository
import com.jamaln.notesndtodos.domain.repository.PreferencesRepository
import com.jamaln.notesndtodos.domain.repository.TodoRepository
import com.jamaln.notesndtodos.presentation.events.HomeEvents
import com.jamaln.notesndtodos.presentation.state.HomeUiState
import com.jamaln.notesndtodos.presentation.state.NoteUiState
import com.jamaln.notesndtodos.presentation.state.Tabs
import com.jamaln.notesndtodos.utils.Constants.ALL_NOTES_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

const val HOME_VIEWMODEL = "NoteViewModel"
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val todoRepository: TodoRepository,
    @DispatchersModule.IoDispatcher private val iODispatcher: CoroutineDispatcher,
    private val preferencesRepository: PreferencesRepository
) : ViewModel(){
    private val _searchBarState = MutableStateFlow(HomeUiState.SearchBarState())
    val searchBarState = _searchBarState.asStateFlow()

    private val _darkModeState = MutableStateFlow(HomeUiState.DarkModeState())
    val darkModeState = _darkModeState.asStateFlow()

    private val _tabState = MutableStateFlow(HomeUiState.TabState())
    val tabState = _tabState.asStateFlow()

    private val _notesState = MutableStateFlow(HomeUiState.NotesListState())
    val notesState = _notesState.asStateFlow()

    private val _tagsState = MutableStateFlow(HomeUiState.TagsState())
    val tagsState = _tagsState.asStateFlow()

    private val _todosListState = MutableStateFlow(HomeUiState.TodosListState())
    val todosListState = _todosListState.asStateFlow()

    private val _uncheckedTodosCount = MutableStateFlow(0)
    val uncheckedTodosCount = _uncheckedTodosCount.asStateFlow()

    private val _currentTodo = MutableStateFlow(Todo(todoId = 0, todoTitle = "", todoDescription = "", isChecked = false))
    val currentTodo = _currentTodo.asStateFlow()

    private val _isInDeleteMode = MutableStateFlow(HomeUiState.DeleteModeState())
    val isInDeleteMode = _isInDeleteMode.asStateFlow()

    private val _todosDeleteListState = MutableStateFlow(HomeUiState.TodosListState())
    val todosDeleteListState = _todosDeleteListState.asStateFlow()

    private val _todoDeleteDialogState = MutableStateFlow(HomeUiState.TodoDeleteDialogState())
    val todoDeleteDialogState = _todoDeleteDialogState.asStateFlow()






    init {
//        insertBulkNotes()
        onEvent(HomeEvents.OnGetDarkModeState)
        onEvent(HomeEvents.CountUncheckedTodos)
        onEvent(HomeEvents.GetAllTags)
        onEvent(HomeEvents.GetAllNotes)
        onEvent(HomeEvents.GetAllTodos)
    }

    fun onEvent(event: HomeEvents){
        when(event){
            is HomeEvents.GetAllNotes -> getAllNotes()
            is HomeEvents.GetAllTags -> getTags()
            is HomeEvents.OnTagSelected -> getNotesForTag(tag = event.tag)
            is HomeEvents.OnSearchQueryChange -> onSearchQueryChange(query = event.query)
            is HomeEvents.OnSearchQueryClear -> onSearchQueryClear()
            is HomeEvents.OnGetDarkModeState -> getDarkModeState()
            is HomeEvents.OnToggleDarkMode -> onDarkModeToggle()
            is HomeEvents.GetAllTodos -> getAllTodos()
            is HomeEvents.OnCheckTodo -> onCheckTodo(event.todo)
            is HomeEvents.OnSaveTodo -> onSaveTodo(todo = event.todo)
            is HomeEvents.OnSelectTab -> onSelectTab(event.tab)
            is HomeEvents.OnTodoDescriptionChange -> onTodoDescriptionChange(event.description)
            is HomeEvents.OnTodoTitleChange -> onTodoTitleChange(event.title)
            is HomeEvents.CountUncheckedTodos -> getUncheckedTodosCount()
            is HomeEvents.OnEditTodo -> onEditTodo(event.todo)
            is HomeEvents.SetIsInDeleteMode -> setIsInDeleteMode(event.isInDeleteMode)
            is HomeEvents.OnDeleteTodos -> onDeleteTodos()
            is HomeEvents.SetSelectedForDelete -> setSelectedForDelte(event.todo)
            is HomeEvents.SelectAllForDeleteToggle -> selectAllForDeleteToggle()
            is HomeEvents.OnCancelTodosDelete -> onCancelTodosDelete()
            is HomeEvents.OnConfirmTodosDelete -> onConfirmTodosDelete(event.todosList)
        }
    }

    private fun onCancelTodosDelete() {
        _todoDeleteDialogState.value = todoDeleteDialogState.value.copy(isDeleteDialogShown = false)
    }

    private fun onDeleteTodos() {
        _todoDeleteDialogState.value = todoDeleteDialogState.value.copy(isDeleteDialogShown = true)
    }
    private fun onConfirmTodosDelete(todosList:List<Todo>) {
        viewModelScope.launch {
            todosList.forEach {
                todoRepository.deleteTodo(it)
            }
            _todosDeleteListState.value = todosDeleteListState.value.copy(todos = emptyList())
            _todoDeleteDialogState.value = todoDeleteDialogState.value.copy(isDeleteDialogShown = false)
        }
    }

    private fun selectAllForDeleteToggle() {
        if (todosDeleteListState.value.todos.isEmpty()){
            _todosDeleteListState.value = todosListState.value.copy(
                todos = todosListState.value.todos
            )
            return
        }
        _todosDeleteListState.value = todosDeleteListState.value.copy(
            todos = emptyList()
        )
    }

    private fun setSelectedForDelte(todo: Todo) {
        if (todosDeleteListState.value.todos.contains(todo)){
            _todosDeleteListState.value = todosDeleteListState.value.copy(
                todos = todosDeleteListState.value.todos - todo
            )
            return
        }

        _todosDeleteListState.value = todosDeleteListState.value.copy(
            todos = todosDeleteListState.value.todos + todo
        )
    }



    private fun setIsInDeleteMode(inDeleteMode: Boolean) {
        _isInDeleteMode.value = isInDeleteMode.value.copy(isInDeleteMode = inDeleteMode)
        if (!inDeleteMode){
            _todosDeleteListState.value = todosDeleteListState.value.copy(todos = emptyList())
        }
    }

    private fun onEditTodo(todo: Todo) {
        _currentTodo.value = todo.copy(
            todoId = todo.todoId,
            todoTitle = todo.todoTitle,
            todoDescription = todo.todoDescription,
            isChecked = todo.isChecked
        )
    }

    private fun getUncheckedTodosCount() {
        viewModelScope.launch {
            todoRepository.countCheckedTodos().flowOn(iODispatcher).catch { e ->
                Log.d(HOME_VIEWMODEL, "Error counting todos", e)
            }.collect{ count ->
                _uncheckedTodosCount.value = count
            }
        }
    }

    private fun onTodoTitleChange(title: String) {
        _currentTodo.value = currentTodo.value.copy(todoTitle = title)
    }

    private fun onTodoDescriptionChange(description: String) {
        _currentTodo.value = currentTodo.value.copy(todoDescription = description)
    }

    private fun onSelectTab(tab: Tabs) {
        _tabState.value = tabState.value.copy(selectedTab = tab)
    }


    private fun onCheckTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.insertTodo(todo.copy(isChecked = !todo.isChecked))
        }
    }


    private fun onSaveTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.insertTodo(todo)
        }
    }

    private fun getAllTodos() {
        viewModelScope.launch {
            todoRepository.getAllTodos().flowOn(iODispatcher).catch { e ->
                Log.d(HOME_VIEWMODEL, "Error fetching todos", e)
            }.collect{ todos ->
                _todosListState.value = todosListState.value.copy(todos = todos)
            }
        }
    }

    private fun getDarkModeState() {
        viewModelScope.launch {
            preferencesRepository.isDarkTheme().collect{ isDarkTheme ->
                _darkModeState.value = darkModeState.value.copy(isInDarkMode = isDarkTheme)
            }
        }
    }

    private fun onDarkModeToggle() {
        viewModelScope.launch {
            preferencesRepository.toggleDarkLight()
        }
    }



    private fun getAllNotes() {
        viewModelScope.launch {
            noteRepository.getAllNotes().flowOn(iODispatcher).catch { e ->
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
                }.flowOn(iODispatcher)
                .catch { e->
                    Log.d(HOME_VIEWMODEL, "Error searching notes", e)
                    emitAll(flowOf(emptyList()))
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
        _tagsState.value = tagsState.value.copy(selectedTag = ALL_NOTES_TAG)
    }


    private fun getTags(){
        viewModelScope.launch {
            noteRepository.getTagsWithNotes()
                .flowOn(iODispatcher)
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
        sampleNotesWithTags.forEach { (note, tags) ->
            viewModelScope.launch {
                noteRepository.insertNoteWithTags(note, tags)
            }
        }

        viewModelScope.launch {
            sampleTodosWithSubTodos.forEach { todo ->
                todoRepository.insertTodo(todo)
            }
        }
    }




}