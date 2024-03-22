package com.company.health_app

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Excercise")
data class Excercise(
    val name : String,
    var setNum : Int,
    @PrimaryKey(autoGenerate = true)val id : Int = 0,
) : Parcelable
