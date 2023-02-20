package codeasus.projects.renaissance.data.dao

import androidx.room.*
import codeasus.projects.renaissance.data.entity.CHash
import codeasus.projects.renaissance.data.entity.Contact
import codeasus.projects.renaissance.data.entity.RawContact
import codeasus.projects.renaissance.data.entity.TContact
import codeasus.projects.renaissance.data.relationship.TContactWithRawContacts
import codeasus.projects.renaissance.util.ContactHelper

@Dao
interface ContactDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCHash(cHash: CHash): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact): Long

    @Insert
    suspend fun insertTContact(tContact: TContact): Long

    @Insert
    suspend fun insertTContactsInBulk(tContacts: List<TContact>): List<Long>

    @Insert
    suspend fun insertRawContactsInBulk(rawContacts: List<RawContact>)

    @Transaction
    suspend fun insertTContactWithRawContacts(tContactWithRawContacts: TContactWithRawContacts) {
        val tContactID = insertTContact(tContactWithRawContacts.tContact)
        if (tContactWithRawContacts.rawContacts.isNotEmpty()) {
            val rawContacts = tContactWithRawContacts.rawContacts.map {
                it.contactID = tContactID
                it
            }
            insertRawContactsInBulk(rawContacts)
        }
    }

    @Transaction
    suspend fun insertTContactsWithRawContacts(tContactsWithRawContacts: List<ContactHelper.TContact>) {
        val tContactIDList = insertTContactsInBulk(
            tContactsWithRawContacts.map {
                TContact(
                    0,
                    it.rawID,
                    it.lookupKey,
                    it.displayName
                )
            }
        )
        val allRawContacts = mutableListOf<RawContact>()
        tContactIDList.forEachIndexed { index, tContactID ->
            val tempRawContactBundle = tContactsWithRawContacts[index].phoneNumbers
            allRawContacts.addAll(
                tempRawContactBundle.map {
                    RawContact(0, tContactID, it)
                }
            )
        }
        insertRawContactsInBulk(allRawContacts)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCHashesInBulk(cHashes: List<CHash>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactsInBulk(contacts: List<Contact>)

    @Query("DELETE FROM t_contact WHERE t_contact_id = :tContactID")
    suspend fun deleteTContactByID(tContactID: Long)

    @Query("SELECT COUNT(lookup_key) FROM c_hash")
    suspend fun getCHashesCount(): Long

    @Query("SELECT * FROM c_hash")
    fun readAllCHashes(): List<CHash>

    @Query("SELECT * FROM contact")
    fun readAllContacts(): List<Contact>
}