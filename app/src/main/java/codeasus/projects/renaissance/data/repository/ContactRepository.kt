package codeasus.projects.renaissance.data.repository

import codeasus.projects.renaissance.data.dao.ContactDAO
import codeasus.projects.renaissance.data.entity.CHash
import codeasus.projects.renaissance.data.entity.Contact
import codeasus.projects.renaissance.data.relationship.TContactWithRawContacts
import codeasus.projects.renaissance.util.ContactHelper

class ContactRepository(
    private val contactDAO: ContactDAO
) {

    suspend fun insertCHash(cHash: CHash) {
        contactDAO.insertCHash(cHash)
    }

    suspend fun insertTContactWithRawContacts(tContactWithRawContacts: TContactWithRawContacts) {
        contactDAO.insertTContactWithRawContacts(tContactWithRawContacts)
    }

    suspend fun insertTContactWithRawContacts(tContactsWithRawContacts: List<ContactHelper.TContact>) {
        contactDAO.insertTContactsWithRawContacts(tContactsWithRawContacts)
    }

    suspend fun insertCHashesInBulk(cHashes: List<CHash>) {
        contactDAO.insertCHashesInBulk(cHashes)
    }

    suspend fun insertContactsInBulk(contacts: List<Contact>) {
        contactDAO.insertContactsInBulk(contacts)
    }

    suspend fun deleteTContactByID(tContactID: Long) {
        contactDAO.deleteTContactByID(tContactID)
    }

    suspend fun getCHashesCount(): Long {
        return contactDAO.getCHashesCount()
    }

    fun readAllCHashes(): List<CHash> {
        return contactDAO.readAllCHashes()
    }

    fun readAllContacts(): List<Contact> {
        return contactDAO.readAllContacts()
    }
}