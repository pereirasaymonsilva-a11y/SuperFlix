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
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
            label = { Text("Início") },
            selected = currentRoute == Destinations.Home.route,
            onClick = { navController.navigate(Destinations.Home.route) }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Pesquisar") },
            label = { Text("Pesquisar") },
            selected = currentRoute == Destinations.Search.route,
            onClick = { navController.navigate(Destinations.Search.route) }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Folder, contentDescription = "Biblioteca") },
            label = { Text("Biblioteca") },
            selected = currentRoute == Destinations.Library.route,
            onClick = { navController.navigate(Destinations.Library.route) }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = currentRoute == Destinations.Profile.route,
            onClick = { navController.navigate(Destinations.Profile.route) }
        )
    }
}