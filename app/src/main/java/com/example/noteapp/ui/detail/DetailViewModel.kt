package com.example.noteapp.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.local.Note
import com.example.noteapp.data.repository.NoteRepository
import com.example.noteapp.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val noteRepository: NoteRepository, private val savedStateHandle: SavedStateHandle) : ViewModel(){

    private val _uiState= MutableStateFlow(DetailUiState())

    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val noteId: Int = savedStateHandle.get(Screen.NOTE_ID_ARG) ?: -1

    val isNewNote: Boolean= noteId== -1

    init {
        if (noteId != -1){
            _uiState.value= _uiState.value.copy(isLoading = true)
            viewModelScope.launch {
                val note= noteRepository.getNoteById(noteId)
                if (note != null){
                    _uiState.value= _uiState.value.copy(
                        title = note.title,
                        content = note.content,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onTitleChange(title: String){
        _uiState.value= _uiState.value.copy(title= title)
    }

    fun onContentChange(content: String){
        _uiState.value= _uiState.value.copy(content= content)
    }

    fun saveNote(){
        viewModelScope.launch {
            val noteToSave= Note(
                id = if (noteId == -1) 0 else noteId,
                title = _uiState.value.title,
                content = _uiState.value.content,
                timestamp = System.currentTimeMillis()
            )

            noteRepository.insertNote(noteToSave)
            _uiState.value= _uiState.value.copy(noteSaved = true)
        }
    }
}