package com.github.alizarazotellez.bible

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data object Bible : Screen()

    @Serializable
    data object About : Screen()
}