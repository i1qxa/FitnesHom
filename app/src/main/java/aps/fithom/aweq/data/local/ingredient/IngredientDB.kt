package aps.fithom.aweq.data.local.ingredient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IngredientDB(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val quantity:Double,
    val measure:String,
    val food:String,
    val weight:Double?,
    val img:String?,
    val imgLocal:String?,
    val isActual:Boolean,
)
