package com.company.health_app.data.datasource.excercise.entity

import androidx.room.Entity
import androidx.room.Ignore

open class Test {
    val test : String? = null
}

@Entity(primaryKeys = ["name" , "setNum"])
data class ExcerciseEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id : Int = 0,
    val name : String,
    val setNum : Int,
    @Ignore val ignore: String? = null
)
{
    constructor(name : String , setNum: Int) : this(name , setNum , null)
}






//@Entity(tableName = "Excercises")
//data class ExcerciseEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id : Int = 0,
//    @ColumnInfo(name = "first_column") val name : String,
//    @ColumnInfo(name = "second_column") val setNum : Int,
//)
