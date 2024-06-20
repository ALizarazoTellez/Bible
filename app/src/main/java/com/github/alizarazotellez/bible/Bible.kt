package com.github.alizarazotellez.bible

import android.content.Context
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@Serializable
data class Book(val Name: String, val Content: List<List<String>>)

object Bible {
    private var books: List<Book> = listOf()

    @OptIn(ExperimentalSerializationApi::class)
    fun load(context: Context) {
        if (books.isNotEmpty()) {
            return
        }

        val stream = context.resources.openRawResource(R.raw.bible)
        books = Json.decodeFromStream(stream)
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