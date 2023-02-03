package codeasus.projects.renaissance.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import java.util.Date

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

    fun b(context: Context) {
        var cursor: Cursor? = null
        val contentProvider = ContactsContract.Contacts.CONTENT_URI

        try {
            cursor = context.contentResolver.query(
                contentProvider,
                null,
                "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} = ?",
                arrayOf("Aygun Test"),
                null
            )
            val lookUpKeyColumnIndex =
                cursor?.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)!!
            val nameRawContactIDColumnIndex =
                cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID)
            val displayNamePrimaryColumnIndex =
                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val displayNameColumnIndex =
                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val contactLastUpdatedTimestampColumnIndex =
                cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP)

            while (cursor.moveToNext()) {
                val a = cursor.getString(lookUpKeyColumnIndex)
                val b = cursor.getString(nameRawContactIDColumnIndex)
                val c = cursor.getString(displayNamePrimaryColumnIndex)
                val d = cursor.getString(displayNameColumnIndex)
                val e = cursor.getLong(contactLastUpdatedTimestampColumnIndex)

                Log.v("B_FUNC -> ", "a: $a; b: $b; c: $c; d: $d; e: ${Date(e)};")
            }
            Log.i("B_FUNC", cursor?.columnNames.contentToString())
        } catch (e: Exception) {
            Log.v("B_FUNC", e.message.toString())
        }
        cursor?.close()
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

    fun displayContactContentProviders() {
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