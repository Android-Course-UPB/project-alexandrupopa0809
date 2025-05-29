package com.example.currencyconverter.utils

object Strings {
    private val translations = mapOf(
        "ro" to mapOf(
            "title" to "Convertor Valutar",
            "from" to "Din",
            "to" to "În",
            "amount" to "Sumă",
            "convert" to "Schimbă",
            "history" to "Vezi Istoric",
            "show_all" to "Arată toate monedele",
            "show_less" to "Afișează mai puțin",
            "invalid_amount" to "⚠️ Introdu un număr valid, nu un text.",
            "settings" to "Setări",
            "language" to "Selectează limba:",
            "color" to "Selectează culoarea butoanelor:",
            "back" to "Înapoi",
            "lang_ro" to "Română",
            "lang_en" to "Engleză",
            "lang_de" to "Germană",
            "history_title" to "Istoric conversii valutare",
            "rate" to "Curs"
        ),
        "en" to mapOf(
            "title" to "Currency Converter",
            "from" to "From",
            "to" to "To",
            "amount" to "Amount",
            "convert" to "Convert",
            "history" to "View History",
            "show_all" to "Show all currencies",
            "show_less" to "Show less",
            "invalid_amount" to "⚠️ Please enter a valid number.",
            "settings" to "Settings",
            "language" to "Select language:",
            "color" to "Select button color:",
            "back" to "Back",
            "lang_ro" to "Romanian",
            "lang_en" to "English",
            "lang_de" to "German",
            "history_title" to "Currency conversion history",
            "rate" to "Rate"
        ),
        "de" to mapOf(
            "title" to "Währungsrechner",
            "from" to "Von",
            "to" to "Nach",
            "amount" to "Betrag",
            "convert" to "Wechseln",
            "history" to "Verlauf ansehen",
            "show_all" to "Alle Währungen anzeigen",
            "show_less" to "Weniger anzeigen",
            "invalid_amount" to "⚠️ Bitte gib eine gültige Zahl ein.",
            "settings" to "Einstellungen",
            "language" to "Sprache auswählen:",
            "color" to "Button-Farbe auswählen:",
            "back" to "Zurück",
            "lang_ro" to "Rumänisch",
            "lang_en" to "Englisch",
            "lang_de" to "Deutsch",
            "history_title" to "Währungsumrechnung Verlauf",
            "rate" to "Wechselkurs"
        )
    )

    fun get(key: String, lang: String): String {
        return translations[lang]?.get(key) ?: key
    }
}
