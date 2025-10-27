package com.example.noteapp.data.repository

import com.example.noteapp.data.local.Note
import com.example.noteapp.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()
    suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id)
    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    suspend fun deleteNote(note: Note)= noteDao.deleteNote(note)
}