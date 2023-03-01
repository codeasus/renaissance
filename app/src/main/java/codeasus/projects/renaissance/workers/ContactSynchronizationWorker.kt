package codeasus.projects.renaissance.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import codeasus.projects.renaissance.data.db.ContactDatabase
import codeasus.projects.renaissance.data.repository.ContactRepository

class ContactSynchronizationWorker(
    private val ctx: Context,
    private val params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    private val contactRepository: ContactRepository

    companion object {
        private val TAG = ContactSynchronizationWorker::class.java.name
    }

    init {
        val databaseInstance = ContactDatabase.getDatabase(ctx)
        val contactDAO = databaseInstance.contactDAO()
        contactRepository = ContactRepository(contactDAO)
    }

    override suspend fun doWork(): Result {

        return Result.success()
    }

}