package com.shafir.todoapp.features.notes.domain.usecase

import com.shafir.todoapp.features.notes.domain.model.Note
import com.shafir.todoapp.features.notes.domain.repository.NoteRepository

class GetNoteByIdUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}