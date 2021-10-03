package com.mayouf.chucknorristest.data.source.remote

import com.mayouf.chucknorristest.domain.ChuckNorrisModel
import io.reactivex.Single
import retrofit2.http.GET

interface ChuckNorrisServiceApi {

    @GET("jokes")
    fun getChuckNorrisJokes(): Single<ChuckNorrisModel>
}