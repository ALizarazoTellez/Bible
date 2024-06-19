package com.github.alizarazotellez.bible

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun BibleScreen(padding: PaddingValues) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        var ready by remember {
            mutableStateOf(booksAreLoaded())
        }

        val context = LocalContext.current

        if (!ready) {
            LaunchedEffect(Unit) {
                loadBooks(context)
                ready = true
            }

            return
        }

        ChapterViewer(bookName = "Génesis", chapter = 6)
    }
}

@Composable
fun ChapterViewer(bookName: String, chapter: Int) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        itemsIndexed(getBook(bookName)?.Content?.get(chapter - 1) ?: listOf()) { index, verse ->
            Card(onClick = {}) {
                Text(text = "${index + 1} $verse", modifier = Modifier.padding(8.dp))
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}