package com.assignment.kotlinmvvm.model

import android.util.Log
import com.assignment.kotlinmvvm.data.ApiClient
import com.assignment.kotlinmvvm.data.MuseumResponse
import com.assignment.kotlinmvvm.data.OperationCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG="CONSOLE"

class AssignmentRepository:AssignmentDataSource {

    private var call:Call<MuseumResponse>?=null

    override fun retrieveMuseums(callback: OperationCallback<Assignment>) {
        call=ApiClient.build()?.museums()
        call?.enqueue(object :Callback<MuseumResponse>{
            override fun onFailure(call: Call<MuseumResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<MuseumResponse>, response: Response<MuseumResponse>) {
                response.body()?.let {
                    if(response.isSuccessful && (it.isSuccess())){
                        Log.v(TAG, "data ${it.rows}")
                        callback.onSuccess(it.rows)
                    }else{
                        callback.onError("error")
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}