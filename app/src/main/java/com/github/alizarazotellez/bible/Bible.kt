package com.github.alizarazotellez.bible

import android.content.Context
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@Serializable
data class Book(val Name: String, val Content: List<List<String>>)

private lateinit var books: List<Book>

@OptIn(ExperimentalSerializationApi::class)
fun loadBooks(context: Context) {
    val stream = context.resources.openRawResource(R.raw.bible)
    books = Json.decodeFromStream(stream)
}

fun booksAreLoaded(): Boolean {
    return ::books.isInitialized
}

fun getLoadedBooks(): List<Book> {
    return books
}