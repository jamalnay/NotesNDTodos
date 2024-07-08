package com.jamaln.notesndtodos.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tags")
data class Tag(
    @PrimaryKey @ColumnInfo(name = "tag_name")val tagName:String
)
