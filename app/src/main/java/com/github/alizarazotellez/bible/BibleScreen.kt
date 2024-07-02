package com.github.alizarazotellez.bible

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BibleScreen(padding: PaddingValues) {
    val viewModel = viewModel<BibleViewModel>()

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

        val chaptersOfCurrentBook = books[viewModel.currentBook].Content.size

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
            Row(modifier = Modifier.padding(4.dp)) {
                CustomDropdownMenu(
                    onSelect = {
                        viewModel.changeCurrentChapter(0)
                        viewModel.changeCurrentBook(it)
                        isOnTopList = true
                    },
                    currentItem = viewModel.currentBook,
                    items = Bible.getBookNames(),
                    modifier = Modifier.weight(2f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                CustomDropdownMenu(
                    onSelect = {
                        viewModel.changeCurrentChapter(it)
                        isOnTopList = true
                    },
                    currentItem = viewModel.currentChapter,
                    items = (1..chaptersOfCurrentBook).map { it.toString() },
                    modifier = Modifier.weight(1f)
                )
            }
            ChapterViewer(
                listState = listState,
                bookName = books[viewModel.currentBook].Name,
                chapter = viewModel.currentChapter
            )
        }
    }
}

@Composable
fun ChapterViewer(listState: LazyListState, bookName: String, chapter: Int) {
    LazyColumn(state = listState, modifier = Modifier.padding(horizontal = 8.dp)) {
        itemsIndexed(Bible.getBook(bookName)?.Content?.get(chapter) ?: listOf()) { index, verse ->
            Card {
                Text(text = "${index + 1} $verse", modifier = Modifier.padding(8.dp))
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(
    onSelect: (Int) -> Unit, currentItem: Int, items: List<String>, modifier: Modifier = Modifier,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = modifier
    ) {
        OutlinedTextField(
            value = items[currentItem],
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
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