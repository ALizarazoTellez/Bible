package com.github.alizarazotellez.bible

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.alizarazotellez.bible.ui.theme.BibleTheme
import kotlinx.serialization.Serializable


@Serializable
object Home

@Serializable
object About

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BibleTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Home) {
                    composable<Home> {
                        HomeScreen(onNavigateToAboutScreen = { navController.navigate(route = About) })
                    }
                    composable<About> {
                        AboutScreen(onNavigateToHomeScreen = { navController.navigate(route = Home) })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToAboutScreen: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Bible") }) },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = true, onClick = {}, icon = {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = null,
                    )
                })
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateToAboutScreen() },
                    icon = {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                        )
                    })
            }
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Bible", fontSize = 30.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onNavigateToHomeScreen: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Bible") }) },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = false, onClick = { onNavigateToHomeScreen() }, icon = {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = null,
                    )
                })
                NavigationBarItem(selected = true, onClick = {}, icon = {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                    )
                })
            }
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Made by Anderson.")
        }
    }
}