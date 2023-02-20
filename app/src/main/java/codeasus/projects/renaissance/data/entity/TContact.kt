package codeasus.projects.renaissance.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "t_contact"
)
data class TContact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "t_contact_id")
    val id: Long,
    @ColumnInfo(name = "raw_id")
    val rawID: Long = -1,
    @ColumnInfo(name = "lookup_key")
    val lookupKey: String? = null,
    @ColumnInfo(name = "display_name")
    val displayName: String? = null,
)