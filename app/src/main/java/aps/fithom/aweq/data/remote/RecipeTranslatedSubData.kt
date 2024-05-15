package aps.fithom.aweq.data.remote


data class RecipeTranslatedSubData(
    val id: Int,
    val name: String,
) {
    companion object {
        fun decodeFromString(encodedString: String): RecipeTranslatedSubData? {
            return try {
                val encodeStringAsList = encodedString.split(FIELDS_SPLITTER)
                val tmpId = encodeStringAsList[0].trim().toInt()
                val tmpName = encodeStringAsList[1]
                RecipeTranslatedSubData(tmpId, tmpName)
            } catch (e: NullPointerException) {
                null
            } catch (e: Exception) {
                e.message
                null
            }
        }
    }
}
