package com.example.examtrainer.presentation.ui.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnswersVariants(answers: List<String>, onSelectAnswer: (String) -> Unit, buttonBgColor: (String) -> Color) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        answers.forEachIndexed() { variantIndex, answer ->
            Button(
                onClick = { onSelectAnswer(answer) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBgColor(answer),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.buttonElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${variantIndex+1}. ${answer}")
                }
            }
        }
    }
}

@Composable
fun HintComponent(hintText: String, isHintUsed: Boolean) {
    Box(
        modifier = Modifier
            .alpha(if (!isHintUsed) 0f else 1f)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = hintText,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun NextButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 22.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ConfirmButton(text: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 22.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
