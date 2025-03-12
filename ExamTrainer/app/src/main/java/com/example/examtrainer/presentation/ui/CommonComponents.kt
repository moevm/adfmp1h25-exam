package com.example.examtrainer.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes

@Composable
fun CommonHeader(backButtonText: String, onClick: () -> Unit) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .padding(vertical = 20.dp, horizontal = 30.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = backButtonText,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun rememberRootBackStackEntry(
    navController: NavController,
    route: String
): NavBackStackEntry {
    val backStackEntry = remember(navController) {
        try {
            navController.getBackStackEntry(route)
        } catch (e: IllegalArgumentException) {
            navController.navigate(route) {
                popUpTo(NavRoutes.MAIN) { inclusive = true }
            }
            navController.getBackStackEntry(route)
        }
    }
    return backStackEntry
}