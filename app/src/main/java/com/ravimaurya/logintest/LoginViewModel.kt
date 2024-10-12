package com.ravimaurya.logintest

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {

    val isLoggedIn = MutableStateFlow(false)


    // function to observer login status
   fun observeStatus(context: Context){
        viewModelScope.launch {
            isUserLoggedIn(context).collectLatest { status ->
                isLoggedIn.value = status
            }
        }

    }


    // function to login user
    fun login(context: Context){

            viewModelScope.launch {
                setUserLoggedIn(context, true)
            }
            isLoggedIn.value = true

    }

    // function to logout user
    fun logout(context: Context){
        viewModelScope.launch {
            setUserLoggedIn(context, false)
        }
        isLoggedIn.value = false
    }

}





// Datastore Pref
val Context.dataStore by preferencesDataStore("login_pref")
val LOGIN_SESSION = booleanPreferencesKey("is_login")

// returns true if logged in else false
   fun isUserLoggedIn(context: Context): Flow<Boolean>{
        return context.dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preference ->
            preference[LOGIN_SESSION] ?: false
        }
    }

// function store login session
    suspend fun setUserLoggedIn(context: Context, isUserLoggedIn: Boolean) {
        context.dataStore.edit { preference ->
            preference[LOGIN_SESSION] = isUserLoggedIn
        }
    }



