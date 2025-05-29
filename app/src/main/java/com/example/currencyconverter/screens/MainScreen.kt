package com.example.currencyconverter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.currencyconverter.utils.Strings
import com.example.currencyconverter.viewmodels.MainViewModel
import com.example.currencyconverter.viewmodels.SettingsViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import com.example.currencyconverter.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel, settingsViewModel: SettingsViewModel, navController: NavController) {
    val apiKey = BuildConfig.CURRENCY_API_KEY
    val lang = settingsViewModel.currentLanguage

    LaunchedEffect(Unit) {
        if (viewModel.rates.isEmpty()) {
            viewModel.fetchRates(apiKey)
        }
    }

    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val currencies = viewModel.currencies
    val fromCurrency = viewModel.fromCurrency
    val toCurrency = viewModel.toCurrency

    val popularCurrencies = listOf("USD", "EUR", "GBP", "JPY", "RON")
    var showAll by remember { mutableStateOf(false) }

    val displayedCurrencies = if (showAll) currencies else currencies.filter { it in popularCurrencies }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.get("title", lang)) },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text(Strings.get("title", lang), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> CircularProgressIndicator()
                errorMessage != null -> Text(
                    "Error: $errorMessage",
                    color = MaterialTheme.colorScheme.error
                )

                currencies.isNotEmpty() -> {
                    // From currency
                    if (showAll) {
                        SearchableDropdown(
                            label = Strings.get("from", lang),
                            options = currencies,
                            onOptionSelected = { viewModel.fromCurrency = it }
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                Strings.get("from", lang),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(end = 8.dp)
                            )

                            DropdownMenu(
                                items = displayedCurrencies,
                                settingsViewModel = settingsViewModel,
                                selected = fromCurrency,
                                onSelected = { viewModel.fromCurrency = it },
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // To currency
                    if (showAll) {
                        SearchableDropdown(
                            label = Strings.get("to", lang),
                            options = currencies,
                            onOptionSelected = { viewModel.toCurrency = it }
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = Strings.get("to", lang),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            DropdownMenu(
                                items = displayedCurrencies,
                                settingsViewModel = settingsViewModel,
                                selected = toCurrency,
                                onSelected = { viewModel.toCurrency = it },
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(onClick = { showAll = !showAll },
                        colors = ButtonDefaults.textButtonColors(contentColor = settingsViewModel.buttonColor)
                    ) {
                        Text(Strings.get(if (showAll) "show_less" else "show_all", lang))
                    }
                }
                else -> Text("Loading currencies...")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.amountInput,
                onValueChange = { viewModel.amountInput = it },
                label = { Text(Strings.get("amount", lang)) },
                isError = viewModel.amountInput.toDoubleOrNull() == null && viewModel.amountInput.isNotEmpty()
            )

            if (viewModel.amountInput.toDoubleOrNull() == null && viewModel.amountInput.isNotEmpty()) {
                Text(
                    text = Strings.get("invalid_amount", lang),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.convert() },
                enabled = viewModel.rates.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = settingsViewModel.buttonColor)
            ) {
                Text(Strings.get("convert", lang))
            }

            if (viewModel.result.isNotEmpty()) {
                Text(viewModel.result, fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate("history") },
                colors = ButtonDefaults.buttonColors(containerColor = settingsViewModel.buttonColor)) {
                Text(Strings.get("history", lang))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableDropdown(
    label: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val filteredOptions = options.filter {
        it.contains(query, ignoreCase = true)
    }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text(label) },
            readOnly = false,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredOptions.take(10).forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        query = selectionOption
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DropdownMenu(items: List<String>, settingsViewModel: SettingsViewModel, selected: String, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    if (items.isEmpty()) {
        return
    }
    Box {
        Button(onClick = { expanded = true }, colors = ButtonDefaults.buttonColors(containerColor = settingsViewModel.buttonColor)) {
            Text(selected)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onSelected(item)
                        expanded = false
                    },
                    text = {
                        Text(item)
                    }
                )
            }
        }
    }
}
