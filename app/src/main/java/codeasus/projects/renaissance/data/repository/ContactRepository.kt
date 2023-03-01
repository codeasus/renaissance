package codeasus.projects.renaissance.data.repository

import codeasus.projects.renaissance.data.dao.ContactDAO
import codeasus.projects.renaissance.data.entity.ContactHash
import codeasus.projects.renaissance.data.entity.Contact
import codeasus.projects.renaissance.data.relationship.TContactWithRawContacts
import codeasus.projects.renaissance.util.ContactHelper

class ContactRepository(
    private val contactDAO: ContactDAO
) {

    suspend fun insertContactHash(contactHash: ContactHash) {
        contactDAO.insertContactHash(contactHash)
    }

    suspend fun insertTContactWithRawContacts(tContactWithRawContacts: TContactWithRawContacts) {
        contactDAO.insertTContactWithRawContacts(tContactWithRawContacts)
    }

    suspend fun insertTContactWithRawContacts(tContactsWithRawContacts: List<ContactHelper.BufferContact>) {
        contactDAO.insertTContactsWithRawContacts(tContactsWithRawContacts)
    }

    suspend fun insertContactHashesInBulk(contactHashes: List<ContactHash>) {
        contactDAO.insertContactHashesInBulk(contactHashes)
    }

    suspend fun insertContactsInBulk(contacts: List<Contact>) {
        contactDAO.insertContactsInBulk(contacts)
    }

    suspend fun deleteTContactByID(tContactID: Long) {
        contactDAO.deleteTContactByID(tContactID)
    }

    suspend fun getContactHashesCount(): Long {
        return contactDAO.getContactHashesCount()
    }

    fun readAllContactHashes(): List<ContactHash> {
        return contactDAO.readAllContactHashes()
    }

    fun readAllContacts(): List<Contact> {
        return contactDAO.readAllContacts()
    }
}