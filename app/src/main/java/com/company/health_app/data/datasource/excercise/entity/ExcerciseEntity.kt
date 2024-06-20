package com.company.health_app.data.datasource.excercise.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "excerciseroutine")
data class ExcerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    val setNum : Int,
)




