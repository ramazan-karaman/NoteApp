package com.example.noteapp.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.example.noteapp.data.repository.NoteRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    @RelaxedMockK
    private lateinit var noteRepository: NoteRepository

    private lateinit var viewModel: DetailViewModel

    private val testDispatcher= StandardTestDispatcher()

    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        viewModel= DetailViewModel(
            noteRepository= noteRepository,
            savedStateHandle = SavedStateHandle()
        )
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `saveNote çağrıldığında, repository insertNote fonksiyonunu çağırmalıdır`() {
        runTest {
            val testTitle = "Test Başlık"
            val testContent = "Test İçerik"
            viewModel.onTitleChange(testTitle)
            viewModel.onContentChange(testContent)

            viewModel.saveNote()
            testDispatcher.scheduler.advanceUntilIdle()

            coVerify(exactly = 1) {
                noteRepository.insertNote(
                    match { note ->
                        note.title == testTitle && note.content == testContent
                    }
                )
            }
        }
    }
}