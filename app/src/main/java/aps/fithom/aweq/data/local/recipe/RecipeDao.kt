package aps.fithom.aweq.data.local.recipe

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import aps.fithom.aweq.domain.Nutrients
import aps.fithom.aweq.presentation.monitoring.progres.ProgressData

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipeItem(recipeItem:RecipeDB)

    @Query("SELECT * FROM recipedb WHERE date =:currentDate")
    fun getCurrentRacion(currentDate:String):LiveData<List<RecipeDB>>

    @Query("SELECT sum(kcalPerGram) as energy, sum(proteinPerGram) as protein," +
            "sum(carbPerGram) as carbs, sum(fatPerGram) as fat" +
            " FROM RecipeDB WHERE date =:currentDate ")
    fun fetchNutrients(currentDate: String):LiveData<Nutrients>

    @Query("SELECT date, sum(kcalPerGram) as value FROM recipedb GROUP BY date")
    fun getEnergyData():LiveData<List<ProgressData>>

    @Query("SELECT date, sum(proteinPerGram) as value FROM recipedb GROUP BY date")
    fun getProteinData():LiveData<List<ProgressData>>

    @Query("SELECT date, sum(fatPerGram) as value FROM recipedb GROUP BY date")
    fun getFatData():LiveData<List<ProgressData>>

    @Query("SELECT date, sum(carbPerGram) as value FROM recipedb GROUP BY date")
    fun getCarbsData():LiveData<List<ProgressData>>



}