package aps.fithom.aweq.presentation.monitoring.racion.recipeList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aps.fithom.aweq.data.remote.ITEM_SPLITTER
import aps.fithom.aweq.data.remote.RecipeItemShort
import aps.fithom.aweq.data.remote.RecipeService
import aps.fithom.aweq.data.remote.RecipeTranslatedSubData
import aps.fithom.aweq.domain.APP_ID
import aps.fithom.aweq.domain.APP_KEY
import aps.fithom.aweq.domain.State
import aps.foodfit.jyrbf.data.local.recipe.FoodDataBase
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.launch

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {

    private val foodDao = FoodDataBase.getInstance(application).recipeDao()
    private val ingredientDao = FoodDataBase.getInstance(application).ingredientDao()
    private val retrofit = RecipeService.getInstance()
    val recipeList = MutableLiveData<List<RecipeItemShort?>?>()
    val state = MutableLiveData<State>(State.START)

    fun addFoodItemToRacion(foodItem:RecipeItemShort, weight:Int){
        viewModelScope.launch {
            val recipeDB = foodItem.toRecipeDB(weight)
            foodDao.addRecipeItem(recipeDB)
        }
    }

    private fun getRecipeList(query: String) {
        viewModelScope.launch {
            recipeList.postValue(null)
            val recipes = mutableListOf<RecipeItemShort?>()
            val recipesTranslated = mutableListOf<RecipeItemShort>()
            val response = retrofit.getRecipeResponse(
                "public", query, APP_ID, APP_KEY
            )
            if (response.isSuccessful) {
                if (response.body()?.count == 0) {
                    state.postValue(State.ERROR)
                } else {
                    state.postValue(State.COMPLETE)
                    var startId = 0
                    response.body()?.hits?.map {
                        recipes.add(it.recipe?.getRecipeShort(startId))
                        startId++
                    }
                    val encoded = StringBuilder()
                    recipes.forEach {
                        if (it != null) {
                            encoded.append(it.getDataForTranslate())
                            encoded.append(ITEM_SPLITTER)
                        }
                    }
                    val options =
                        TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.ENGLISH)
                            .setTargetLanguage(TranslateLanguage.RUSSIAN).build()
                    val englishGermanTranslator = Translation.getClient(options)
                    val conditions = DownloadConditions.Builder().build()
                    englishGermanTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener {
                        val translator = Translation.getClient(options)
                        translator.translate(encoded.toString())
                            .addOnSuccessListener { translatedCollection ->
                                translatedCollection.split(ITEM_SPLITTER).forEach {
                                    if (it.isNotEmpty()) {
                                        val tmpTranslatedItem =
                                            RecipeTranslatedSubData.decodeFromString(it)
                                        val recipeEnglishList =
                                            recipes.filter { it?.id == tmpTranslatedItem?.id }
                                        if (recipeEnglishList.isNotEmpty()) {
                                            val recipeEnglish = recipeEnglishList[0]
                                            if (recipeEnglish != null && tmpTranslatedItem != null) {
                                                recipesTranslated.add(
                                                    recipeEnglish.copy(
                                                        label = tmpTranslatedItem.name,
//                                                        mealType = tmpTranslatedItem.diets,
//                                                        ingredients = tmpTranslatedItem.ingredients
                                                    )
                                                )
                                            }
                                        }

                                    }

                                }
                                recipeList.postValue(recipesTranslated)
                            }
                    }.addOnFailureListener { exception ->
                        state.postValue(State.ERROR)
                    }
                }
            }
        }

    }

    fun translateQuery(queryRussian: String) {
        state.value = State.LOADING
        val options =
            TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.RUSSIAN)
                .setTargetLanguage(TranslateLanguage.ENGLISH).build()
        val russianEnglishTranslator = Translation.getClient(options)
        val conditions = DownloadConditions.Builder().build()
        russianEnglishTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener {
            val translator = Translation.getClient(options)
            translator.translate(queryRussian)
                .addOnSuccessListener {
                    getRecipeList(it)
                }
        }
            .addOnFailureListener { exception ->
                state.postValue(State.ERROR)
            }
    }


}