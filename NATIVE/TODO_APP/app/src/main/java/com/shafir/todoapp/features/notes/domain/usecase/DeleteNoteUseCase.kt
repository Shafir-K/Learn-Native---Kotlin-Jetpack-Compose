package com.shafir.todoapp.features.notes.domain.usecase

import com.shafir.todoapp.features.notes.domain.model.Note
import com.shafir.todoapp.features.notes.domain.repository.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        return repository.deleteNote(note)
    }
}