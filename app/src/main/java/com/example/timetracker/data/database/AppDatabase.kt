package com.example.timetracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.timetracker.data.dao.TimeEntryDao
import com.example.timetracker.data.entity.TimeEntry

@Database(
    entities = [TimeEntry::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun timeEntryDao(): TimeEntryDao
}