package codeasus.projects.renaissance.util

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import codeasus.projects.renaissance.data.entity.CHash
import codeasus.projects.renaissance.data.entity.Contact
import java.util.HashSet

object ContactHelper {

    private const val TAG = "CONTACT_HELPER"

    data class TContact(
        val id: Long = 0,
        val rawID: Long = -1,
        val lookupKey: String? = null,
        val displayName: String? = null,
        val phoneNumbers: HashSet<String>
    )

    fun getLocalContactsCount(ctx: Context): Long? {
        val contentProvider = ContactsContract.Contacts.CONTENT_URI
        val projections = arrayOf(ContactsContract.Contacts.LOOKUP_KEY)
        val cursor = ctx.contentResolver.query(
            contentProvider,
            projections,
            null,
            null,
            null
        )
        cursor?.use {
            return it.count.toLong()
        }
        return null
    }

    fun getLocalContactsDetailed(ctx: Context): List<TContact> {
        val contactSet: MutableList<TContact> = mutableListOf()

        val contactContentProvider = ContactsContract.Contacts.CONTENT_URI
        val dataContentProvider = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        // Do not change the order of the array elements
        // If you must change the order or add a new column name,
        // Please update the related columns indexes too
        val contactTableProjections = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
        )

        // Do not change the order of the array elements
        // If you must change the order or add a new column name,
        // Please update the related columns indexes too
        val dataTableProjections = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val contactCur = ctx.contentResolver.query(
            contactContentProvider,
            contactTableProjections,
            null,
            null,
            null
        )

        contactCur?.use { cCur ->
            val idColIdx = cCur.getColumnIndex(contactTableProjections[0])
            val lookupKeyColIdx = cCur.getColumnIndex(contactTableProjections[1])
            val displayNameColIdx = cCur.getColumnIndex(contactTableProjections[2])

            var id: Long = 0
            var rawID: Long?
            var lookupKey: String?
            var displayName: String?

            while (cCur.moveToNext()) {
                rawID = cCur.getLongOrNull(idColIdx)
                lookupKey = cCur.getStringOrNull(lookupKeyColIdx)
                displayName = cCur.getStringOrNull(displayNameColIdx)

                if (rawID == null) return@use

                val tempTC = TContact(
                    id = id++,
                    rawID = rawID,
                    lookupKey = lookupKey,
                    displayName = displayName,
                    phoneNumbers = HashSet()
                )

                val dataCur = ctx.contentResolver.query(
                    dataContentProvider,
                    dataTableProjections,
                    "${dataTableProjections[0]}=?",
                    arrayOf(rawID.toString()),
                    null
                )
                dataCur?.use { dCur ->
                    while (dCur.moveToNext()) {
                        val numberColIdx = dCur.getColumnIndex(dataTableProjections[1])
                        tempTC.phoneNumbers.add(dCur.getString(numberColIdx))
                    }
                }
                contactSet.add(tempTC)
            }
        }
        return contactSet
    }

    fun extractCHashFromLocalContacts(ctx: Context): List<CHash> {
        val cHashList = mutableListOf<CHash>()
        val contentProvider = ContactsContract.Contacts.CONTENT_URI

        val cursor = ctx.contentResolver.query(
            contentProvider,
            null,
            null,
            null,
            null
        )
        val lookUpKeyColumnIndex =
            cursor?.getColumnIndex(ContactsContract.Profile.LOOKUP_KEY)!!
        val idColumnIndex = cursor.getColumnIndex(ContactsContract.Profile._ID)
        val lastUpdatedTimestampColumnIndex =
            cursor.getColumnIndex(ContactsContract.Profile.CONTACT_LAST_UPDATED_TIMESTAMP)

        cursor.use {
            while (cursor.moveToNext()) {
                val id = cursor.getLongOrNull(idColumnIndex)
                val lookUpKey = cursor.getStringOrNull(lookUpKeyColumnIndex)
                val lastUpdateTimestamp = cursor.getLongOrNull(lastUpdatedTimestampColumnIndex)
                if (lookUpKey != null && id != null) {
                    cHashList.add(CHash(lookUpKey, lastUpdateTimestamp))
                }
            }
        }
        return cHashList
    }

    fun getAllContactsWithNumbers(ctx: Context): List<Contact> {
        val contactsList = mutableListOf<Contact>()
        val queryUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projections = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Photo.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY
        )

        val cursor = ctx.contentResolver.query(queryUri, projections, null, null, null)
        cursor?.use {
            while (it.moveToNext()) {
                val phoneNumber = it.getStringOrNull(0)
                if (phoneNumber.isNullOrEmpty().not()) {
                    val displayName = it.getStringOrNull(1)
                    val contactId =
                        it.getLongOrNull(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone._ID))
                    val lookUpKey =
                        it.getStringOrNull(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY))
                    contactId?.let { id ->
                        val uri = ContactsContract.Contacts.getLookupUri(id, lookUpKey).toString()
//                        contactsList.add(
//                            Contact(id, uri, displayName, phoneNumber)
//                        )
                    }
                }
            }
        }
        return contactsList
    }

    fun getNameAndPhoneNumbersFromUri(ctx: Context, uri: Uri) {
        val cursor = uri.let { ctx.contentResolver.query(it, null, null, null, null) }
        cursor?.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
                val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val numberIndex = it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                if (numberIndex >= 0 && idIndex >= 0) {
                    val id = it.getString(idIndex)
                    val numberCursor = ctx.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = $id",
                        null,
                        null
                    )
                    numberCursor?.use { numCursor ->
                        val numberList = mutableListOf<String>()
                        var nextNumber = numCursor.moveToFirst()
                        while (nextNumber) {
                            val number =
                                numCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            numberList.add(numCursor.getString(number))
                            nextNumber = numCursor.moveToNext()
                        }
                    }
                }
            }
        }
    }
}