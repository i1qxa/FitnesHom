package aps.fithom.aweq.presentation.monitoring.shoping_list.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import aps.fithom.aweq.R
import aps.fithom.aweq.data.local.ingredient.IngredientDB
import coil.load
import coil.transform.RoundedCornersTransformation

class IngredientRVAdapter :
    ListAdapter<IngredientDB, IngredientViewHolder>(IngredientDiffCallBack()) {

    var onCompleteClickListener: ((IngredientDB) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IngredientViewHolder(
            layoutInflater.inflate(
                R.layout.ingredient_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = getItem(position)
        holder.btnComplete.setOnClickListener {
            onCompleteClickListener?.invoke(item)
        }
        with(holder) {
            ivLogo.load(item.img) {
                transformations(RoundedCornersTransformation(20.0f))
            }
            tvtvIngredient.text = item.getIngredientAsString()
        }
    }
}

