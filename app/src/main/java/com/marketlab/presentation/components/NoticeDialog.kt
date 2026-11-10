package com.marketlab.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun NoticeDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Notice") },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}
