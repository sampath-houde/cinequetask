package com.task.cinqueuex.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.cinqueuex.auth.data.repo.AuthRepo
import com.task.cinqueuex.auth.ui.viewmodel.AuthViewModel
import com.task.tsjtask.utils.BaseRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepo) as T
            else -> throw IllegalArgumentException("ViewModel class not found")
        }
    }

}