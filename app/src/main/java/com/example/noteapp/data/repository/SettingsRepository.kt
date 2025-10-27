package com.example.noteapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.noteapp.data.settings.SettingsKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun setGridView(isGrid: Boolean){
        dataStore.edit { preferences ->
            preferences[SettingsKeys.IS_GRID_VIEW] = isGrid
        }
    }

    fun isGridView(): Flow<Boolean>{
        return dataStore.data
            .catch { exception->
                if (exception is IOException){
                    emit(emptyPreferences())
                }else{
                    throw exception
                }

            }
            .map { preferences ->
            preferences[SettingsKeys.IS_GRID_VIEW] ?: false
        }
    }
}