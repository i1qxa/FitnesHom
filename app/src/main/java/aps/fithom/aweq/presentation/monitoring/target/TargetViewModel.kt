package aps.fithom.aweq.presentation.monitoring.target

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import aps.fithom.aweq.domain.NutrientsState

class TargetViewModel(application: Application) : AndroidViewModel(application) {

    private val context by lazy { application.applicationContext }

    var currentValue = 0

    val nutrientState = MutableLiveData<NutrientsState?>(null)


}

