package aps.fithom.aweq.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val next: Next?=null
)
