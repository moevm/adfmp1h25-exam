package com.example.examtrainer.presentation.ui.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun InfoHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun InfoText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun StartExerciseButton(onClick: () -> Unit) {
    Button(
        shape = RoundedCornerShape(10.dp),
        onClick = { onClick() }
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 22.dp),
            text = "Начать",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun StartExerciseInfoBox(headerText: String, infoText: String, onStart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(.85f)
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f))
                .padding(25.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                InfoHeader(
                    text = headerText
                )
                Spacer(modifier = Modifier.height(20.dp))
                InfoText(
                    text = infoText
                )
                Spacer(modifier = Modifier.height(70.dp))
                StartExerciseButton(onClick = { onStart() })
            }
        }
    }
}