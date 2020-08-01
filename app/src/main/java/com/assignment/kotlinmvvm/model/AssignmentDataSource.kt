package com.assignment.kotlinmvvm.model

import com.assignment.kotlinmvvm.data.OperationCallback

interface AssignmentDataSource {

    fun retrieveMuseums(callback: OperationCallback<Assignment>)
    fun cancel()
}