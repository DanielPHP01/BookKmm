package com.example.bookkmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.bookkmm.data.model.BookVolume
import com.example.bookkmm.ui.MainViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    var editText by remember { mutableStateOf("") }
    var data by remember { mutableStateOf<List<BookVolume.VolumeItem>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = editText,
            onValueChange = { editText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.getBooks(editText).collect { books ->
                        data = books
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Search")
        }

        if (data.isNotEmpty()) {
            LazyColumn {
                items(data.size) { index ->
                    val book1 = data[index]
                    BookItem(book = book1)
                }
            }
        }
    }
}

@Composable
fun BookItem(book: BookVolume.VolumeItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Title: ${book.kind}")
            Text(text = "Author: ${book.volumeInfo?.title}")

        }
    }
}