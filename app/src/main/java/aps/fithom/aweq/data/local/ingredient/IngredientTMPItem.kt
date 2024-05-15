package aps.fithom.aweq.data.local.ingredient

import aps.fithom.aweq.data.remote.IngredientItem

data class IngredientTMPItem(
    val id: Int,
    val ingredientName: String,
    val measure: String,
) {
    fun getIngredientDB(ingredientItem: IngredientItem): IngredientDB {
        return IngredientDB(
            0,
            ingredientName,
            ingredientItem.quantity ?: 1.0,
            measure,
            ingredientItem.weight ?: 1.0,
            ingredientItem.image ?: "",
            true
        )
    }
}
