package aps.fithom.aweq.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

const val DATA_STORE_NAME = "fitnes_hom_data_strore"
val IS_INITIAL_SETUP_COMPLETE = booleanPreferencesKey("initial_setup_ds")
val TARGET_CALORIES_DS = intPreferencesKey("target_calories")
val TARGET_PROTEIN_DS = intPreferencesKey("target_protein")
val TARGET_FAT_DS = intPreferencesKey("target_fat")
val TARGET_CARB_DS = intPreferencesKey("target_carb")
val TARGET_WEIGHT_DS = doublePreferencesKey("target_weight")
val CURRENT_WEIGHT_DS = doublePreferencesKey("current_weight")

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

suspend fun Context.setInitialSetupComplete(){
    this.dataStore.edit {
        it[IS_INITIAL_SETUP_COMPLETE] = true
    }
}

suspend fun Context.setTargetCalories(calories:Int){
    this.dataStore.edit { preferences ->
        preferences[TARGET_CALORIES_DS] = calories
    }
}

suspend fun Context.setTargetProtein(protein:Int){
    this.dataStore.edit { preferences ->
        preferences[TARGET_PROTEIN_DS] = protein
    }
}

suspend fun Context.setTargetFat(fat:Int){
    this.dataStore.edit { preferences ->
        preferences[TARGET_FAT_DS] = fat
    }
}

suspend fun Context.setTargetCarb(carb:Int){
    this.dataStore.edit { preferences ->
        preferences[TARGET_CARB_DS] = carb
    }
}

suspend fun Context.setTargetWeight(weight:Double){
    this.dataStore.edit { preferences ->
        preferences[TARGET_WEIGHT_DS] = weight
    }
}

suspend fun Context.setCurrentWeight(weight:Double){
    this.dataStore.edit { preferences ->
        preferences[CURRENT_WEIGHT_DS] = weight
    }
}