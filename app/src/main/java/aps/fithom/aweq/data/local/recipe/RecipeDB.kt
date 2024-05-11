package aps.fithom.aweq.data.local.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val imgRemote: String,
    val weightInGrams: Int,
    val kcalPerGram: Double,
    val proteinPerGram: Double,
    val fatPerGram: Double,
    val carbPerGram: Double,
    val totalTimeInMinutes: Int,
    val date:String,
    val uri:String,
) {

    var inShoppingCard = false
    val kcalTotal: Double
        get() = kcalPerGram * weightInGrams
    val proteinTotal: Double
        get() = proteinPerGram * weightInGrams
    val fatTotal: Double
        get() = fatPerGram * weightInGrams
    val carbTotal: Double
        get() = carbPerGram * weightInGrams

    val proteinPercent:Int
        get() = ((proteinTotal/totalNuntrientsMass)*100).toInt()

    val fatPercent:Int
        get() = ((fatTotal/totalNuntrientsMass)*100).toInt()

    val carbPercent:Int
        get() = ((carbTotal/totalNuntrientsMass)*100).toInt()
    private val totalNuntrientsMass:Int
        get() = (proteinTotal + fatTotal + carbTotal).toInt()

}
