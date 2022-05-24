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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
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
                    PlayQuiz("Android")
                }
            }
            mainViewModel.getQuiz()
        }
    }
}

@Composable
fun PlayQuiz(name: String) {
    var visible by remember { mutableStateOf(true) }

    AnimatedVisibility(visible = visible, enter = slideIn(tween(300, easing = LinearOutSlowInEasing)) {
        IntOffset(it.width / 2, 100)
    }, exit = slideOut(tween(100, easing = FastOutSlowInEasing)) {
        IntOffset(-180, 50)
    }) {
        Surface(color = MaterialTheme.colors.primary) {
            TextButton(onClick = { visible = false }) {
                Text(text = "Hello $name!. Do you want to play Quiz?", color = Color.White)
            }
        }
    }

    AnimatedVisibility(
        visible = !visible,
        enter = slideInVertically(initialOffsetY = { -100 }) + expandVertically(expandFrom = Alignment.Top),
        exit = slideOutVertically() + shrinkVertically()) {
        Surface(color = MaterialTheme.colors.primary) {
            ShowQuestions()
        }
    }

}

@Composable
private fun ShowQuestions(quesList: List<String> = listOf(
    "Several characters in Super Mario blink their eyes.",
    "BMW M GmbH is a subsidiary of BMW AG that focuses on car performance.",
    "When was the game Roblox released?",
    "Who is the author of Jurrasic Park?",
    "In Geometry Dash, what is level 13?",
    "Popcorn was invented in 1871 by Frederick W. Rueckheim in the USA where he sold the snack on the streets of Chicago.",
    "What album did Gorillaz release in 2017?",
    "Which U.S. President was famously attacked by a swimming rabbit?",
    "Several characters in Super Mario blink their eyes.",
    "BMW M GmbH is a subsidiary of BMW AG that focuses on car performance.",
    "In Geometry Dash, what is level 13?")) {

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)) {
            for (question in quesList) {
                RenderQuestion(title = question, correctAnswer = false)
            }
        }
    }
}

@Composable
private fun RenderQuestion(title: String, correctAnswer: Boolean) {
    var visible by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    var rowColor by remember { mutableStateOf(Color.Blue) }

    AnimatedVisibility(visible = visible, enter = fadeIn(initialAlpha = 0.4f), exit = fadeOut(animationSpec = tween(durationMillis = 250))) {
        Surface(color = rowColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp)) {
            Column(modifier = Modifier.padding(all = 4.dp)) {
                TextButton(onClick = { expanded = !expanded }) {
                    Text(text = "Ques: $title!", color = Color.White)
                }
                AnimatedVisibility(visible = expanded) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)) {
                        OutlinedButton(
                            onClick = {
                                // change color
                                if (correctAnswer) {
                                    expanded = !expanded
                                    rowColor = Color.Green
                                } else {
                                    rowColor = Color.Red
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
                                rowColor = if (correctAnswer) {
                                    Color.Red
                                } else {
                                    expanded = !expanded
                                    Color.Green
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuizAppTheme {
        PlayQuiz("Android")
    }
}

@Composable
fun QuizItemView(quizItem: QuizItem) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
    ) {
        Surface() {

            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                    Text(
                        text = quizItem.question,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = quizItem.category,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .background(
                                color = LightGray
                            )
                            .padding(4.dp)
                    )

                }
            }
        }
    }
}

@Composable
fun QuizListView(quizList: List<QuizItem>) {
    LazyColumn {
        itemsIndexed(items = quizList) { index, item ->
            QuizItemView(quizItem = item)
        }
    }
}