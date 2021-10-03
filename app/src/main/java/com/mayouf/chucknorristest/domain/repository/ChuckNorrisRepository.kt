package com.mayouf.chucknorristest.domain.repository

import com.mayouf.chucknorristest.domain.model.ChuckNorrisModel
import io.reactivex.Single

interface ChuckNorrisRepository {

    fun getChuckNorrisJokes(): Single<ChuckNorrisModel>
}