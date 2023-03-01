package codeasus.projects.renaissance.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Objects

@Entity(tableName = "contact_hash")
data class ContactHash(
    @PrimaryKey
    @ColumnInfo(name = "lookup_key")
    val lookupKey: String,
    @ColumnInfo(name = "last_update_timestamp")
    val lastUpdateTimestamp: Long?
) {
    override fun hashCode(): Int {
        return Objects.hash(lookupKey, lastUpdateTimestamp)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as ContactHash
        if (this.lookupKey == other.lookupKey
            && this.lastUpdateTimestamp == other.lastUpdateTimestamp
        ) return true
        return false
    }

    override fun toString(): String {
        return "{lookup_key: $lookupKey; last_update_timestamp: $lastUpdateTimestamp}"
    }
}