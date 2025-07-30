package com.shafir.todoapp.di

import android.app.Application
import androidx.room.Room
import com.shafir.todoapp.features.notes.data.data_source.NoteDatabase
import com.shafir.todoapp.features.notes.data.repository.NoteRepositoryImpl
import com.shafir.todoapp.features.notes.domain.repository.NoteRepository
import com.shafir.todoapp.features.notes.domain.usecase.DeleteNoteUseCase
import com.shafir.todoapp.features.notes.domain.usecase.GetNoteByIdUseCase
import com.shafir.todoapp.features.notes.domain.usecase.GetNotesUseCase
import com.shafir.todoapp.features.notes.domain.usecase.InsertNoteUseCase
import com.shafir.todoapp.features.notes.domain.usecase.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(app, NoteDatabase::class.java, NoteDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            getNoteById = GetNoteByIdUseCase(repository),
            insertNote = InsertNoteUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository)
        )
    }

}