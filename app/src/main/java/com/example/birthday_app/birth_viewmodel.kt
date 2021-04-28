package com.example.birthday_app

import androidx.lifecycle.ViewModel
import com.example.birthday_app.db.entity.birthdaye
import com.example.birthday_app.db.repository.birth_repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class birth_viewmodel(private val repository:birth_repository):ViewModel() {
    fun insert(item:birthdaye) = CoroutineScope(Dispatchers.Main).launch {
repository.insert(item)
    }

    fun delete(item: birthdaye) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getall() = repository.getAll()
}