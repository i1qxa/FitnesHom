package aps.fithom.aweq.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aps.fithom.aweq.domain.BASE_DELAY
import aps.fithom.aweq.domain.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    val progressLoadingLD = MutableLiveData<Long>()
    val state = MutableLiveData(State.LOADING)
    init {
        var counter = BASE_DELAY
        viewModelScope.launch {
            while (counter>0){
                delay(100)
                counter-=100
            }
            state.postValue(State.COMPLETE)
        }

    }

}