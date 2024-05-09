package aps.fithom.aweq.presentation.questions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aps.fithom.aweq.domain.setCurrentWeight
import aps.fithom.aweq.domain.setInitialSetupComplete
import aps.fithom.aweq.domain.setTargetCalories
import aps.fithom.aweq.domain.setTargetCarb
import aps.fithom.aweq.domain.setTargetFat
import aps.fithom.aweq.domain.setTargetProtein
import aps.fithom.aweq.domain.setTargetWeight
import kotlinx.coroutines.launch

class QuestionsViewModel(application: Application): AndroidViewModel(application) {

    private val context = application.baseContext
    var currentWeight = 0.0
    var targetWeight = 0.0
    var targetCalories = 0
    var targetProtein = 0
    var targetFat = 0
    var targetCarbs = 0
    fun saveData(){
        viewModelScope.launch {
            with(context){
                setCurrentWeight(currentWeight)
                setTargetWeight(targetWeight)
                setTargetCalories(targetCalories)
                setTargetProtein(targetProtein)
                setTargetFat(targetFat)
                setTargetCarb(targetCarbs)
                setInitialSetupComplete()
            }
        }
    }

}