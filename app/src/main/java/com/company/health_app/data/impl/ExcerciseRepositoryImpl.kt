package com.company.health_app.data.impl

import android.util.Log
import com.company.health_app.data.datasource.excercise.dao.ExcerciseDao
import com.company.health_app.data.datasource.excercise.entity.ExcerciseEntity
import com.company.health_app.domain.repository.ExcerciseRepository
import javax.inject.Inject

class ExcerciseRepositoryImpl @Inject constructor(
    private val dao : ExcerciseDao
) : ExcerciseRepository {
    override suspend fun insert(excerciseEntity: ExcerciseEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun update(excerciseEntity: ExcerciseEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(excerciseEntity: ExcerciseEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        try {
            dao.deleteAll()
            Log.d("ExcerciseRepositoryImpl", "All exercises deleted successfully")
        } catch (e: Exception) {
            Log.e("ExcerciseRepositoryImpl", "Error deleting all exercises", e)
        }
    }

}