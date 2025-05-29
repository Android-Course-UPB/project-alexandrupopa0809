package com.example.currencyconverter.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.viewmodels.MainViewModel
import com.example.currencyconverter.viewmodels.SettingsViewModel

@Composable
fun AppNav(
    mainViewModel: MainViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen(mainViewModel, settingsViewModel, navController)
        }
        composable("history") {
            HistoryScreen(mainViewModel, settingsViewModel, navController)
        }
        composable("settings") {
            SettingsScreen(settingsViewModel, navController)
        }
    }
}
