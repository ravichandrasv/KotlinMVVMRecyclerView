package com.assignment.kotlinmvvm.di

import androidx.lifecycle.ViewModelProvider
import com.assignment.kotlinmvvm.model.AssignmentDataSource
import com.assignment.kotlinmvvm.model.AssignmentRepository
import com.assignment.kotlinmvvm.viewmodel.ViewModelFactory

object Injection {

    private val museumDataSource:AssignmentDataSource = AssignmentRepository()
    private val museumViewModelFactory = ViewModelFactory(museumDataSource)

    fun providerRepository():AssignmentDataSource{
        return museumDataSource
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory{
        return museumViewModelFactory
    }
}