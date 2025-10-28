package com.example.noteapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.local.Note
import com.example.noteapp.data.repository.NoteRepository
import com.example.noteapp.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val noteRepository: NoteRepository, private val settingsRepository: SettingsRepository): ViewModel() {
    private val notesFlow= noteRepository.getAllNotes()
    private val settingsFlow= settingsRepository.isGridView()

    val uiState: StateFlow<HomeUiState> = combine(notesFlow, settingsFlow){notes, isGrid ->
        HomeUiState(notes= notes, isGridView = isGrid)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HomeUiState()
        )

    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun setGridView(isGrid: Boolean){
        viewModelScope.launch {
            settingsRepository.setGridView(isGrid)
        }
    }


}