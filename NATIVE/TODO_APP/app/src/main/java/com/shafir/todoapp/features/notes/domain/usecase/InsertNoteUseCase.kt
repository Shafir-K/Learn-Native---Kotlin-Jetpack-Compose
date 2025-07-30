package com.shafir.todoapp.features.notes.domain.usecase

import com.shafir.todoapp.features.notes.domain.model.InvalidNoteException
import com.shafir.todoapp.features.notes.domain.model.Note
import com.shafir.todoapp.features.notes.domain.repository.NoteRepository

class InsertNoteUseCase(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title Cannot Be Empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Content Cannot Be Empty")
        }
        return repository.insertNote(note)
    }
}