package com.example.timetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetracker.data.entity.TimeEntry
import com.example.timetracker.repository.TimeEntryRepository
import com.example.timetracker.timer.TimerEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TimerUiState(
    val isRunning: Boolean = false,
    val startTime: Long? = null,
    val elapsedMs: Long = 0L
)

class TimerViewModel(
    private val repository: TimeEntryRepository
) : ViewModel() {

    private val timerEngine = TimerEngine()

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState: StateFlow<TimerUiState> = _uiState

    fun startTimer() {
        val start = timerEngine.start()
        _uiState.value = TimerUiState(
            isRunning = true,
            startTime = start,
            elapsedMs = 0L
        )
    }

    fun stopTimer() {
        val (start, end) = timerEngine.stop()
        val duration = end - start

        // Save to DB
        viewModelScope.launch {
            repository.insertTimeEntry(
                TimeEntry(
                    startTime = start,
                    endTime = end,
                    description = null
                )
            )
        }

        _uiState.value = TimerUiState(
            isRunning = false,
            startTime = null,
            elapsedMs = duration
        )
    }
}