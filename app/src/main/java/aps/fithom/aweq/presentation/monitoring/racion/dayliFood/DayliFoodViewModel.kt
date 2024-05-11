package aps.fithom.aweq.presentation.monitoring.racion.dayliFood

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import aps.fithom.aweq.domain.getCurrentDateFormatted
import aps.foodfit.jyrbf.data.local.recipe.FoodDataBase

class DayliFoodViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = FoodDataBase.getInstance(application).recipeDao()
    private val ingredientsDao = FoodDataBase.getInstance(application).ingredientDao()

    val currentNutrients = recipeDao.fetchNutrients(getCurrentDateFormatted())
    val recipeList = recipeDao.getCurrentRacion(getCurrentDateFormatted())

}