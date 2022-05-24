package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                    QuizListView(quizList = mainViewModel.quizList)

                }
            }
            mainViewModel.getQuiz()
        }
    }
}


@Composable
fun Greeting(name: String) {
    var visible by remember { mutableStateOf(true) }
    AnimatedVisibility(visible = visible) {
        TextButton(onClick = { visible = false }) {

            Text(text = "Hello $name!. Do you want to play Quiz")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuizAppTheme {
        Greeting("Android")
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