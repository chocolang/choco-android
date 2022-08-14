package com.chocolang.android.chocoapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chocolang.android.chocoapp.repository.ApiClient
import com.chocolang.android.chocoapp.repository.ChocoHttpClient
import com.chocolang.android.chocoapp.repository.result.GitRepositoryResult
import com.chocolang.android.chocoapp.repository.result.GitUserResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RepositoryViewModel : ViewModel() {
    var repo: ApiClient? = null
    var httpRepo: ChocoHttpClient = ChocoHttpClient()
    var disposable: Disposable? = null
    var repositoryItems: MutableLiveData<GitRepositoryResult> = MutableLiveData()
    var userItems: MutableLiveData<GitUserResult> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    var page = 0
    var hasNextPage = true

    fun getRepositoryList(q: String) {
        Log.d("jwlee", "getList ------ page: ${page}")
        isLoading.value = true
        disposable = repo?.run {
            apiService.getRepositories(q, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    page += 1
                    repositoryItems.value = response
                    isLoading.value = false
                }, { throwable ->
                    isLoading.value = false
                    throwable.printStackTrace()
                    stopList()
                })
        }
    }

    fun getUserList(q: String) {
        isLoading.value = true
        disposable = repo?.run {
            apiService.getUsers(q, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    Log.d("jwlee", "getUserList ------ page: ${response.toString()}")
                    page += 1
                    userItems.value = response
                    isLoading.value = false
                }, { throwable ->
                    isLoading.value = false
                    throwable.printStackTrace()
                    stopList()
                })
        }
    }

    fun getListByHttp(q: String) {
        Log.d("jwlee", "getListByHttp ------ page: ${page}")
        isLoading.value = true
        disposable = Observable.fromCallable {
            return@fromCallable httpRepo.getRepositoryList(q, page)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                page += 1
                repositoryItems.value = response
                isLoading.value = false
            }, { throwable ->
                isLoading.value = false
                throwable.printStackTrace()
                stopList()
            })
    }

    fun getDetail() {
        Log.d("jwlee", "getDetail ------")
        isLoading.value = true
        disposable = repo?.run {
            apiService.getRepositories("recycler", page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    repositoryItems.value = response
                    isLoading.value = false
                }, { throwable ->
                    isLoading.value = false
                    throwable.printStackTrace()
                    stopList()
                })
        }
    }

    fun stopList() {
        disposable?.dispose()
    }
}