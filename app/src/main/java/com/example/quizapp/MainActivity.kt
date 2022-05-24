@file:OptIn(ExperimentalAnimationApi::class)

package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.dp
import com.example.quizapp.data.QuizItem
import com.example.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {

    val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    color = MaterialTheme.colors.background
                ) {
                    PlayQuiz("Android", mainViewModel.quizList)
                }
            }
            mainViewModel.getQuiz()
        }
    }
}

@Composable
fun PlayQuiz(name: String, quizlist: List<QuizItem>) {
    var visible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = visible,
        enter = slideIn(tween(300, easing = LinearOutSlowInEasing)) {
            IntOffset(it.width / 2, 100)
        },
        exit = scaleOut(
            tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        ) + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        Surface(color = MaterialTheme.colors.primary) {
            TextButton(onClick = { visible = false }) {
                Text(text = "Hello $name!. Do you want to play Quiz?", color = Color.White)
            }
        }
    }

    AnimatedVisibility(
        visible = !visible,
        enter = slideInVertically(initialOffsetY = { -100 }) + expandVertically(expandFrom = Alignment.Top),
        exit = slideOutVertically() + shrinkVertically()
    ) {
        Surface(color = MaterialTheme.colors.background) {
            ShowQuestions(quizlist)
        }
    }

}

@Composable
private fun ShowQuestions(
    quesList: List<QuizItem>
) {

    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(quesList) { quiz ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                RenderQuestion(title = quiz.question, correctAnswer = quiz.correct_answer)

            }
        }
    }
}

enum class Answer {
    NOTHING,
    CORRECT,
    WRONG
}

@Composable
private fun RenderQuestion(title: String, correctAnswer: Boolean) {
    var visible by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    var userAnswer by remember { mutableStateOf(Answer.NOTHING) }
    val rowColor by animateColorAsState(
        when (userAnswer) {
            Answer.NOTHING -> Blue
            Answer.CORRECT -> Green
            else -> Red
        }
    )

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(initialAlpha = 0.4f),
        exit = fadeOut(animationSpec = tween(durationMillis = 250))
    ) {
        Surface(
            color = rowColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Column(modifier = Modifier.padding(all = 4.dp)) {
                TextButton(onClick = { if (userAnswer != Answer.CORRECT) expanded = !expanded }) {
                    Text(text = "Ques: $title!", color = Color.White)

                }
                AnimatedVisibility(visible = expanded) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                userAnswer = if (correctAnswer) {
                                    expanded = !expanded
                                    Answer.CORRECT
                                } else {
                                    Answer.WRONG

                                }
                            },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .wrapContentWidth(align = Alignment.CenterHorizontally)
                        ) {
                            Text("True")
                        }

                        OutlinedButton(
                            onClick = {

                                userAnswer =
                                    if (correctAnswer) {
                                        Answer.WRONG
                                    } else {
                                        expanded = !expanded
                                        Answer.CORRECT
                                    }

                            },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .wrapContentWidth(align = Alignment.CenterHorizontally)
                        ) {
                            Text("False")
                        }
                    }
                }
            }
        }
    }
}