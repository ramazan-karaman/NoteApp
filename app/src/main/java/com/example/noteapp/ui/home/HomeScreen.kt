package com.example.noteapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onAddNewNoteClick: ()-> Unit,
               onNoteClick: (Int)-> Unit,
               viewModel: HomeViewModel= hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title= {Text("NoteApp")},
                actions = {
                    IconButton(onClick = {viewModel.setGridView(!uiState.isGridView)}) {
                        val icon= if (uiState.isGridView){
                            Icons.Default.List
                        }else{
                            Icons.Default.Star
                        }

                        Icon(imageVector = icon, contentDescription = "Görünümü Değiştir")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNewNoteClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Yeni Not Ekle")
            }
        }
    ) {paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            if (uiState.notes.isEmpty()){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text("Henüz not eklenmemiş.")
                }
            }else{
                if (uiState.isGridView){
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(uiState.notes){note->
                            NoteItem(
                                note = note,
                                onNoteClick= onNoteClick,
                            )
                        }
                    }
                }else{
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(uiState.notes){note->
                            NoteItem(
                                note= note,
                                onNoteClick= onNoteClick,
                                modifier= Modifier.fillParentMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}