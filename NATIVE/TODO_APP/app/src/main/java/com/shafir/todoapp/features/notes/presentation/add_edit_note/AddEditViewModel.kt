package com.shafir.todoapp.features.notes.presentation.add_edit_note

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shafir.todoapp.features.notes.domain.model.InvalidNoteException
import com.shafir.todoapp.features.notes.domain.model.Note
import com.shafir.todoapp.features.notes.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject() constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteTitle = mutableStateOf<NoteTextFieldState>(
        NoteTextFieldState(
            hint = "Enter Title"
        )
    )
    val noteTitle: MutableState<NoteTextFieldState> = _noteTitle
    private val _noteContent = mutableStateOf<NoteTextFieldState>(
        NoteTextFieldState(
            hint = "Enter Content"
        )
    )
    val noteContent: MutableState<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val noteColor: MutableState<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val evenFlow = _eventFlow.asSharedFlow()

    private var currenNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("NoteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteById(noteId)?.also { note ->
                        currenNoteId = note.id
                        _noteTitle.value = _noteTitle.value.copy(note.title)
                        _noteContent.value = _noteContent.value.copy(note.content)
                        _noteColor.value = note.color

                    }
                }
            }
        }
    }

    fun OnEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            is AddEditNoteEvent.ChangedContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && _noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangedTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && _noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = _noteTitle.value.copy(
                    text = event.value
                )
            }

            AddEditNoteEvent.saveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.insertNote(
                            Note(
                                noteTitle.value.text,
                                noteContent.value.text,
                                System.currentTimeMillis(),
                                noteColor.value,
                                id = currenNoteId,
                            )
                        )

                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(e.message ?: "Couldn't Save Note")
                        )

                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}