package aps.fithom.aweq.data.remote

import aps.fithom.aweq.data.remote.Hit
import aps.fithom.aweq.data.remote.Links
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponseBody(
    val from:Int?,
    val to:Int?,
    val count:Int?,
    val _links: Links?,
    val hits:List<Hit>?,
)
