package com.jamaln.notesndtodos.data.local.datasources

import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.data.model.Todo

object BulkDataSamples {
    val sampleNotesWithTags = listOf(
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
    val sampleTodosWithSubTodos = listOf(
        Todo(
            todoId = 0,
            todoTitle = "Finish Android Project",
            todoDescription = "Complete the Android project by implementing the remaining features and fixing bugs.",
            isChecked = false
        ),
        Todo(
            todoId = 0,
            todoTitle = "Grocery Shopping",
            todoDescription = "Buy milk, eggs, bread, and vegetables for the week.",
            isChecked = true
        ),
        Todo(
            todoId = 0,
            todoTitle = "Workout Routine",
            todoDescription = "Complete a 30-minute workout session, including cardio and strength training.",
            isChecked = false
        ),
        Todo(
            todoId = 0,
            todoTitle = "Read a Book",
            todoDescription = "Finish reading the last 2 chapters of the book 'Atomic Habits'.",
            isChecked = true
        ),
        Todo(
            todoId = 0,
            todoTitle = "Plan Vacation",
            todoDescription = "Research and plan the itinerary for the upcoming vacation trip.",
            isChecked = false
        ),
        Todo(
            todoId = 0,
            todoTitle = "Clean the House",
            todoDescription = "Vacuum the living room, clean the kitchen, and organize the bedroom.",
            isChecked = false
        ),
        Todo(
            todoId = 0,
            todoTitle = "Attend Team Meeting",
            todoDescription = "Join the team meeting to discuss the project status and next steps.",
            isChecked = true
        ),
        Todo(
            todoId = 0,
            todoTitle = "Cook Dinner",
            todoDescription = "Prepare a healthy and delicious dinner with the ingredients bought from the store.",
            isChecked = false
        )
    )
}