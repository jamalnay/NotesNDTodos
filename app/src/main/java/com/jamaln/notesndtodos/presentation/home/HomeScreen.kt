package com.jamaln.notesndtodos.presentation.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jamaln.notesndtodos.presentation.PreviewDarkLight
import com.jamaln.notesndtodos.ui.theme.NotesNDTodosTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

enum class AppTabs { NOTES, TODOS }

@Composable
fun HomeScreen(){

    HomeContent()
}


@OptIn(ExperimentalMaterial3Api::class)
@PreviewDarkLight
@Composable
fun HomeContent(){
    NotesNDTodosTheme {
    var selectedTab by remember { mutableStateOf(AppTabs.NOTES) }
    val selectedColorAnimation by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.primary,
        label = ""
    )
    val unselectedColorAnimation by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.onSecondary,
        label = ""
    )

        SecondaryTabRow(
            modifier = Modifier.padding(horizontal = 20.dp),
            selectedTabIndex = if(selectedTab == AppTabs.NOTES) 0 else 1
        ) {

            Tab(
                selected = selectedTab == AppTabs.NOTES,
                text = {
                    Text(
                        text = "Notes",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.W400
                        ),
                        color = if(selectedTab == AppTabs.NOTES) selectedColorAnimation else unselectedColorAnimation
                    )
                },
                onClick = {
                    selectedTab = AppTabs.NOTES
                },
                interactionSource = object : MutableInteractionSource {
                    override val interactions: Flow<Interaction> = emptyFlow()

                    override suspend fun emit(interaction: Interaction) {}

                    override fun tryEmit(interaction: Interaction) = true
                }
            )

            Tab(
                selected = selectedTab == AppTabs.TODOS,
                text = {
                    Text(
                        text = "ToDos",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.W400
                        ),
                        color = if(selectedTab == AppTabs.TODOS) selectedColorAnimation else unselectedColorAnimation
                    )
                },
                onClick = {
                    selectedTab = AppTabs.TODOS
                },
                interactionSource = object : MutableInteractionSource {
                    override val interactions: Flow<Interaction> = emptyFlow()

                    override suspend fun emit(interaction: Interaction) {}

                    override fun tryEmit(interaction: Interaction) = true
                }
            )

        }
    }

}