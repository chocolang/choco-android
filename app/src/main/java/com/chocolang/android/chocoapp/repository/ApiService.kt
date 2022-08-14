package com.chocolang.android.chocoapp.repository

import com.chocolang.android.chocoapp.repository.result.GitRepositoryResult
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("${SEARCH}repositories")
    fun getRepositories(
        @Query("q") param: String,
        @Query("page") page: Int
    ): Single<GitRepositoryResult>

    @GET(SAMPLE)
    fun getList2(
    ): Single<String>
}