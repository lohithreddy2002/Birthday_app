package com.example.birthday_app.db.entity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface birthdaydoa {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: birthdaye)

    @Delete
    suspend fun delete(item: birthdaye)

    @Query("SELECT * FROM BIRTHDAY_TABLE")
    fun getall():LiveData<List<birthdaye>>
}