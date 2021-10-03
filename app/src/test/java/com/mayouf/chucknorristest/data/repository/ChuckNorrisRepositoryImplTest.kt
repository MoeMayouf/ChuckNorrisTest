package com.mayouf.chucknorristest.data.repository

import com.mayouf.chucknorristest.data.source.remote.ChuckNorrisServiceApi
import com.mayouf.chucknorristest.domain.model.ChuckNorrisModel
import com.mayouf.chucknorristest.domain.repository.ChuckNorrisRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Test

class ChuckNorrisRepositoryImplTest {
    lateinit var sut: ChuckNorrisRepository
    lateinit var api: ChuckNorrisServiceApi

    @Test
    fun `get cat`() {
        val resp: ChuckNorrisModel = mock()

        api = mock {
            on { it.getChuckNorrisJokes() } doReturn Single.just(resp)
        }

        sut = ChuckNorrisRepositoryImpl(api)

        val testObserver = sut.getChuckNorrisJokes().test()

        testObserver.assertNoErrors()
        testObserver.assertValue { it == resp }
        testObserver.assertComplete()

        verify(api).getChuckNorrisJokes()
    }
}