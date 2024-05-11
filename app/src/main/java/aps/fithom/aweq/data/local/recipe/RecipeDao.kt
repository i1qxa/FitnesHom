package aps.fithom.aweq.data.local.recipe

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import aps.fithom.aweq.domain.Nutrients

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipeItem(recipeItem:RecipeDB)

    @Query("SELECT * FROM recipedb WHERE date =:currentDate")
    fun getCurrentRacion(currentDate:String):LiveData<List<RecipeDB>>

    @Query("SELECT sum(kcalPerGram*weightInGrams) as energy, sum(proteinPerGram*weightInGrams) as protein," +
            "sum(carbPerGram*weightInGrams) as carbs, sum(fatPerGram*weightInGrams) as fat" +
            " FROM RecipeDB WHERE date =:currentDate ")
    fun fetchNutrients(currentDate: String):LiveData<Nutrients>



}