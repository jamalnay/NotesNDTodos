package com.jamaln.notesndtodos.utils

class AppUtils {
    companion object {
        fun capitalizeFirstLetter(tag: String): String {
            if (tag.isEmpty()) return tag
            return tag[0].uppercaseChar() + tag.substring(1)
        }
    }
}