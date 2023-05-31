package com.example.phoenixnews.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phoenixnews.repository.repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewmodelPerf : ViewModel() {
    private val repository = repository()

    private val _userDetails: MutableStateFlow<HashMap<String, String>> = MutableStateFlow(HashMap())
    val userDetails: StateFlow<HashMap<String, String>> = _userDetails

    fun getUserDetails() = viewModelScope.launch {
        val details = repository.getUserDetails()
        _userDetails.emit(details)
    }

    fun isLoggedIn() = viewModelScope.launch { repository.isLoggedIn() }

    fun loginUser(username: String, email: String) {
        repository.loginUser(username, email)
    }

    fun logoutUser() {
        repository.logoutUser()
    }
}
