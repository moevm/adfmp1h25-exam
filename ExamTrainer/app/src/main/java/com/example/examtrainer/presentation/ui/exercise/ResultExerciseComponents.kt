package com.example.examtrainer.presentation.ui.exercise

import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ShareButton(text: String, shareText: String) {
    val context = LocalContext.current
    Button(
        shape = RoundedCornerShape(10.dp),
        onClick = {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(shareIntent, "Поделиться через"))
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Share",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 22.dp),
                text = text,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun BackToMainSreenButton(text: String, onClick: () -> Unit) {
    Button(
        shape = RoundedCornerShape(10.dp),
        onClick = {
            onClick()

        }
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 22.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

