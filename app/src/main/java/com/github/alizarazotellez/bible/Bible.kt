package com.github.alizarazotellez.bible

import android.content.Context
import androidx.compose.runtime.Immutable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@Serializable
@Immutable
data class Book(val Name: String, val Content: List<List<String>>)

object Bible {
    private var books: List<Book> = listOf()

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun load(context: Context) {
        if (books.isNotEmpty()) {
            return
        }

        withContext(Dispatchers.IO) {
            val stream = context.resources.openRawResource(R.raw.bible)
            books = Json.decodeFromStream(stream)
        }
    }

    fun getBooks(): List<Book> {
        return books
    }

    fun getBook(name: String): Book? {
        return books.find { it.Name == name }
    }

    private var bookNames: List<String> = listOf()

    fun getBookNames(): List<String> {
        if (bookNames.isEmpty()) {
            bookNames = books.map { it.Name }
        }

        return bookNames
    }
}