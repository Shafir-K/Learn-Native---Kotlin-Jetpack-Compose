package com.shafir.todoapp.features.notes.presentation.notes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shafir.todoapp.features.notes.domain.model.Note
import com.shafir.todoapp.features.notes.domain.usecase.NoteUseCases
import com.shafir.todoapp.features.notes.domain.util.NoteOrder
import com.shafir.todoapp.features.notes.domain.util.OrderType
import com.shafir.todoapp.features.notes.presentation.notes.NotesEvent.DeleteNote
import com.shafir.todoapp.features.notes.presentation.notes.NotesEvent.Order
import com.shafir.todoapp.features.notes.presentation.notes.NotesEvent.RestoreNote
import com.shafir.todoapp.features.notes.presentation.notes.NotesEvent.ToggleOrderSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _state = mutableStateOf(NotesState())
    val state: MutableState<NotesState> = _state
    private var recentlyDeleteNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }


    fun onEvent(event: NotesEvent) {
        when (event) {
            is DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeleteNote = event.note
                }
            }

            is Order -> {
                if (state.value.noteOrder::class == event.noteOrder && state.value.noteOrder.orderType == event.noteOrder.orderType) {
                    return
                }
                getNotes(event.noteOrder)

            }

            is RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.insertNote(recentlyDeleteNote ?: return@launch)
                    recentlyDeleteNote = null
                }
            }

            is ToggleOrderSection -> {
                _state.value =
                    state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder).onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }

}