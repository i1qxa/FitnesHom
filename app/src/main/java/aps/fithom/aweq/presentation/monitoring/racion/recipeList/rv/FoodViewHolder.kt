package aps.fithom.aweq.presentation.monitoring.racion.recipeList.rv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import aps.fithom.aweq.R

class FoodViewHolder(itemView:View):ViewHolder(itemView) {
    val ivLogo = itemView.findViewById<ImageView>(R.id.ivRecipeLogo)
    val tvName = itemView.findViewById<TextView>(R.id.tvRecipeName)
    val tvKcal = itemView.findViewById<TextView>(R.id.tvKcal)
    val tvProtein = itemView.findViewById<TextView>(R.id.tvProtein)
    val tvFat = itemView.findViewById<TextView>(R.id.tvFat)
    val tvCarb = itemView.findViewById<TextView>(R.id.tvCarb)
    val btnShoppingCart = itemView.findViewById<ImageView>(R.id.btnAddToShoppingCart)
    val btnAddToRacion = itemView.findViewById<TextView>(R.id.btnAddToRacion)
}