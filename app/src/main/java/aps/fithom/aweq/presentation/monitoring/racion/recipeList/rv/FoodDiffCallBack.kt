package aps.fithom.aweq.presentation.monitoring.racion.recipeList.rv

import androidx.recyclerview.widget.DiffUtil
import aps.fithom.aweq.data.remote.RecipeItemShort

class FoodDiffCallBack:DiffUtil.ItemCallback<RecipeItemShort>() {

    override fun areItemsTheSame(oldItem: RecipeItemShort, newItem: RecipeItemShort): Boolean {
        return oldItem.label == newItem.label
    }

    override fun areContentsTheSame(oldItem: RecipeItemShort, newItem: RecipeItemShort): Boolean {
        return oldItem == newItem
    }
}