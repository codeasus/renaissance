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
        val primaryPhoneNumber: String
    ) {
        override fun hashCode(): Int {
            return this.id.toInt()
        }

        override fun equals(other: Any?): Boolean {
            if (other == this) return true
            if (other !is Contact) return false
            return false
        }
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
        return emptySet()
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