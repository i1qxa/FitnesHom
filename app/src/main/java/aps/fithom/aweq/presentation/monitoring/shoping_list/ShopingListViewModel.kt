package aps.fithom.aweq.presentation.monitoring.shoping_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import aps.fithom.aweq.data.local.ingredient.IngredientDB
import aps.foodfit.jyrbf.data.local.recipe.FoodDataBase
import kotlinx.coroutines.launch

class ShopingListViewModel(application: Application) : AndroidViewModel(application) {

    private val ingredientDao = FoodDataBase.getInstance(application).ingredientDao()

    val listOfIngredients = ingredientDao.getShoppingList()

    fun removeIngredient(ingredientItem: IngredientDB){
        viewModelScope.launch {
            ingredientDao.removeFromShoppingList(ingredientItem)
        }
    }

}