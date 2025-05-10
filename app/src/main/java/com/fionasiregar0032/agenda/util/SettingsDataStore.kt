package com.fionasiregar0032.agenda.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class SettingsDataStore(private val context: Context) {
    companion object {
        val IS_LIST_MODE = booleanPreferencesKey("is_list_mode")
    }

    val layoutModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[IS_LIST_MODE] ?: true }

    suspend fun saveLayoutMode(isListMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIST_MODE] = isListMode
        }
    }
}
