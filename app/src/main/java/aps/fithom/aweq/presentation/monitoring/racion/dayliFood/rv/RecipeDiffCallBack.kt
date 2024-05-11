package aps.fithom.aweq.presentation.monitoring.racion.dayliFood.rv

import androidx.recyclerview.widget.DiffUtil
import aps.fithom.aweq.data.local.recipe.RecipeDB
import aps.fithom.aweq.data.remote.RecipeItemShort

class RecipeDiffCallBack:DiffUtil.ItemCallback<RecipeDB>() {

    override fun areItemsTheSame(oldItem: RecipeDB, newItem: RecipeDB): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipeDB, newItem: RecipeDB): Boolean {
        return oldItem == newItem
    }
}