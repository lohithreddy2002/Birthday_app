package com.example.birthday_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.birthday_app.db.repository.birth_repository

class birthfactory(private val repo : birth_repository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return birth_viewmodel(repo) as T
    }
}