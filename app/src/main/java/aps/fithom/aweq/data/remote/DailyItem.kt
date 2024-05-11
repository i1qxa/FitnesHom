package aps.fithom.aweq.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class DailyItem(
    val label:String?,
    val quantity:Float?,
    val unit:String?,
)
