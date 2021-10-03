package com.mayouf.chucknorristest.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mayouf.chucknorristest.domain.model.ChuckNorrisModel
import com.mayouf.chucknorristest.domain.repository.ChuckNorrisRepository
import com.mayouf.chucknorristest.domain.usecase.chucknorrisjokes.GetChuckNorrisJokesUseCase
import com.mayouf.chucknorristest.utils.ThreadScheduler
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ChuckNorrisViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    lateinit var repo: ChuckNorrisRepository

    @Mock
    lateinit var mockLiveDataObserver: Observer<ChuckNorrisModel>

    private var threadScheduler: ThreadScheduler = mock {
        on { io() } doReturn Schedulers.trampoline()
        on { main() } doReturn Schedulers.trampoline()
        on { computation() } doReturn Schedulers.trampoline()
    }

    @Before
    fun `set up`() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Given useCase returns data, when getChuckNorrisJokes() called, then update contentliveData`() {
        //Setting how up the mock behaves
        val resp: ChuckNorrisModel = mock()
        repo = mock {
            on { it.getChuckNorrisJokes() } doReturn Single.just(resp)
        }
        val useCase = GetChuckNorrisJokesUseCase(threadScheduler, repo)
        val myViewModel = ChuckNorrisViewModel(Application(), useCase)
        myViewModel.contentLiveData.observeForever(mockLiveDataObserver)
        myViewModel.getChuckNorrisJokes()
        Assert.assertEquals(resp, myViewModel.contentLiveData.value)
    }

    @Test
    fun `Given useCase returns error, when getChuckNorrisJokes() called, then do not change contentLiveData`() {
        //Setting how up the mock behaves
        repo = mock {
            on { it.getChuckNorrisJokes() } doReturn Single.error(Exception())
        }
        val useCase = GetChuckNorrisJokesUseCase(threadScheduler, repo)
        val myViewModel = ChuckNorrisViewModel(Application(), useCase)
        myViewModel.contentLiveData.observeForever(mockLiveDataObserver)
        myViewModel.loadingLiveData
        verify(mockLiveDataObserver, times(0)).onChanged(any())
    }

    @After
    fun tearDown() {
    }
}