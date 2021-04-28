package com.example.birthday_app.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "birthday_table")
data class birthdaye(
    @PrimaryKey val name:String,
    val Date : String
)
