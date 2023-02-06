package codeasus.projects.renaissance.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull

object ContactHelper {

    private const val TAG = "CONTACT_HELPER"

    class Contact(
        val id: Long,
        val rawID: Long,
        val displayName: String,
        val primaryPhoneNumber: String,
        val uri: String
    ) {
        override fun hashCode(): Int {
            return id.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if(this === other) return true
            if(other !is Contact) return false
            if(id == other.id) return true
            return false
        }

        override fun toString(): String {
            return "{id: $id; rawID: $rawID; displayName: $displayName; primaryPhoneNumber: $primaryPhoneNumber}"
        }
    }

    fun printContactTable(context: Context) {
        val uri = ContactsContract.Data.CONTENT_URI
        val projections = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts._ID,
        )
        val cursor = context.contentResolver.query(uri, projections, null, null, null)
        while (cursor != null && cursor.moveToNext()) {
            Log.d("CONTACT", cursor.getString(1))
        }
        cursor?.close()
    }

    fun b(context: Context) {
        var cursor: Cursor? = null
        val contentProvider = ContactsContract.Contacts.CONTENT_URI

        cursor = context.contentResolver.query(
            contentProvider,
            null,
            null,
            null,
            null
        )
        val lookUpKeyColumnIndex =
            cursor?.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)!!
        val nameRawContactIDColumnIndex =
            cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID)
        val displayNamePrimaryColumnIndex =
            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
        val displayNameColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        val lastUpdatedTimestampColumnIndex =
            cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP)

        Log.i("B_FUNC", cursor.columnNames.contentToString())

        cursor.use {
            while (cursor.moveToNext()) {
                val lookUpKey = cursor.getStringOrNull(lookUpKeyColumnIndex)
                val displayNamePrimary = cursor.getStringOrNull(displayNamePrimaryColumnIndex)
                val lastUpdateTimestamp =
                    cursor.getLongOrNull(lastUpdatedTimestampColumnIndex)

                Log.d(
                    "B_FUNC -> ",
                    "lookUpKey: $lookUpKey;" +
                            "displayNamePrimary: $displayNamePrimary;" +
                            "lastUpdatedTimestamp: ${lastUpdateTimestamp};"
                )
            }
        }
    }

    fun a(context: Context) {
        var cursor: Cursor? = null
        val contentProvider = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        try {
            cursor = context.contentResolver.query(
                contentProvider,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                ),
                null,
                null,
                null
            )
            while (cursor != null && cursor.moveToNext()) {
                val a = cursor.getString(0)
                val b = cursor.getString(1)
                Log.v("A_FUNC", cursor.columnNames.contentToString())

                Log.v("A_FUNC", "$a $b")
            }
        } catch (e: java.lang.Exception) {
            Log.v("A_FUNC", e.message.toString())
        } finally {
            cursor?.close()
        }
    }

    fun getAllContactsWithNumbers(context: Context): Set<Contact> {
        val contactsSet = mutableSetOf<Contact>()
        val queryUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projections = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Photo.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY
        )

        val cursor = context.contentResolver.query(queryUri, projections, null, null, null)
        cursor?.use {
            while (it.moveToNext()) {
                val phone = it.getStringOrNull(0)
                if (phone.isNullOrEmpty().not()) {
                    val name = it.getStringOrNull(1)
                    val contactId =
                        it.getLongOrNull(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone._ID))
                    val lookUpKey =
                        it.getStringOrNull(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY))
                    contactId?.let { id ->
                        val uri = ContactsContract.Contacts.getLookupUri(id, lookUpKey)
//                        val contactDetails = Contact(phone, name, uri)
//                        contactsSet.add(contactDetails)
                    }
                }
            }
        }
        return contactsSet
    }

    fun getNameAndPhoneNumbersFromUri(context: Context, uri: Uri) {
        val cursor = uri.let { context.contentResolver.query(it, null, null, null, null) }
        cursor?.let {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
                val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val numberIndex = it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                if (numberIndex >= 0 && idIndex >= 0) {
                    val contactName = it.getString(nameIndex)
                    val id = it.getString(idIndex)
                    val numberCursor = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = $id",
                        null,
                        null
                    )
                    numberCursor?.let { numCursor ->
                        val numberList = mutableListOf<String>()
                        var nextNumber = numCursor.moveToFirst()
                        while (nextNumber) {
                            val number =
                                numCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            numberList.add(numCursor.getString(number))
                            nextNumber = numCursor.moveToNext()
                        }
                        numCursor.close()
                    }
                }
            }
        }
    }
}