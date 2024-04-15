package com.example.tasktify.service
import com.example.tasktify.model.Login
import com.example.tasktify.model.ResponseStatus
import com.example.tasktify.model.Task
import com.example.tasktify.model.TaskListStatus
import com.example.tasktify.model.Token
import com.example.tasktify.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskService {
    @POST("api/users/login")
    suspend fun authenticate(@Body login: Login): ResponseStatus

    @POST("api/users/register")
    suspend fun register(@Body user: User): Call<User>

    @POST("api/users/token")
    suspend fun validateToken(@Body token: Token): ResponseStatus

    @GET("api/task")
    suspend fun getTaskList(@Header("Authorization") token: String?): TaskListStatus

    @GET("api/task/{id}")
    suspend fun getTask(@Header("Authorization") token: String?, @Path("id") taskId: String): Task

    @DELETE("api/task/{id}")
    suspend fun deleteTask(@Header("Authorization") token: String?, @Path("id") taskId: String? = ""): ResponseStatus

    @PUT("api/task/{id}")
    suspend fun updateTask(@Header("Authorization") token: String?, @Path("id") taskId: String? = "", @Body task: Task): Task

    @POST("api/task")
    suspend fun createTask(@Header("Authorization") token: String?, @Body task: Task): ResponseStatus

}