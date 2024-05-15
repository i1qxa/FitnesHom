package aps.fithom.aweq.presentation.monitoring.progres

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import aps.fithom.aweq.domain.NutrientsState
import aps.foodfit.jyrbf.data.local.recipe.FoodDataBase

class ProgresViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = FoodDataBase.getInstance(application).recipeDao()

    val stateLD = MutableLiveData<NutrientsState>(NutrientsState.ENERGY)

    val progressDataLD = stateLD.switchMap {
        when(it){
            NutrientsState.ENERGY -> recipeDao.getEnergyData()
            NutrientsState.PROTEIN -> recipeDao.getProteinData()
            NutrientsState.FAT -> recipeDao.getFatData()
            NutrientsState.CARBS -> recipeDao.getCarbsData()
            null -> recipeDao.getEnergyData()
        }
    }

}