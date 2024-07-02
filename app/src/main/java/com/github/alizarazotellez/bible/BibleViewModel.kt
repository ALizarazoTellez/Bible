package com.github.alizarazotellez.bible

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BibleViewModel : ViewModel() {
    var currentBook by mutableIntStateOf(0)
        private set
    var currentChapter by mutableIntStateOf(0)
        private set

    fun changeCurrentBook(book: Int) {
        currentBook = book
    }

    fun changeCurrentChapter(chapter: Int) {
        currentChapter = chapter
    }
}