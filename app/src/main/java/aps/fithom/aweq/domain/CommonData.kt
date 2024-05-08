package aps.fithom.aweq.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

const val BASE_DELAY:Long = 3000

enum class State{
    LOADING, COMPLETE, ERROR
}

suspend fun Context.saveBitmapInFileDir(fileName: String, bitmap: Bitmap) = withContext(Dispatchers.IO) {
    val file = File(filesDir, "${fileName}.webp")
    file.outputStream().use {
        bitmap.compress(Bitmap.CompressFormat.WEBP, 75, it)
    }
}

fun Context.getBitmapByNameFromFildir(fileName: String): Bitmap?{
    if(fileName.isNotEmpty()){
        val img = File(this@getBitmapByNameFromFildir.filesDir, fileName)
        img.inputStream().use {
            return BitmapFactory.decodeStream(it)
        }
    }else return null
}

fun Context.getBitmapFromAssets(fileName: String): Bitmap?{
    return if(fileName.isNotEmpty()){
        try {
            val img = this@getBitmapFromAssets.assets.open(fileName)
            BitmapFactory.decodeStream(img)
        }catch (e:Exception){
            null
        }

    }else null
}

fun FragmentManager.launchNewFragment(fragment: Fragment){
    TODO("Сделать MonitoringActivity и заменить котейнер")
    this.beginTransaction().apply {
//        replace(R.id.foodConteiner, fragment)
        addToBackStack(null)
        commit()
    }
}

fun String.firstCharToUpperCase():String{
    val strTrim = this.trim()
    val firstChar = strTrim[0].uppercaseChar()
    return "$firstChar${strTrim.removeRange(0..0)}"
}