package aps.fithom.aweq.data.local.ingredient

import androidx.room.Entity
import androidx.room.PrimaryKey
import aps.fithom.aweq.domain.firstCharToUpperCase

@Entity
data class IngredientDB(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val quantity:Double,
    val measure:String,
    val weight:Double?,
    val img:String?,
    val isActual:Boolean,
){
    fun getIngredientAsString() = "$name $quantity$measure".firstCharToUpperCase()
}
