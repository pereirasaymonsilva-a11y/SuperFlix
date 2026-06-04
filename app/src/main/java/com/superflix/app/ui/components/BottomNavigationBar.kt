// ui/components/BottomNavigationBar.kt
package com.superflix.app.ui.components

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.superflix.app.ui.navigation.Destinations

@Composable
fun BottomNavigationBar(navController: NavHostController = rememberNavController()) {
    val items = listOf(
        Destinations.Home to "Início" to Icons.Default.Home,
        Destinations.Search to "Pesquisar" to Icons.Default.Search,
        Destinations.Library to "Biblioteca" to Icons.Default.Folder,
        Destinations.Profile to "Perfil" to Icons.Default.Person
    )
    
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route
        
        items.forEach { (destination, title, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = title) },
                label = { Text(title) },
                selected = currentRoute == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(Destinations.Home.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}