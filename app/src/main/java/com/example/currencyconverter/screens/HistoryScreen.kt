package com.example.currencyconverter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.currencyconverter.utils.Strings
import com.example.currencyconverter.viewmodels.MainViewModel
import com.example.currencyconverter.viewmodels.SettingsViewModel
import androidx.compose.ui.text.font.FontWeight

@Composable
fun HistoryScreen(viewModel: MainViewModel, settingsViewModel: SettingsViewModel, navController: NavController) {
    val lang = settingsViewModel.currentLanguage

    LaunchedEffect(Unit) {
        viewModel.loadHistoryFromDb(lang)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(Strings.get("history_title", lang), fontSize = 24.sp, fontWeight = FontWeight.Bold)

        LazyColumn {
            items(viewModel.history) { item ->
                Text(item, modifier = Modifier.padding(8.dp))
            }
        }

        Button(onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = settingsViewModel.buttonColor)
        ) {
            Text(Strings.get("back", lang))
        }
    }
}
