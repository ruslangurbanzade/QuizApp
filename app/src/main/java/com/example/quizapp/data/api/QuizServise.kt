package com.example.quizapp.data.api

import com.example.quizapp.data.QuizResponse
import com.example.quizapp.data.TokenResponse
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import java.util.concurrent.TimeUnit


public interface QuizServise {
    @GET("api.php?amount=10&type=boolean")
    suspend fun getQuiz(): QuizResponse

    companion object {
        var quizServise: QuizServise? = null


        fun getInstance(): QuizServise {
            val logging = HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            val client = OkHttpClient().newBuilder()
                .connectTimeout(500, TimeUnit.MILLISECONDS)
                .readTimeout(500, TimeUnit.MILLISECONDS)
                .addInterceptor(logging)
                .build()

            val jsonConverter = Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.asConverterFactory("application/json".toMediaType())

            quizServise = Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(jsonConverter)
                .client(client)
                .build()
                .create(QuizServise::class.java)

            return quizServise!!
        }
    }
}