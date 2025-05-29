package com.example.currencyconverter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.currencyconverter.utils.Strings
import com.example.currencyconverter.viewmodels.SettingsViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel, navController: NavController) {
    val currentLanguage = settingsViewModel.currentLanguage
    val currentColor = settingsViewModel.buttonColor

    Column(modifier = Modifier.padding(16.dp)) {
        Text(Strings.get("settings", currentLanguage), fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Text(Strings.get("language", currentLanguage))

        val languages = listOf("ro" to Strings.get("lang_ro", currentLanguage),
            "en" to Strings.get("lang_en", currentLanguage),
            "de" to Strings.get("lang_de", currentLanguage))

        languages.forEach { (code, name) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentLanguage == code,
                    onClick = { settingsViewModel.setLanguage(code) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = settingsViewModel.buttonColor
                    )                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(name)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(Strings.get("color", currentLanguage))

        val colors = listOf(
            Color(0xFF6750A4),
            Color(0xFF03DAC5),
            Color(0xFFFF5722),
            Color(0xFF4CAF50)
        )

        Row {
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .background(color)
                        .border(
                            width = if (currentColor == color) 3.dp else 1.dp,
                            color = if (currentColor == color) Color.Black else Color.Gray
                        )
                        .clickable { settingsViewModel.setButtonColor(color) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = settingsViewModel.buttonColor)
        ) {
            Text(Strings.get("back", currentLanguage))
        }
    }
}
