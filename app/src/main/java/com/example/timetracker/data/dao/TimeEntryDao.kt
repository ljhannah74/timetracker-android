package com.example.timetracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.timetracker.data.entity.TimeEntry
import kotlinx.coroutines.flow.Flow
import androidx.room.Query

@Dao
interface TimeEntryDao {

    @Insert
    suspend fun insertTimeEntry(entry: TimeEntry)

    @Query("SELECT * FROM time_entries")
    fun getAll(): Flow<List<TimeEntry>>
}
