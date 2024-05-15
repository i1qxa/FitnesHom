package aps.fithom.aweq.presentation.monitoring.shoping_list.rv

import androidx.recyclerview.widget.DiffUtil
import aps.fithom.aweq.data.local.ingredient.IngredientDB
import aps.fithom.aweq.data.remote.RecipeItemShort

class IngredientDiffCallBack:DiffUtil.ItemCallback<IngredientDB>() {

    override fun areItemsTheSame(oldItem: IngredientDB, newItem: IngredientDB): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IngredientDB, newItem: IngredientDB): Boolean {
        return oldItem == newItem
    }
}