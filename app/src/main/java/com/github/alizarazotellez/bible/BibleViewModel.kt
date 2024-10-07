package com.github.alizarazotellez.bible

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class BibleViewModel(private val state: SavedStateHandle) : ViewModel() {
    var currentBook by mutableIntStateOf(state["currentBook"] ?: 0)
        private set
    var currentChapter by mutableIntStateOf(state["currentChapter"] ?: 0)
        private set

    fun changeCurrentBook(book: Int) {
        state["currentBook"] = book
        currentBook = book
    }

    fun changeCurrentChapter(chapter: Int) {
        state["currentChapter"] = chapter
        currentChapter = chapter
    }
}