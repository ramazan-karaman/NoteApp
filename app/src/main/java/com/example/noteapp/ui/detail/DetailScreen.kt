package com.example.noteapp.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(onNavigateBack: ()-> Unit){
    val viewModel: DetailViewModel= hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.noteSaved) {
        if (uiState.noteSaved){
            onNavigateBack()
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector= Icons.Default.ArrowBack,
                            contentDescription = "Geri Git"
                        )
                    }
                },
                title = {
                    Text(text =if(viewModel.isNewNote) {
                        "Yeni Not"
                    }else{
                        "Notu Düzenle"
                    })
                },
                actions = {
                    IconButton(onClick = {viewModel.saveNote()}) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Kaydet"
                        )
                    }
                }
            )
        }
    ){ paddingValues ->
        if (uiState.isLoading){
            CircularProgressIndicator()
        }else{
            Column(Modifier.padding(paddingValues).padding(16.dp)) {
                TextField(value = uiState.title,
                    onValueChange = {viewModel.onTitleChange(it)},
                    label = {Text("Başlık")},
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(value = uiState.content,
                    onValueChange = {viewModel.onContentChange(it)},
                    label = {Text("İçerik")},
                    modifier = Modifier.fillMaxWidth().weight(1f))
            }
        }

    }
}