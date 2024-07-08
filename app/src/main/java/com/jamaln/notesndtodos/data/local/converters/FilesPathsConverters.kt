package com.jamaln.notesndtodos.data.local.converters

import androidx.room.TypeConverter


class FilesPathsConverters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it }
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}