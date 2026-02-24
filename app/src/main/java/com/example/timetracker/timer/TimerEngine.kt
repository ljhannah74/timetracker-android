package com.example.timetracker.timer

class TimerEngine {

    private var startTime: Long? = null
    private var running = false

    fun start(): Long {
        if (running) return startTime!!

        startTime = System.currentTimeMillis()
        running = true
        return startTime!!
    }

    fun stop(): Pair<Long, Long> {
        if (!running || startTime == null)
            throw IllegalStateException("Timer is not running")

        val end = System.currentTimeMillis()
        val start = startTime!!

        startTime = null
        running = false

        return Pair(start, end)
    }

    fun isRunning(): Boolean = running
}