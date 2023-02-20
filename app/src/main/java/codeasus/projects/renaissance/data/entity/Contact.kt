package codeasus.projects.renaissance.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "contact"
)
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    val id: Long = 0,
    @ColumnInfo(name = "raw_id")
    val rawID: Long,
    @ColumnInfo(name = "lookup_key")
    val lookupKey: String,
    @ColumnInfo(name = "display_name")
    val displayName: String?,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String?,
) {
    override fun hashCode(): Int {
        return Objects.hash(phoneNumber, displayName)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as Contact
        if (this.phoneNumber == other.phoneNumber &&
            this.displayName == other.displayName
        ) return true
        return false
    }

    override fun toString(): String {
        return "{id: $id; rawID: $rawID; lookup_key: $lookupKey; displayName: $displayName; phoneNumber: $phoneNumber}"
    }
}