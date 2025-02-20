package com.audioiconography.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.audioiconography.app.ui.theme.AudioIconographyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start the foreground service when the app launches
        val serviceIntent = Intent(this, HeadphoneService::class.java)
        startForegroundService(serviceIntent)

        setContent {
            AudioIconographyTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var isServiceRunning by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Audio Icon Service", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { isServiceRunning = !isServiceRunning }) {
            Text(text = if (isServiceRunning) "Stop Service" else "Start Service")
        }
    }
}
