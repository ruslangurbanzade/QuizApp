package com.example.quizapp.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class QuizResponse(
    @SerializedName("response_code")
    val response_code: Int,
    @SerializedName("results")
    val results: List<QuizItem>
)

@Serializable
data class QuizItem(
    @SerializedName("category")
    val category: String,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("correct_answer")
    val correct_answer: Boolean
)


@Serializable
data class TokenResponse(val token: String)