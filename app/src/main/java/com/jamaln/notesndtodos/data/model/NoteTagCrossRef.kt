package com.jamaln.notesndtodos.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["note_id", "tag_name"])
data class NoteTagCrossRef(
    @ColumnInfo(name = "note_id",index = true) val noteId: Int,
    @ColumnInfo(name = "tag_name",index = true)  val tagName: String
)
