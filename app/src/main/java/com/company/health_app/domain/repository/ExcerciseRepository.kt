package com.company.health_app.domain.repository

import com.company.health_app.data.datasource.excercise.entity.ExcerciseEntity

interface ExcerciseRepository {

//    val allExcercises: LiveData<List<Excercise>> = liveData {
//        val data = excerciseDao.getAll()
//        emit(data)
//    }

    suspend fun insert(excerciseEntity: ExcerciseEntity)
//        excerciseDao.insert(excercise)


    suspend fun update(excerciseEntity: ExcerciseEntity)
//        excerciseDao.update(excercise)


    suspend fun delete(excerciseEntity: ExcerciseEntity)
//        excerciseDao.delete(excercise)


    suspend fun deleteAll()
//        excerciseDao.deleteAll()


//    suspend fun getLatestWord(): Excercise? {
////        return excerciseDao.getLatestWord()
//    }
}