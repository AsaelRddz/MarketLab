package com.marketlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.marketlab.ui.navigation.MarketLabNavigation
import com.marketlab.ui.theme.MarketLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MarketLabNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}