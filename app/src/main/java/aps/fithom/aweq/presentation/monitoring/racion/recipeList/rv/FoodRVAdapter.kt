package aps.fithom.aweq.presentation.monitoring.racion.recipeList.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ListAdapter
import aps.fithom.aweq.R
import aps.fithom.aweq.data.remote.RecipeItemShort
import aps.fithom.aweq.domain.firstCharToUpperCase
import coil.load
import coil.transform.RoundedCornersTransformation

class FoodRVAdapter : ListAdapter<RecipeItemShort, FoodViewHolder>(FoodDiffCallBack()) {

    var onItemClickListener: ((RecipeItemShort) -> Unit)? = null
    var onShoppingCardClickListener: ((RecipeItemShort) -> Unit)? = null
    var onBtnAddClickListener: ((RecipeItemShort) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FoodViewHolder(
            layoutInflater.inflate(
                R.layout.food_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = getItem(position)
        val context = holder.itemView.context
        if (item.inShoppingCard) {
            holder.btnShoppingCart.setImageResource(R.drawable.in_shopping_cart)
        }
        holder.btnShoppingCart.setOnClickListener {
            if (!item.inShoppingCard) {
                onShoppingCardClickListener?.invoke(item)
            }
        }
        holder.btnAddToRacion.setOnClickListener {
            onBtnAddClickListener?.invoke(item)
        }
        holder.itemView.setOnClickListener {
//            item.imgBitmap = holder.ivLogo.drawable.toBitmap()
            onItemClickListener?.invoke(item)
        }
        with(holder) {
            ivLogo.load(item.imgSmall) {
                transformations(RoundedCornersTransformation(20.0f))
            }
            tvName.text = item.label?.firstCharToUpperCase()
            tvKcal.text = context.getString(R.string.kcal_and_value, item.calories)
            tvProtein.text = context.getString(R.string.protein_and_value, item.protein)
            tvFat.text = context.getString(R.string.fat_and_value, item.fat)
            tvCarb.text = context.getString(R.string.carb_and_value, item.carbs)
        }
    }
}

