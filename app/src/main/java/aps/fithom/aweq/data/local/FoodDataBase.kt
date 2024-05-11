package aps.foodfit.jyrbf.data.local.recipe

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import aps.fithom.aweq.data.local.ingredient.IngredientDB
import aps.fithom.aweq.data.local.ingredient.IngredientDao
import aps.fithom.aweq.data.local.recipe.RecipeDB
import aps.fithom.aweq.data.local.recipe.RecipeDao
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [
        RecipeDB::class,
        IngredientDB::class,
    ],
    exportSchema = false,
    version = 1
)
abstract class FoodDataBase:RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao():IngredientDao
    companion object {
        private var INSTANCE: FoodDataBase? = null
        private val LOCK = Any()
        private const val DB_NAME = "food_db"

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(application: Application): FoodDataBase {
            INSTANCE?.let {
                return it
            }
            kotlinx.coroutines.internal.synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    FoodDataBase::class.java,
                    DB_NAME
                )
//                    .createFromAsset("food_fit_db.db")
                    .build()
                INSTANCE = db
                return db
            }
        }
    }


}