package com.example.noteapp.data.repository

import com.example.noteapp.data.local.Note
import com.example.noteapp.data.local.NoteDao
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteRepositoryTest {

    @RelaxedMockK
    private lateinit var noteDao: NoteDao

    private lateinit var noteRepository: NoteRepository

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        noteRepository= NoteRepository(noteDao)
    }

    @Test
    fun `insertNote çağrıldığında, dao insertNote fonksiyonunu çağırmalıdır`(){
        runTest {
            val testNote= Note(id = 1, title = "Test", content = "Test içerik", timestamp = 123L)

            noteRepository.insertNote(testNote)

            coVerify {
                noteDao.insertNote(testNote)
            }
        }
    }

    @Test
    fun `deleteNote çağrıldığında, dao deleteNote fonksiyonunu çağırmalıdır`() {
        runTest {
            val testNote = Note(id = 1, title = "Test", content = "Test içerik", timestamp = 123L)
            noteRepository.deleteNote(testNote)
            coVerify(exactly = 1) {
                noteDao.deleteNote(testNote)
            }
        }
    }
}