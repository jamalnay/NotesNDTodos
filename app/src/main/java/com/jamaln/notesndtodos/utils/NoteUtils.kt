package com.jamaln.notesndtodos.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class NoteUtils {
    companion object {

        fun capitalizeFirstLetter(tag: String): String {
            if (tag.isEmpty()) return tag
            return tag[0].uppercaseChar() + tag.substring(1)
        }

        fun timeStampToDate(timestamp: Long): String {
            val format = "dd/MMM/yyyy"
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(Date(timestamp))
        }
    }
}