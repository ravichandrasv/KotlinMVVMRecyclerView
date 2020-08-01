package com.assignment.kotlinmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.kotlinmvvm.model.AssignmentDataSource

class ViewModelFactory(private val repository:AssignmentDataSource):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MuseumViewModel(repository) as T
    }
}