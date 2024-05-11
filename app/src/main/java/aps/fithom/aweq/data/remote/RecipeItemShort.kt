package aps.fithom.aweq.data.remote

import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import aps.fithom.aweq.data.local.recipe.RecipeDB
import aps.fithom.aweq.domain.getCurrentDateFormatted
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Calendar

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
    val totalTime: Double?
) {
    fun toRecipeDB(weight: Int): RecipeDB {
        return RecipeDB(
            0,
            label ?: "Название",
            imgRegular ?: "",
            weight,
            calories.toDouble() / 100,
            protein.toDouble() / 100,
            fat.toDouble() / 100,
            carbs.toDouble() / 100,
            totalTime?.toInt() ?: 0,
            getCurrentDateFormatted(),
            uri
        )
    }

    var inShoppingCard = false

    @Contextual
    var imgBitmap: Bitmap? = null

    var savedImgName = ""

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


    val proteinPercent: Int
        get() = ((protein.toDouble() / totalNuntrientsMass) * 100).toInt()

    val fatPercent: Int
        get() = ((fat.toDouble() / totalNuntrientsMass) * 100).toInt()

    val carbPercent: Int
        get() = ((carbs.toDouble() / totalNuntrientsMass) * 100).toInt()
    private val totalNuntrientsMass: Int
        get() = (protein + fat + carbs)

}
