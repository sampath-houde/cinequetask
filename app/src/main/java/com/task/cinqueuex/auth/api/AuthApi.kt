package com.task.cinqueuex.auth.api

import com.task.cinqueuex.auth.data.get.LoginResponse
import com.task.cinqueuex.auth.data.post.EmailAndPassword
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(@Body data: EmailAndPassword) : LoginResponse
}