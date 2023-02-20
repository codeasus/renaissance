package codeasus.projects.renaissance.features.contanct.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.renaissance.data.db.ContactDatabase
import codeasus.projects.renaissance.data.entity.CHash
import codeasus.projects.renaissance.data.entity.Contact
import codeasus.projects.renaissance.data.relationship.TContactWithRawContacts
import codeasus.projects.renaissance.data.repository.ContactRepository
import codeasus.projects.renaissance.util.ContactHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val contactRepository: ContactRepository

    init {
        val databaseInstance = ContactDatabase.getDatabase(application)
        val contactDAO = databaseInstance.contactDAO()
        contactRepository = ContactRepository(contactDAO)
    }

    fun insertCHashesInBulk(cHashes: List<CHash>) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.insertCHashesInBulk(cHashes)
        }
    }

    suspend fun extractCHashesFromLocalContacts(ctx: Context): List<CHash> {
        return withContext(Dispatchers.IO) {
            ContactHelper.extractCHashFromLocalContacts(ctx)
        }
    }

    suspend fun getAllLocalContacts(ctx: Context): List<Contact> {
        return withContext(Dispatchers.IO) {
            ContactHelper.getAllContactsWithNumbers(ctx)
        }
    }

    suspend fun getAllEnigmaContacts(): List<Contact> {
        return withContext(Dispatchers.IO) {
            contactRepository.readAllContacts()
        }
    }

    suspend fun getCHashesCount(): Long {
        return withContext(Dispatchers.IO) {
            contactRepository.getCHashesCount()
        }
    }

    suspend fun getLocalContactsCount(ctx: Context): Long? {
        return withContext(Dispatchers.IO) {
            ContactHelper.getLocalContactsCount(ctx)
        }
    }

    suspend fun readAllCHashes(): List<CHash> {
        return withContext(Dispatchers.IO) {
            contactRepository.readAllCHashes()
        }
    }

    fun deleteTContactByID(tContactID: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.deleteTContactByID(tContactID)
        }
    }

    fun insertContactsInBulk(contacts: List<Contact>) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.insertContactsInBulk(contacts)
        }
    }

    fun insertTContactWithRawContacts(tContactWithRawContacts: TContactWithRawContacts) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.insertTContactWithRawContacts(tContactWithRawContacts)
        }
    }

    fun insertTContactsWithRawContacts(tContactsWithRawContacts: List<ContactHelper.TContact>) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.insertTContactWithRawContacts(tContactsWithRawContacts)
        }
    }
}