package com.example.timetracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timetracker.data.database.AppDatabase
import com.example.timetracker.repository.TimeEntryRepository
import com.example.timetracker.viewmodel.TimerViewModel
import com.example.timetracker.viewmodel.TimerViewModelFactory
import kotlinx.coroutines.delay

@Composable
fun TimerScreen() {
    val context = LocalContext.current
    val db = AppDatabase.getInstance(context)
    val repository = TimeEntryRepository(db.timeEntryDao())
    val factory = TimerViewModelFactory(repository)

    val timerViewModel: TimerViewModel = viewModel(factory = factory)
    val uiState by timerViewModel.uiState.collectAsState()

    var displayTime by remember { mutableStateOf("00:00") }

    // Live timer updater
    LaunchedEffect(uiState.isRunning) {
        while (uiState.isRunning) {
            val now = System.currentTimeMillis()
            val elapsed = if (uiState.startTime != null) {
                now - uiState.startTime!!
            } else 0L

            displayTime = formatTime(elapsed)
            delay(1000)
        }

        if (!uiState.isRunning) {
            displayTime = formatTime(uiState.elapsedMs)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Text(
                text = displayTime,
                style = MaterialTheme.typography.displayLarge
            )

            Button(
                onClick = {
                    if (uiState.isRunning) {
                        timerViewModel.stopTimer()
                    } else {
                        timerViewModel.startTimer()
                    }
                },
                modifier = Modifier.width(200.dp)
            ) {
                Text(if (uiState.isRunning) "Stop" else "Start")
            }
        }
    }
}

private fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
