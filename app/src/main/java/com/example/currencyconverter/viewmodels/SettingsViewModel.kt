package com.example.currencyconverter.viewmodels

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color

class SettingsViewModel : ViewModel() {
    private var _currentLanguage by mutableStateOf("ro")
    private var _buttonColor by mutableStateOf(Color(0xFF6750A4)
    )

    val currentLanguage: String get() = _currentLanguage
    val buttonColor: Color get() = _buttonColor

    fun setLanguage(lang: String) {
        _currentLanguage = lang
    }

    fun setButtonColor(color: Color) {
        _buttonColor = color
    }
}
