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
            return this.id.toInt()
        }

        override fun equals(other: Any?): Boolean {
            return other == this
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

    fun addContacts() {

    }

    fun a(context: Context) {
        var cursor: Cursor? = null
        val contentProvider = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        try {
            cursor = context.contentResolver.query(
                contentProvider,
                arrayOf(ContactsContract.CommonDataKinds.Phone., ContactsContract.CommonDataKinds.Phone.NUMBER),
                null,
                null,
                null
            )
            while (cursor != null && cursor.moveToNext()) {

                val a = cursor.getString(0)
                val b = cursor.getString(1)
//                val c = cursor.getString(2)
//                val d = cursor.getString(3)
//                val e = cursor.getString(4)
                Log.d("COLS_$TAG", cursor.columnNames.contentToString())

                Log.d("C_$TAG", "$a $b")
            }
        } catch (e: java.lang.Exception) {
            Log.e(TAG, e.message.toString())
        } finally {
            cursor?.close()
        }
    }

    fun displayContentProviders() {
        val one = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val two = ContactsContract.RawContacts.CONTENT_URI
        val three = ContactsContract.RawContactsEntity.CONTENT_URI
        val four = ContactsContract.Contacts.CONTENT_URI

        Log.d(
            TAG,
            "ContactsContract.CommonDataKinds.Phone.CONTENT_URI $one\n" +
                    "ContactsContract.RawContacts.CONTENT_URI $two\n" +
                    "ContactsContract.RawContactsEntity.CONTENT_URI $three\n" +
                    "ContactsContract.Contacts.CONTENT_URI $four"
        )
    }

    fun getAllContactsWithNumbers(context: Context): Set<Contact> {
        val contactsSet = mutableSetOf<Contact>()

        var cursor: Cursor? = null
        try {
            val queryUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val projections = arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY
            )
            cursor = context.contentResolver.query(queryUri, projections, null, null, null)

            while (cursor != null && cursor.moveToNext()) {
                if (cursor.getString(0).isNullOrEmpty().not()) {
                    val phone = cursor.getString(0)
                    val name = cursor.getStringOrNull(1)
                    val contactId =
                        cursor.getLongOrNull(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone._ID))
                    val lookUpKey =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY))
                    contactId?.let {
                        val uri: Uri = ContactsContract.Contacts.getLookupUri(contactId, lookUpKey)
//                        val contactDetails = Contact(phone, name, uri)
                        //results.put(formattedPhoneNumber, contactDetails)
                    }
                }
            }

        } catch (e: SecurityException) {
            Log.d(TAG, "Contact access security exception")
        } finally {
            cursor?.close()
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