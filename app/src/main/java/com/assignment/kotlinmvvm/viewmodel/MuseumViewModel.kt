package com.assignment.kotlinmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.kotlinmvvm.data.OperationCallback
import com.assignment.kotlinmvvm.model.Assignment
import com.assignment.kotlinmvvm.model.AssignmentDataSource

class MuseumViewModel(private val repository: AssignmentDataSource):ViewModel() {

    private val _museums = MutableLiveData<List<Assignment>>().apply { value = emptyList() }
    val museums: LiveData<List<Assignment>> = _museums

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val _isEmptyList=MutableLiveData<Boolean>()
    val isEmptyList:LiveData<Boolean> = _isEmptyList

    /*
    If you require that the data be loaded only once, you can consider calling the method
    "loadMuseums()" on constructor. Also, if you rotate the screen, the service will not be called.

    init {
        //loadMuseums()
    }
     */

    fun loadMuseums(){
        _isViewLoading.postValue(true)
        repository.retrieveMuseums(object:OperationCallback<Assignment>{
            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue( error)
            }

            override fun onSuccess(data: List<Assignment>?) {
                _isViewLoading.postValue(false)

                if(data!=null){
                    if(data.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _museums.value= data
                    }
                }
            }
        })
    }

}