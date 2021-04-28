package com.example.birthday_app.db.repository

import com.example.birthday_app.db.entity.birthdaye

class birth_repository(private val a: birthday_database) {
    suspend fun insert(item:birthdaye) = a.getbirthdao().insert(item)

    suspend fun delete(item: birthdaye) = a.getbirthdao().delete(item)

    fun getAll() = a.getbirthdao().getall()
}