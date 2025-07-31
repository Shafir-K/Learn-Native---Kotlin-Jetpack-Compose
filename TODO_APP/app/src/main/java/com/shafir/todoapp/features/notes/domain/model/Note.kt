package com.shafir.todoapp.features.notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shafir.todoapp.ui.theme.BabyBlue
import com.shafir.todoapp.ui.theme.LightGreen
import com.shafir.todoapp.ui.theme.RedOrange
import com.shafir.todoapp.ui.theme.RedPink
import com.shafir.todoapp.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
    }
}

class InvalidNoteException(message: String) : Exception(message)