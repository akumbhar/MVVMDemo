package com.example.mvvmdiapplication.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mvvmdiapplication.common.doLogE
import com.example.mvvmdiapplication.repository.retrofit.RemoteDao
import com.example.mvvmdiapplication.repository.room.LocalDao
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainRepository(val remoteDao: RemoteDao, val localDao: LocalDao) {

    fun getFacts(): LiveData<List<Fact>> {

        remoteDao.getFactsFromAPI()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)


        return localDao.getAllFacts()

    }

    val observer = object : Observer<APIResponse> {
        override fun onSubscribe(d: Disposable?) {
            doLogE("onSubscribe")
        }

        override fun onNext(apiResponse: APIResponse) {
            doLogE("onSuccess: ")
            localDao.deleteAllFacts()
            localDao.insertAll(apiResponse.rows)
        }

        override fun onError(e: Throwable) {
            doLogE("onError: " + e.message)
        }

        override fun onComplete() {
            Log.e("as","onComplete")
        }

    }
}