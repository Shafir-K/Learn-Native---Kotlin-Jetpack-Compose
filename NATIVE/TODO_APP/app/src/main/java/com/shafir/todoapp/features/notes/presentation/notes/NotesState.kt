package com.shafir.todoapp.features.notes.presentation.notes

import com.shafir.todoapp.features.notes.domain.model.Note
import com.shafir.todoapp.features.notes.domain.util.NoteOrder
import com.shafir.todoapp.features.notes.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
)
