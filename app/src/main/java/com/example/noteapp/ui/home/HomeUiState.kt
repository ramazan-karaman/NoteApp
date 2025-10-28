package com.example.noteapp.ui.home

import com.example.noteapp.data.local.Note

data class HomeUiState(
    val notes: List<Note> = emptyList(),
    val isGridView: Boolean= false
)
