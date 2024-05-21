package com.company.health_app.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

import com.company.health_app.Excercise
import com.company.health_app.ExcerciseDao

class ExcerciseRepository(private val excerciseDao: ExcerciseDao) {

    val allExcercises: LiveData<List<Excercise>> = liveData {
        val data = excerciseDao.getAll()
        emit(data)
    }

    suspend fun insert(excercise: Excercise) {
        excerciseDao.insert(excercise)
    }

    suspend fun update(excercise: Excercise) {
        excerciseDao.update(excercise)
    }

    suspend fun delete(excercise: Excercise) {
        excerciseDao.delete(excercise)
    }

    suspend fun deleteAll() {
        excerciseDao.deleteAll()
    }

    suspend fun getLatestWord(): Excercise? {
        return excerciseDao.getLatestWord()
    }
}