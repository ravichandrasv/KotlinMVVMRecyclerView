package com.assignment.kotlinmvvm.data

import com.assignment.kotlinmvvm.model.Assignment

data class MuseumResponse(val rows:List<Assignment>?){
    fun isSuccess():Boolean= (true)
}