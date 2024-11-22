package com.example.currencyconverter

import CurrencyConverterApp
import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()
            CurrencyConverterTheme(content = { CurrencyConverterApp(viewModel) })
        }
    }
}
