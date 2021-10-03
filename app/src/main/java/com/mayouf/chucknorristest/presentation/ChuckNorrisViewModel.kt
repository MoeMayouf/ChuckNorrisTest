package com.mayouf.chucknorristest.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mayouf.chucknorristest.domain.model.ChuckNorrisModel
import com.mayouf.chucknorristest.domain.usecase.chucknorrisjokes.GetChuckNorrisJokesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChuckNorrisViewModel @Inject constructor(
    application: Application,
    private val getChuckNorrisJokesUseCase: GetChuckNorrisJokesUseCase
) : AndroidViewModel(application) {

    val loadingLiveData = MutableLiveData<Boolean>()
    val contentLiveData = MutableLiveData<ChuckNorrisModel>()
    val errorLiveData = MutableLiveData<Throwable>()

    fun getChuckNorrisJokes() {
        getChuckNorrisJokesUseCase.execute(
            onLoading = { loadingLiveData.postValue(true) },
            onSuccess = {
                loadingLiveData.postValue(false)
                contentLiveData.value = it
            },
            onError = {
                Log.i("Error", it.printStackTrace().toString())
                errorLiveData.value = it
            }
        )
    }

}