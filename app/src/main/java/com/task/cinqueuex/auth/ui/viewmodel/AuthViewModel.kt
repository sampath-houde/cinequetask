package com.task.cinqueuex.auth.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.cinqueuex.auth.data.get.LoginResponse
import com.task.cinqueuex.auth.data.post.EmailAndPassword
import com.task.cinqueuex.auth.data.repo.AuthRepo
import com.task.cinqueuex.utils.ApiResponseHandler
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepo): ViewModel() {

    private val _loginResponse: MutableLiveData<ApiResponseHandler<LoginResponse>> = MutableLiveData()

    val loginResponse: LiveData<ApiResponseHandler<LoginResponse>>
        get() = _loginResponse

    fun login(email: String, password: String) = viewModelScope.launch {
        val data = EmailAndPassword(email, password)
        _loginResponse.value = repository.login(data)
    }
}