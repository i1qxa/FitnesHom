package aps.fithom.aweq.domain

import androidx.lifecycle.MutableLiveData
import aps.fithom.aweq.data.local.ingredient.IngredientDB

object NewIngridients {

    val newIngridientsLD = MutableLiveData<List<IngredientDB>?>()

    fun updateIngredients(listIngridients:List<IngredientDB>){
        newIngridientsLD.value = listIngridients
    }

    fun clear(){
        newIngridientsLD.value = null
    }
}