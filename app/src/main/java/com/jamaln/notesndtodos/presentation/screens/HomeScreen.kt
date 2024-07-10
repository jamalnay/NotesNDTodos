package com.jamaln.notesndtodos.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jamaln.notesndtodos.R
import com.jamaln.notesndtodos.presentation.NoteViewModel
import com.jamaln.notesndtodos.presentation.components.LargeNoteCard
import com.jamaln.notesndtodos.presentation.components.SearchBar
import com.jamaln.notesndtodos.presentation.components.TagFilterChip
import com.jamaln.notesndtodos.presentation.events.HomeEvents
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    toggleDarkMode: () -> Unit,
    noteViewModel: NoteViewModel = hiltViewModel(),
) {
    val notesState by noteViewModel.notesListState.collectAsStateWithLifecycle()
    val tagsState by noteViewModel.tagsState.collectAsStateWithLifecycle()
    val darkModeState by noteViewModel.darkModeState.collectAsStateWithLifecycle()
    val searchBarState by noteViewModel.searchBarState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(72.dp),
                shape = CircleShape,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "New note/todo")
            }
        },
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    value = searchBarState.searchQuery,
                    onValueChange = { noteViewModel.onEvent(HomeEvents.OnSearchQueryChange(it)) },
                    onQueryClear = { noteViewModel.onEvent(HomeEvents.OnSearchQueryClear) },
                    label = "Search...",
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 16.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                    onClick = {
                        noteViewModel.onEvent(HomeEvents.OnDarkModeToggle)
                        toggleDarkMode()
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (darkModeState.isInDarkMode) R.drawable.dark_mode
                            else R.drawable.light_mode
                        ),
                        contentDescription = "Dark/Light mode toggle"
                    )
                }
            }

        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
                .fillMaxSize(),
        ){
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    text = {
                        Text(
                            text = "Notes",
                            style = MaterialTheme.typography.displayMedium
                        )
                    },
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) } },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface
                )

                Tab(
                    selected = pagerState.currentPage == 1,
                    text = {
                        Text(
                            text = "ToDos",
                            style = MaterialTheme.typography.displayMedium
                        )
                    },
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) } },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface
                )
            }

            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) { page: Int ->
                if (page == 0) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                                .nestedScroll(scrollBehavior.nestedScrollConnection)
                                .horizontalScroll(rememberScrollState())
                                .padding(start = 20.dp, top = 16.dp, end = 20.dp),
                        ) {
                            //We want "All Notes" to be the first tag to appear
                            tagsState.tags.sortedWith(compareBy { it.tagName != "All Notes" }).forEach { tag ->
                                TagFilterChip(
                                    tagName = ("#").plus(tag.tagName),
                                    selected = tag == tagsState.selectedTag,
                                    onClick = { noteViewModel.onEvent(HomeEvents.GetNotesForTag(tag)) }
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
                            if (notesState.notes.notes.isNotEmpty()){
                                notesState.notes.notes.size.let { it ->
                                    items(count = it, key = { notesState.notes.notes[it].noteId } ) { index ->
                                        LargeNoteCard(
                                            modifier = Modifier.animateItemPlacement(),
                                            note = notesState.notes.notes[index],
                                            onClick = {}
                                        )
                                    }
                                }
                            }
                            item(
                                span = StaggeredGridItemSpan.FullLine
                            ) {
                                if (notesState.notes.notes.isEmpty()){
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(
                                            modifier = Modifier.padding(vertical = 48.dp),
                                            painter = painterResource(id = if (darkModeState.isInDarkMode) R.drawable.no_notes_dark
                                        else R.drawable.no_notes_light),
                                            contentDescription = ""
                                        )
                                        Text(text = "Oops!", style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Light))
                                        Text(text = "You have no notes", style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Light))
                                        Text(modifier = Modifier.padding(vertical = 48.dp), text = "Start by creating a new note.", style = MaterialTheme.typography.labelLarge)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}