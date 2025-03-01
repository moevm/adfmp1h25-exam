package com.example.examtrainer.presentation.ui.theory

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.examtrainer.presentation.ui.CommonHeader

@Composable
fun TOC(
    navController: NavController,
    backButtonText: String,
    backButtonRoute: String,
    items: List<String>,
    onItemClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CommonHeader(
            backButtonText = backButtonText,
            onClick = {
                navController.navigate(backButtonRoute) {
                    launchSingleTop = true
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .verticalScroll(ScrollState(0)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items.forEachIndexed { itemIdx, item ->
                TOCDivider()
                TOCButton(
                    text = item,
                    onClick = { onItemClick(itemIdx) },
                )
            }
            TOCDivider()
        }
    }
}

@Composable
fun TOCButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = Color.Black
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Select",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Composable
fun TOCDivider() {
    HorizontalDivider(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
    )
}
