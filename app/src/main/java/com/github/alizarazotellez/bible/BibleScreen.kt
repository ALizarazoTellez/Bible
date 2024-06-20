package com.github.alizarazotellez.bible

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BibleScreen(padding: PaddingValues) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        val context = LocalContext.current

        var books by remember { mutableStateOf(Bible.getBooks()) }
        if (books.isEmpty()) {
            LaunchedEffect(Unit) {
                Bible.load(context)
                books = Bible.getBooks()
            }

            return
        }

        var currentBook by remember { mutableIntStateOf(0) }
        var currentChapter by remember { mutableIntStateOf(0) }

        val chaptersOfCurrentBook = books[currentBook].Content.size

        // LazyColumn helpers.
        var isOnTopList by remember { mutableStateOf(false) }
        val listState = rememberLazyListState()
        LaunchedEffect(isOnTopList) {
            if (isOnTopList) {
                listState.scrollToItem(0)
                isOnTopList = false
            }
        }

        Column {
            Row {
                CustomDropdownMenu(
                    onSelect = {
                        currentChapter = 0
                        currentBook = it
                        isOnTopList = true
                    },
                    currentItem = currentBook,
                    items = Bible.getBookNames(),
                    modifier = Modifier.weight(2f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                CustomDropdownMenu(
                    onSelect = {
                        currentChapter = it
                        isOnTopList = true
                    },
                    currentItem = currentChapter,
                    items = (1..chaptersOfCurrentBook).map { it.toString() },
                    modifier = Modifier.weight(1f)
                )
            }
            ChapterViewer(
                listState = listState, bookName = books[currentBook].Name, chapter = currentChapter
            )
        }
    }
}

@Composable
fun ChapterViewer(listState: LazyListState, bookName: String, chapter: Int) {
    LazyColumn(state = listState, modifier = Modifier.padding(horizontal = 16.dp)) {
        itemsIndexed(Bible.getBook(bookName)?.Content?.get(chapter) ?: listOf()) { index, verse ->
            Card(onClick = {}) {
                Text(text = "${index + 1} $verse", modifier = Modifier.padding(8.dp))
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(
    onSelect: (Int) -> Unit, currentItem: Int, items: List<String>, modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = modifier
    ) {
        TextField(
            value = items[currentItem],
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(text = { Text(text = item) }, onClick = {
                    onSelect(index)
                    expanded = false
                })
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun ChapterViewPreview() {
    BibleScreen(padding = PaddingValues(0.dp))
}