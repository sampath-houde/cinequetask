package com.task.cinqueuex.auth.data.repo

import com.task.cinqueuex.auth.api.AuthApi
import com.task.cinqueuex.auth.data.post.EmailAndPassword
import com.task.tsjtask.utils.BaseRepository

class AuthRepo(
    private val api: AuthApi
) : BaseRepository(){

    suspend fun login(data: EmailAndPassword) = safeApiCall {
        api.login(data)
    }
}