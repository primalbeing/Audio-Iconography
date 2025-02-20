package com.audioiconography.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import com.audioiconography.app.ui.theme.AudioIconographyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start the service automatically
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
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Header
            Text(
                text = "🎧 Audio Iconography",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Centered Description
            Text(
                text = "This app automatically detects when wired headphones are connected and displays a silent notification.",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        // Menu Icon (Placed in Top-Right Corner)
        IconButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu", tint = Color.White)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("App Info", color = Color.White) },
            text = {
                Column {
                    InfoRow("Author", "Primal Being")
                    InfoRow("Version", "2.0.0")
                    InfoRow("License", "GPLv3")

                    Spacer(modifier = Modifier.height(8.dp))

                    // Clearer "Source Code" Label
                    Text("Source Code:", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                    ClickableText(
                        text = AnnotatedString("https://github.com/primalbeing/Audio-Iconography"),
                        onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/primalbeing/Audio-Iconography"))) },
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFB0B0B0))
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAAAAAA)) // Light gray button
                ) {
                    Text("Close", color = Color.Black) // Black text for better contrast
                }
            },
            containerColor = Color.Black,
            textContentColor = Color.White
        )
    }
}

// Function to Format Info Rows (Title + Value)
@Composable
fun InfoRow(title: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
        Text(text = value, fontSize = 16.sp, color = Color.White)
    }
}
