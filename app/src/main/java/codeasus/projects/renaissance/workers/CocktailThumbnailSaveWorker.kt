package codeasus.projects.renaissance.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class CocktailThumbnailSaveWorker(
    private val ctx: Context,
    private val params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }
}