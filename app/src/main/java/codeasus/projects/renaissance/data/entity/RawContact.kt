package codeasus.projects.renaissance.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "raw_contact",
    foreignKeys = [
        ForeignKey(
            entity = TContact::class,
            parentColumns = ["t_contact_id"],
            childColumns = ["contact_id"],
            onDelete = CASCADE
        )
    ]
)
data class RawContact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "raw_contact_id")
    val id: Long = 0,
    @ColumnInfo(name = "contact_id")
    var contactID: Long = 0,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String? = null
)