package aps.fithom.aweq.domain

import android.icu.text.SimpleDateFormat
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import aps.fithom.aweq.R
import java.util.Calendar

const val BASE_DELAY:Long = 3000
const val APP_ID = "a7807cbf"
const val APP_KEY = "ed52861f7f0cc88e8ba9f5437a15082e"

enum class State{
    LOADING, COMPLETE, ERROR, START
}

enum class NutrientsState{
    ENERGY, PROTEIN, FAT, CARBS
}

fun getCurrentDateFormatted():String{
    val currentTime = Calendar.getInstance().timeInMillis
    val df = SimpleDateFormat("DD/MM/YYYY")
    return df.format(currentTime)
}

fun FragmentManager.launchNewFragment(fragment: Fragment){
    this.beginTransaction().apply {
        replace(R.id.monitoringConteiner, fragment)
        addToBackStack(null)
        commit()
    }
}

fun FragmentManager.launchNewQuestion(fragment: Fragment){
    this.beginTransaction().apply {
        replace(R.id.conteinerQuiestions, fragment)
        addToBackStack(null)
        commit()
    }
}

fun String.firstCharToUpperCase():String{
    val strTrim = this.trim()
    val firstChar = strTrim[0].uppercaseChar()
    return "$firstChar${strTrim.removeRange(0..0)}"
}

fun View.makeInvisible(){
    this.visibility = View.INVISIBLE
}

fun View.makeVisible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}