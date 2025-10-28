package com.example.noteapp.ui.detail

data class DetailUiState(
    val title: String= "",
    val content: String= "",
    val isLoading: Boolean= false,
    val noteSaved: Boolean= false
)