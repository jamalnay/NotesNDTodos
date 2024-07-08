package com.jamaln.notesndtodos.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "note_id") val noteId:Int,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "content_text") val contentText:String,
    @ColumnInfo(name = "content_images_paths") val contentImagesPaths:List<String>,
    @ColumnInfo(name = "content_audios_paths") val contentAudiosPaths:List<String>,
    @ColumnInfo(name = "update_date") val updateDate:Long
)