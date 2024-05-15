package aps.fithom.aweq.presentation.monitoring.shoping_list.rv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import aps.fithom.aweq.R

class IngredientViewHolder(itemView:View):ViewHolder(itemView) {
    val ivLogo = itemView.findViewById<ImageView>(R.id.ivLogo)
    val tvtvIngredient = itemView.findViewById<TextView>(R.id.tvIngredientName)
    val btnComplete = itemView.findViewById<ImageView>(R.id.btnComplete)
}