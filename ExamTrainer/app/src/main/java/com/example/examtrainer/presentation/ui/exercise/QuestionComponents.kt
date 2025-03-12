package com.example.examtrainer.presentation.ui.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AnswersVariants(
    answers: List<String>,
    onSelectAnswer: (String) -> Unit,
    buttonBgColor: (String) -> Color
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        answers.forEachIndexed { variantIndex, answer ->
            Button(
                onClick = { onSelectAnswer(answer) },
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minHeight = 60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBgColor(answer),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.buttonElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical=10.dp, horizontal=5.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${variantIndex+1}. $answer"
                    )

                }
            }
        }
    }
}

@Composable
fun HintComponent(hintText: String, isHintUsed: Boolean) {
    if (isHintUsed) {
        Box(
            modifier = Modifier
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

@Composable
fun ConfirmExitDialog(
    titleText: String,
    text: String,
    onDismiss: () -> Unit, // Закрытие диалога
    onConfirm: () -> Unit, // Действие при подтверждении
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        onDismissRequest = onDismiss, // Закрытие при нажатии вне диалога
        title = {
            Text(
                text = titleText,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = text,
                color = Color.Black
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text("Подтвердить")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Отмена")
            }
        }
    )
}