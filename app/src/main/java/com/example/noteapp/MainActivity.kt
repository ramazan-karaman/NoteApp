package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.ui.Screen
import com.example.noteapp.ui.detail.DetailScreen
import com.example.noteapp.ui.home.HomeScreen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color= MaterialTheme.colorScheme.background) {
                    val navController= rememberNavController()

                    NavHost(
                        navController= navController,
                        startDestination = Screen.Home
                    ){
                        composable(route= Screen.Home){
                            HomeScreen(
                                onAddNewNoteClick = {
                                    navController.navigate("${Screen.Detail}?${Screen.NOTE_ID_ARG}=-1")
                                },

                                onNoteClick = { noteId->
                                    navController.navigate("${Screen.Detail}?${Screen.NOTE_ID_ARG}=$noteId")
                                }
                            )
                        }

                        composable(route = "${Screen.Detail}?${Screen.NOTE_ID_ARG}={${Screen.NOTE_ID_ARG}}",
                            arguments = listOf(navArgument(Screen.NOTE_ID_ARG){
                                type= NavType.IntType
                                defaultValue= -1
                            }
                            )
                        ) {
                            DetailScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NoteAppTheme {
        Greeting("Android")
    }
}