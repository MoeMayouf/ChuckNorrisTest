package com.mayouf.chucknorristest.data.repository

import com.mayouf.chucknorristest.data.source.remote.ChuckNorrisServiceApi
import com.mayouf.chucknorristest.domain.model.ChuckNorrisModel
import com.mayouf.chucknorristest.domain.repository.ChuckNorrisRepository
import io.reactivex.Single

class ChuckNorrisRepositoryImpl(private val chuckNorrisServiceApi: ChuckNorrisServiceApi) :
    ChuckNorrisRepository {
    override fun getChuckNorrisJokes(): Single<ChuckNorrisModel> =
        chuckNorrisServiceApi.getChuckNorrisJokes()
}