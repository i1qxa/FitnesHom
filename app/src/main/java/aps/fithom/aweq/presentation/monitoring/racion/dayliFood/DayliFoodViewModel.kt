package aps.fithom.aweq.presentation.monitoring.racion.dayliFood

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import aps.fithom.aweq.data.remote.RecipeByUriService
import aps.fithom.aweq.data.remote.RecipeItemShort
import aps.fithom.aweq.domain.APP_ID
import aps.fithom.aweq.domain.APP_KEY
import aps.fithom.aweq.domain.getCurrentDateFormatted
import aps.foodfit.jyrbf.data.local.recipe.FoodDataBase
import kotlinx.coroutines.launch

class DayliFoodViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = FoodDataBase.getInstance(application).recipeDao()
    private val retrofitByUri = RecipeByUriService.getInstanceByUri()

    val currentNutrients = recipeDao.fetchNutrients(getCurrentDateFormatted())
    val recipeList = recipeDao.getCurrentRacion(getCurrentDateFormatted())

    fun addIngredientsToShoppingList(recipeUri:String){
        var recipeShort:RecipeItemShort? = null
        viewModelScope.launch {
            val response = retrofitByUri.getRecipeByUri("public", recipeUri, APP_ID, APP_KEY)
            if (response.isSuccessful){
                response.body()?.hits?.map {
                    recipeShort = it.recipe?.getRecipeShort(0)
                    if (recipeShort!=null){
                        recipeShort?.translateIngredients()
                    }
                }
            }
        }
    }

}