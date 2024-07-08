package com.jamaln.notesndtodos.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class TagWithNotes(
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "tag_name",
        entityColumn = "note_id",
        associateBy = Junction(NoteTagCrossRef::class)
    ) val notes: List<Note>
)
