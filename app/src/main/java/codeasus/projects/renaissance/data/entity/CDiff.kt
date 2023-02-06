package codeasus.projects.renaissance.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.Objects

@Entity(tableName = "c_diff")
class CDiff(
    @ColumnInfo(name = "lookup_key")
    val lookupKey: String,
    @ColumnInfo(name = "last_update_timestamp")
    val lastUpdateTimestamp: Long
) {
    override fun hashCode(): Int {
        return Objects.hash(lastUpdateTimestamp)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.lookupKey == (other as CDiff).lookupKey) return true
        return false
    }
}