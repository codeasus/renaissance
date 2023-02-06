package codeasus.projects.renaissance.data.repository

import codeasus.projects.renaissance.data.dao.CDiffDAO
import codeasus.projects.renaissance.data.entity.CDiff

class ContactRepository(private val cDiffDAO: CDiffDAO) {

    suspend fun addCDiff(cDiff: CDiff) {
        cDiffDAO.addCDiff(cDiff)
    }

    suspend fun addCDiffInBulk(cDiffSet: Set<CDiff>) {
        cDiffDAO.addCDiffInBulk(cDiffSet)
    }

    suspend fun readAllCDiff() {
        cDiffDAO.readAllCDiff()
    }
}