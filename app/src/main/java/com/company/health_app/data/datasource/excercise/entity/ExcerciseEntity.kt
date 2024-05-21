package com.company.health_app.data.datasource.excercise.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Excercise")
data class ExcerciseEntity(
    val name : String,
    var setNum : Int,
    @PrimaryKey(autoGenerate = true)val id : Int = 0,
) : Parcelable
