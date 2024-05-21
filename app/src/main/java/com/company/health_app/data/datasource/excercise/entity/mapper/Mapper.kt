package com.company.health_app.data.datasource.excercise.entity.mapper

import com.company.health_app.data.datasource.excercise.entity.ExcerciseEntity
import com.company.health_app.domain.model.ExcerciseModel

fun ExcerciseEntity.toExcerciseModel(): ExcerciseModel {
    return ExcerciseModel(
        name = this.name,
        setNum = this.setNum,
    )
}

fun ExcerciseModel.toExcerciseEntity(): ExcerciseEntity {
    return ExcerciseEntity(
        name = this.name,
        setNum = this.setNum,
    )
}



