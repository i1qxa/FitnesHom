package aps.fithom.aweq.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Self(
    val title:String?,
    val href:String?,
)
