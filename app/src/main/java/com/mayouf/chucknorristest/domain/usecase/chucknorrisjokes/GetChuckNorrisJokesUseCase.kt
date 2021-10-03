package com.mayouf.chucknorristest.domain.usecase.chucknorrisjokes

import com.mayouf.chucknorristest.domain.model.ChuckNorrisModel
import com.mayouf.chucknorristest.domain.repository.ChuckNorrisRepository
import com.mayouf.chucknorristest.domain.usecase.base.SingleUseCase
import com.mayouf.chucknorristest.utils.ThreadScheduler
import io.reactivex.Single
import javax.inject.Inject

class GetChuckNorrisJokesUseCase @Inject constructor(
    threadScheduler: ThreadScheduler,
    private val repository: ChuckNorrisRepository
) : SingleUseCase<ChuckNorrisModel>(threadScheduler) {

    override fun createSingleUseCase(): Single<ChuckNorrisModel> = repository.getChuckNorrisJokes()
}