package com.example.quizapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.QuizItem
import com.example.quizapp.data.api.QuizServise
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var quizList: List<QuizItem> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getQuiz() {
        viewModelScope.launch {
            val apiServise = QuizServise.getInstance()
            runCatching {
                val response = apiServise.getQuiz()
                response!!.results
            }.onSuccess {
                quizList = it
            }.onFailure {
                errorMessage = it.message.toString()
            }
        }
    }
}