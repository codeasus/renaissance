package codeasus.projects.renaissance.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codeasus.projects.renaissance.data.entity.CDiff
import kotlinx.coroutines.flow.Flow

@Dao
interface CDiffDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCDiff(cDiff: CDiff)

    @Insert
    suspend fun addCDiffInBulk(cDiffSet: Set<CDiff>)

    @Query("SELECT * from c_diff ORDER BY last_update_timestamp DESC")
    suspend fun readAllCDiff(): Flow<Set<CDiff>>
}