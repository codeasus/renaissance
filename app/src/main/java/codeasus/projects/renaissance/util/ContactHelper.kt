package codeasus.projects.renaissance.util

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import java.util.HashSet

object ContactHelper {

    private val TAG = ContactHelper::class.java.name

    data class ContactDetails(
        val phoneNumber: String? = null,
        val displayName: String? = null,
        val rawID: Long? = null,
        val lookupKey: String? = null
    )

    data class ContactHash(
        val rawID: Long,
        val lookupKey: String,
        val lastUpdateTimestamp: Long?
    )

    data class BufferContact(
        val id: Long = 0,
        val rawID: Long = -1,
        val lookupKey: String? = null,
        val displayName: String? = null,
        var phoneNumber: String? = null,
        val phoneNumbers: HashSet<String>
    )

    fun getLocalContactsCount(ctx: Context): Long {
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
        return 0
    }

    fun getLocalContactsWithPhoneNumbersA(ctx: Context): HashMap<String, ContactDetails> {
        val results = hashMapOf<String, ContactDetails>()

        val queryURI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projections = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY
        )

        val cursor = ctx.contentResolver.query(
            queryURI,
            projections,
            null,
            null,
            null
        )

        cursor?.use {
            try {
                val phoneNumberIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val nameIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val contactIDIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)
                val lookupKeyIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY)

                while (cursor.moveToNext()) {
                    val contactID = cursor.getLongOrNull(contactIDIndex)
                    val lookupKey = cursor.getStringOrNull(lookupKeyIndex)
                    val phoneNumber = cursor.getString(phoneNumberIndex)
                    val displayName = cursor.getString(nameIndex)
                    contactID?.let {
                        val contactDetails =
                            ContactDetails(phoneNumber, displayName, contactID, lookupKey)
                        results.put(phoneNumber, contactDetails)
                    }
                }
            } catch (e: SecurityException) {
                Log.d(TAG, "Contact access security exception: $e")
            }
        }
        return results
    }

    fun getLocalContactsWithPhoneNumbersB(
        ctx: Context,
        localNumber: String?
    ): MutableList<BufferContact> {
        val contactSet: MutableList<BufferContact> = mutableListOf()

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

                val tempTC = BufferContact(
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
                        val phoneNumberColIdx = dCur.getColumnIndex(dataTableProjections[1])
                        val phoneNumber = dCur.getString(phoneNumberColIdx)
                        tempTC.phoneNumbers.add(phoneNumber)
                    }
                }
                contactSet.add(tempTC)
            }
        }
        return contactSet
    }

    fun getContactHashesFromLocalContacts(ctx: Context): List<ContactHash> {
        val contactHashList = mutableListOf<ContactHash>()
        val contentProvider = ContactsContract.Contacts.CONTENT_URI
        val projections = arrayOf(
            ContactsContract.Profile._ID,
            ContactsContract.Profile.LOOKUP_KEY,
            ContactsContract.Profile.CONTACT_LAST_UPDATED_TIMESTAMP
        )
        val cursor = ctx.contentResolver.query(
            contentProvider,
            projections,
            null,
            null,
            null
        )
        cursor?.use {
            val idColIdx = cursor.getColumnIndex(projections[0])
            val lookUpKeyColIdx = cursor.getColumnIndex(projections[1])
            val lastUpdatedTimestampColIdx = cursor.getColumnIndex(projections[2])

            while (cursor.moveToNext()) {
                val id = cursor.getLongOrNull(idColIdx)
                val lookUpKey = cursor.getStringOrNull(lookUpKeyColIdx)
                val lastUpdateTimestamp = cursor.getLongOrNull(lastUpdatedTimestampColIdx)
                if (lookUpKey != null && id != null) {
                    contactHashList.add(ContactHash(id, lookUpKey, lastUpdateTimestamp))
                }
            }
        }
        return contactHashList
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