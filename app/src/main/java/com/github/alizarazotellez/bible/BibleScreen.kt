package com.github.alizarazotellez.bible

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun BibleScreen(padding: PaddingValues) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        Text(text = "Bible content.", fontSize = 30.sp)
    }
}