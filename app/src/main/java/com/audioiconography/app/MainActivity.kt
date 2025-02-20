package com.audioiconography.app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.platform.LocalContext
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.net.Uri
import com.audioiconography.app.ui.theme.AudioIconographyTheme

// Explicitly enable experimental Material 3 APIs
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // FIX: REQUEST NOTIFICATION PERMISSION BEFORE STARTING SERVICE
        requestNotificationPermission()

        // Start the foreground service automatically
        val serviceIntent = Intent(this, HeadphoneService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)

        setContent {
            AudioIconographyTheme {
                MainScreen()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Header (FIXED: Used `TopAppBar`)
        TopAppBar(
            title = { Text("Audio Iconography", color = Color.White) },
            actions = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Centered App Name
        Text(
            text = "🎧 Audio Iconography",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "This app detects wired headphone connection\nand displays a silent notification.",
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("App Info", color = Color.White) },
            text = {
                Column {
                    Text("Author", color = Color.LightGray)
                    Text("Your Name", color = Color.White)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Version", color = Color.LightGray)
                    Text("2.0.0", color = Color.White)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("License", color = Color.LightGray)
                    Text("GPLv3", color = Color.White)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Source Code", color = Color.LightGray)
                    Text(
                        text = "GitHub Repository",
                        color = Color.Cyan,
                        modifier = Modifier.clickable {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/yourrepo")))
                        }
                    )
                }
            },
            confirmButton = { Button(onClick = { showDialog = false }) { Text("Close") } },
            containerColor = Color.Black,
            textContentColor = Color.White
        )
    }
}
