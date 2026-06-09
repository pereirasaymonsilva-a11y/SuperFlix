// ui/components/BottomNavigationBar.kt
package com.superflix.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Library,
        BottomNavItem.Profile
    )
    
    var selectedItem by remember { mutableStateOf(0) }
    
    NavigationBar(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    when (item.route) {
                        "home" -> navController.navigate("home") {
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                        }
                        "search" -> navController.navigate("search") {
                            launchSingleTop = true
                        }
                        "library" -> navController.navigate("library") {
                            launchSingleTop = true
                        }
                        "profile" -> navController.navigate("profile") {
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                }
            )
        }
    }
}

sealed class BottomNavItem(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : BottomNavItem("home", "Início", Icons.Default.Home)
    object Search : BottomNavItem("search", "Pesquisar", Icons.Default.Search)
    object Library : BottomNavItem("library", "Biblioteca", Icons.Default.Favorite)
    object Profile : BottomNavItem("profile", "Perfil", Icons.Default.Person)
}