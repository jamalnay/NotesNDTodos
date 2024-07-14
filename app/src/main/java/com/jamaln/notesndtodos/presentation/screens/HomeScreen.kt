package com.jamaln.notesndtodos.presentation.screens


import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.data.model.Todo
import com.jamaln.notesndtodos.presentation.HomeViewModel
import com.jamaln.notesndtodos.presentation.components.DarkModeToggle
import com.jamaln.notesndtodos.presentation.components.SearchBar
import com.jamaln.notesndtodos.presentation.components.note.LargeNoteCard
import com.jamaln.notesndtodos.presentation.components.note.TagFilterChip
import com.jamaln.notesndtodos.presentation.components.todo.TodoBottomSheet
import com.jamaln.notesndtodos.presentation.components.todo.TodoCard
import com.jamaln.notesndtodos.presentation.events.HomeEvents
import com.jamaln.notesndtodos.presentation.state.HomeUiState
import com.jamaln.notesndtodos.presentation.state.Tabs
import com.jamaln.notesndtodos.utils.Constants.ALL_NOTES_TAG


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNoteClick: (Int) -> Unit,
) {

    val notesState by homeViewModel.notesState.collectAsStateWithLifecycle()
    val todosState by homeViewModel.todosListState.collectAsStateWithLifecycle()
    val tagsState by homeViewModel.tagsState.collectAsStateWithLifecycle()
    val searchBarState by homeViewModel.searchBarState.collectAsStateWithLifecycle()
    val isDarTheme by homeViewModel.darkModeState.collectAsStateWithLifecycle()
    val selectedTabState by homeViewModel.tabState.collectAsStateWithLifecycle()
    val newTodoTitleState by homeViewModel.todoTitle.collectAsStateWithLifecycle()
    val newTodoDescriptionState by homeViewModel.todoDescription.collectAsStateWithLifecycle()

    HomeContent(
        selectedTab = selectedTabState.selectedTab,
        onSelectTab = { homeViewModel.onEvent(HomeEvents.OnSelectTab(it)) },
        onNoteClick = onNoteClick,
        notes = notesState.notes,
        todos = todosState.todos,
        tagsState = tagsState,
        searchQuery = searchBarState.searchQuery,
        isDarkTheme = isDarTheme.isInDarkMode,
        onSearchBarQueryChange = { homeViewModel.onEvent(HomeEvents.OnSearchQueryChange(it)) },
        onSearchQueryClear = { homeViewModel.onEvent(HomeEvents.OnSearchQueryClear) },
        onDarkModeToggle = { homeViewModel.onEvent(HomeEvents.OnToggleDarkMode) },
        onGetNotesForTag = { homeViewModel.onEvent(HomeEvents.OnTagSelected(it)) },
        onCheckTodo = { homeViewModel.onEvent(HomeEvents.OnCheckTodo(it)) },
        newTodoTitle = newTodoTitleState.title,
        newTodoDescription = newTodoDescriptionState.description,
        onTodoTitleChange = { homeViewModel.onEvent(HomeEvents.OnTodoTitleChange(it)) },
        onTodoDescriptionChange = { homeViewModel.onEvent(HomeEvents.OnTodoDescriptionChange(it)) },
        onSaveTodo = { homeViewModel.onEvent(HomeEvents.OnSaveNewTodo(it)) }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    selectedTab: Tabs,
    onSelectTab: (Tabs) -> Unit,
    onNoteClick: (Int) -> Unit,
    notes: List<Note>,
    todos: List<Todo>,
    tagsState: HomeUiState.TagsState,
    searchQuery: String,
    isDarkTheme: Boolean,
    onSearchBarQueryChange: (String) -> Unit,
    onSearchQueryClear: () -> Unit,
    onDarkModeToggle: () -> Unit,
    onGetNotesForTag: (Tag) -> Unit,
    onCheckTodo: (Todo) -> Unit,
    newTodoTitle:String,
    newTodoDescription:String,
    onTodoTitleChange: (String) -> Unit,
    onTodoDescriptionChange: (String) -> Unit,
    onSaveTodo: (Todo) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    var showNewTodoSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(key1 = selectedTab) {
        pagerState.animateScrollToPage(selectedTab.ordinal)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(72.dp),
                shape = CircleShape,
                onClick = {
                    if (selectedTab == Tabs.Notes){
                        onNoteClick(0)
                    }else if (selectedTab == Tabs.Todos){
                        showNewTodoSheet = true
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "New note/todo"
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { paddingValues ->

        if(showNewTodoSheet){
            TodoBottomSheet(
                onDismiss = { showNewTodoSheet = false },
                todoTitle = newTodoTitle,
                todoDescription = newTodoDescription,
                onTitleChange = {onTodoTitleChange(it)},
                onDescriptionChange = {onTodoDescriptionChange(it)},
                onTodoSave = {
                    if (newTodoTitle.isNotEmpty()){
                        onSaveTodo(
                            Todo(
                                todoId = 0,
                                todoTitle = newTodoTitle,
                                todoDescription = newTodoDescription,
                                isChecked = false
                            )
                        )
                        onTodoTitleChange("")
                        onTodoDescriptionChange("")
                    }

                    Toast.makeText(context,"Todo saved successfully",Toast.LENGTH_SHORT).show()
                }
            )
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    value = searchQuery,
                    onValueChange = { onSearchBarQueryChange(it) },
                    onQueryClear = { onSearchQueryClear() },
                    label = "Search...",
                    modifier = Modifier.weight(1f)
                )
                DarkModeToggle(
                    isDarkTheme = isDarkTheme,
                    onDarkModeToggle = onDarkModeToggle
                )
            }
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Tab(
                    selected = pagerState.currentPage == Tabs.Notes.ordinal,
                    text = {
                        Text(
                            text = "Notes",
                            style = MaterialTheme.typography.displaySmall
                        )
                    },
                    onClick = { onSelectTab(Tabs.Notes) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface
                )

                Tab(
                    selected = pagerState.currentPage == Tabs.Todos.ordinal,
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "ToDos",
                                style = MaterialTheme.typography.displaySmall
                            )
                            Text(
                                text = "(${todos.size})",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    },
                    onClick = { onSelectTab(Tabs.Todos) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface
                )
            }

            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) { page: Int ->
                if (page == Tabs.Notes.ordinal) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                                .horizontalScroll(rememberScrollState())
                                .padding(start = 20.dp, top = 16.dp, end = 20.dp),
                        ) {
                            //We want "All Notes" to be the first tag to in the tags list
                            tagsState.tags.sortedWith(compareBy { it.tagName != ALL_NOTES_TAG.tagName })
                                .forEach { tag ->
                                    TagFilterChip(
                                        tagName = ("#").plus(tag.tagName),
                                        selected = tag == tagsState.selectedTag,
                                        onClick = { onGetNotesForTag(tag) }
                                    )
                                }
                        }
                        LazyVerticalStaggeredGrid(
                            modifier = Modifier
                                .padding(start = 20.dp, end = 20.dp)
                                .fillMaxSize(1f)
                                .background(MaterialTheme.colorScheme.surface),
                            contentPadding = PaddingValues(vertical = 20.dp),
                            columns = StaggeredGridCells.Adaptive(280.dp),
                            verticalItemSpacing = 24.dp,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (notes.isNotEmpty()) {
                                items(count = notes.size, key = { notes[it].noteId }) { index ->
                                    LargeNoteCard(
                                        modifier = Modifier,
                                        note = notes[index],
                                        onClick = { onNoteClick(notes[index].noteId) }
                                    )
                                }
                            }
                        }
                    }
                } else if (page == Tabs.Todos.ordinal) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val sortedTodos = todos.sortedBy { it.isChecked }

                        LazyVerticalStaggeredGrid(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface),
                            contentPadding = PaddingValues(vertical = 20.dp),
                            columns = StaggeredGridCells.Adaptive(280.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (sortedTodos.isNotEmpty()) {
                                items(count = sortedTodos.size, key = { sortedTodos[it].todoId }) { index ->
                                    TodoCard(
                                        todo = sortedTodos[index],
                                        onTodoChecked = { onCheckTodo(sortedTodos[index]) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
