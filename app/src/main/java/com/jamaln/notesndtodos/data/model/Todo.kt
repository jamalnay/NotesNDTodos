package com.jamaln.notesndtodos.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "todo_id") val todoId:Int,
    @ColumnInfo(name = "todo_title") val todoTitle:String,
    @ColumnInfo(name = "todo_description") val todoDescription:String,
    @ColumnInfo(name = "is_checked") val isChecked:Boolean,
)
