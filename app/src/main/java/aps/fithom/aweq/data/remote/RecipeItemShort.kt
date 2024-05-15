package aps.fithom.aweq.data.remote

import aps.fithom.aweq.data.local.ingredient.IngredientDB
import aps.fithom.aweq.data.local.ingredient.IngredientDao
import aps.fithom.aweq.data.local.ingredient.IngredientTMPItem
import aps.fithom.aweq.data.local.recipe.RecipeDB
import aps.fithom.aweq.domain.NewIngridients
import aps.fithom.aweq.domain.State
import aps.fithom.aweq.domain.getCurrentDateFormatted
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

const val FIELDS_SPLITTER = "|"
const val VALUE_SPLITTER = "~"
const val ITEM_SPLITTER = "@"

@Serializable
data class RecipeItemShort(
    val id: Int,
    val uri: String,
    val label: String?,
    val imgRegular: String?,
    val imgSmall: String?,
    val calories: Int,
    val carbs: Int,
    val fat: Int,
    val protein: Int,
    val weight: Int,
    val ingredients: List<String>?,
    val dietLabels: List<String>?,
    val mealType: List<String>?,
    val totalTime: Double?,
    val ingredientItems: List<IngredientItem>?
) {
    fun toRecipeDB(weightPortion: Int): RecipeDB {
        return RecipeDB(
            0,
            label ?: "Название",
            imgRegular ?: "",
            weightPortion,
            calories.toDouble() * weightPortion / weight,
            protein.toDouble() * weightPortion / weight,
            fat.toDouble() * weightPortion / weight,
            carbs.toDouble() * weightPortion / weight,
            totalTime?.toInt() ?: 0,
            getCurrentDateFormatted(),
            uri
        )
    }

    var inShoppingCard = false

//    @Contextual
//    var imgBitmap: Bitmap? = null
//
//    var savedImgName = ""

    private fun getIngredientsAsString(): String {
        return try {
            Json.encodeToString(ingredients)
        } catch (e: Exception) {
            ""
        }
    }


    fun getDataForTranslate(): String {
        val answerSB = StringBuilder()
        answerSB.append(id)
        answerSB.append(FIELDS_SPLITTER)
        answerSB.append(label)
        return answerSB.toString()
    }

    fun translateIngredients() {
        val listTranslatedItems = mutableListOf<IngredientTMPItem>()
        val listDB = mutableListOf<IngredientDB>()
        val ingredientsAsString = StringBuilder()
        ingredientItems?.forEach { item ->
            ingredientsAsString.append(item.text)
            ingredientsAsString.append(VALUE_SPLITTER)
            ingredientsAsString.append(item.measure)
            ingredientsAsString.append(ITEM_SPLITTER)
        }
        val options =
            TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.RUSSIAN).build()
        val englishRussianTranslator = Translation.getClient(options)
        val conditions = DownloadConditions.Builder().build()
        englishRussianTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener {
            val translator = Translation.getClient(options)
            translator.translate(ingredientsAsString.toString())
                .addOnSuccessListener { translatedCollection ->
                    var startIndex = 0
                    translatedCollection.split(ITEM_SPLITTER).forEach {
                        if (it.isNotEmpty()) {
                            val splittedIngredient = it.split(VALUE_SPLITTER)
                            if (splittedIngredient.isNotEmpty()) {
                                val translatedIngredient = IngredientTMPItem(
                                    startIndex,
                                    splittedIngredient[0] ?: "",
                                    splittedIngredient[1] ?: ""
                                )
                                listTranslatedItems.add(translatedIngredient)
                                startIndex++
                            }
                        }
                    }

                    var index = 0
                    ingredientItems?.forEach { ingredientItem ->
                        listDB.add(listTranslatedItems[index].getIngredientDB(ingredientItem))
                        index++
                    }
                    NewIngridients.updateIngredients(listDB)
                }
        }.addOnFailureListener { exception ->

        }
    }


//    val proteinPercent: Int
//        get() = ((protein.toDouble() / totalNuntrientsMass) * 100).toInt()
//
//    val fatPercent: Int
//        get() = ((fat.toDouble() / totalNuntrientsMass) * 100).toInt()
//
//    val carbPercent: Int
//        get() = ((carbs.toDouble() / totalNuntrientsMass) * 100).toInt()
//    private val totalNuntrientsMass: Int
//        get() = (protein + fat + carbs)

}
