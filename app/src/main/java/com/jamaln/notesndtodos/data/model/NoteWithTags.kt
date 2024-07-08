package com.jamaln.notesndtodos.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation


data class NoteWithTags(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "note_id",
        entityColumn = "tag_name",
        associateBy = Junction(NoteTagCrossRef::class)
    ) val tags: List<Tag>
)
