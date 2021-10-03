package com.mayouf.chucknorristest.usecase

import com.mayouf.chucknorristest.domain.model.ChuckNorrisModel
import com.mayouf.chucknorristest.domain.repository.ChuckNorrisRepository
import com.mayouf.chucknorristest.domain.usecase.chucknorrisjokes.GetChuckNorrisJokesUseCase
import com.mayouf.chucknorristest.utils.ThreadScheduler
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class GetChuckNorrisJokesUseCaseTest {
    private lateinit var sut: GetChuckNorrisJokesUseCase
    private lateinit var repository: ChuckNorrisRepository

    private var threadScheduler: ThreadScheduler = mock {
        on { io() } doReturn Schedulers.trampoline()
        on { main() } doReturn Schedulers.trampoline()
        on { computation() } doReturn Schedulers.trampoline()
    }

    @Test
    fun `verify interactions in success case`() {
        val resp: ChuckNorrisModel = mock()

        repository = mock {
            on { getChuckNorrisJokes() } doReturn Single.just(resp)
        }

        sut = GetChuckNorrisJokesUseCase(threadScheduler, repository)

        val testObserver = sut.execute(onLoading = {}, onSuccess = {}, onError = {}).test()

        testObserver.assertNoErrors()
        verify(repository, times(2)).getChuckNorrisJokes()
    }

    @Test
    fun `verify interactions in failure case`() {

        repository = mock {
            on { getChuckNorrisJokes() } doReturn Single.error(Exception())
        }

        sut = GetChuckNorrisJokesUseCase(threadScheduler, repository)

        val testObserver = sut.execute(onLoading = {}, onSuccess = {}, onError = {}).test()

        testObserver.assertError(Exception::class.java)
        verify(repository, times(2)).getChuckNorrisJokes()
    }


}