package com.github.alizarazotellez.bible

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.alizarazotellez.bible.ui.theme.BibleTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BibleTheme {
                val context = LocalContext.current

                MainComponent()

                // Preload bible books.
                LaunchedEffect(Unit) {
                    Bible.load(context)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainComponent(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Bible") })
    }, bottomBar = {
        CustomNavigationBar(
            navController = navController, items = listOf(
                CustomNavigationBarItem(Icons.Default.Home, Screen.Home),
                CustomNavigationBarItem(
                    ImageVector.vectorResource(R.drawable.baseline_book_24), Screen.Bible
                ),
                CustomNavigationBarItem(Icons.Default.Info, Screen.About),
            )
        )
    }, modifier = modifier
    ) { padding ->
        NavHost(navController = navController, startDestination = Screen.Home) {
            composable<Screen.Home> {
                HomeScreen(padding = padding)
            }
            composable<Screen.Bible> {
                BibleScreen(padding = padding)
            }
            composable<Screen.About> {
                AboutScreen(padding = padding)
            }
        }
    }
}

data class CustomNavigationBarItem(val icon: ImageVector, val screen: Screen)

@Composable
fun CustomNavigationBar(
    navController: NavController, items: List<CustomNavigationBarItem>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(selected = navBackStackEntry?.destination?.hasRoute(item.screen::class)
                ?: false,
                onClick = {
                    navController.navigate(item.screen) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = null) })
        }
    }
}