package com.example.birthday_app.db.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.birthday_app.db.entity.birthdaydoa
import com.example.birthday_app.db.entity.birthdaye


@Database(
    entities = [birthdaye::class],
    version =2
)
abstract class birthday_database:RoomDatabase(){
    abstract fun getbirthdao(): birthdaydoa

    companion object{
        @Volatile
        private var instance : birthday_database? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: create(context).also {
                instance = it
            }
        }



        private  fun create(context: Context) = Room.databaseBuilder(context.applicationContext,
            birthday_database::class.java,
            "Birthday1.db")
            .fallbackToDestructiveMigration().build()
    }


}