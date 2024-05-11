package aps.fithom.aweq.presentation.monitoring.racion.dayliFood.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import aps.fithom.aweq.R
import aps.fithom.aweq.data.local.recipe.RecipeDB
import aps.fithom.aweq.data.remote.RecipeItemShort
import aps.fithom.aweq.domain.firstCharToUpperCase
import aps.fithom.aweq.presentation.monitoring.racion.recipeList.rv.FoodDiffCallBack
import coil.load
import coil.transform.RoundedCornersTransformation

class RecipeRVAdapter : ListAdapter<RecipeDB, RecipeViewHolder>(RecipeDiffCallBack()) {

    var onItemClickListener: ((RecipeDB) -> Unit)? = null
    var onShoppingCardClickListener: ((RecipeDB) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecipeViewHolder(
            layoutInflater.inflate(
                R.layout.recipe_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
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

        holder.itemView.setOnClickListener {
//            item.imgBitmap = holder.ivLogo.drawable.toBitmap()
            onItemClickListener?.invoke(item)
        }
        with(holder) {
            ivLogo.load(item.imgRemote) {
                transformations(RoundedCornersTransformation(20.0f))
            }
            tvName.text = item.name?.firstCharToUpperCase()
            tvKcal.text = context.getString(R.string.kcal_and_value, item.kcalTotal)
            tvProtein.text = context.getString(R.string.protein_and_value, item.proteinTotal)
            tvFat.text = context.getString(R.string.fat_and_value, item.fatTotal)
            tvCarb.text = context.getString(R.string.carb_and_value, item.carbTotal)
        }
    }
}

