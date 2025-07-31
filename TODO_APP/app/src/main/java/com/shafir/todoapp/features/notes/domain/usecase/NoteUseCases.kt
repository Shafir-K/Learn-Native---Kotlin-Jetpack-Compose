package com.shafir.todoapp.features.notes.domain.usecase

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val getNoteById: GetNoteByIdUseCase,
    val insertNote: InsertNoteUseCase,
    val deleteNote: DeleteNoteUseCase
)



