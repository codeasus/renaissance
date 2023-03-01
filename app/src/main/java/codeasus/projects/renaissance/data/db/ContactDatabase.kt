package codeasus.projects.renaissance.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import codeasus.projects.renaissance.data.dao.ContactDAO
import codeasus.projects.renaissance.data.entity.ContactHash
import codeasus.projects.renaissance.data.entity.Contact
import codeasus.projects.renaissance.data.entity.RawContact
import codeasus.projects.renaissance.data.entity.TContact

@Database(
    entities = [
        ContactHash::class,
        Contact::class,
        TContact::class,
        RawContact::class
    ],
    version = 5,
    exportSchema = false
)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDAO(): ContactDAO

    companion object {

        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contact_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}