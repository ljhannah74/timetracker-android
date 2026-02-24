package com.example.timetracker.repository

import com.example.timetracker.data.dao.TimeEntryDao
import com.example.timetracker.data.entity.TimeEntry

class TimeEntryRepository(private val dao: TimeEntryDao) {
    suspend fun insertTimeEntry(entry: TimeEntry) {
        dao.insertTimeEntry(entry)
    }

    fun getAll() = dao.getAll()
}