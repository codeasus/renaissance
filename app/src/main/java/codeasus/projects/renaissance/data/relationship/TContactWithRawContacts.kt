package codeasus.projects.renaissance.data.relationship

import androidx.room.Embedded
import androidx.room.Relation
import codeasus.projects.renaissance.data.entity.RawContact
import codeasus.projects.renaissance.data.entity.TContact

class TContactWithRawContacts (
    @Embedded
    val tContact: TContact,
    @Relation(
        parentColumn = "t_contact_id",
        entityColumn = "contact_id"
    )
    val rawContacts: List<RawContact>
)