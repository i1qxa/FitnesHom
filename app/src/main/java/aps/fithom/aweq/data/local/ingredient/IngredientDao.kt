package aps.fithom.aweq.data.local.ingredient

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Query("SELECT * FROM ingredientdb")
    fun getShoppingList():LiveData<List<IngredientDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListIngredientsToShoppingList(listIngredients:List<IngredientDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIngredientItemToShoppingList(ingredient:IngredientDB)

    @Delete()
    suspend fun removeFromShoppingList(ingredient:IngredientDB)

}