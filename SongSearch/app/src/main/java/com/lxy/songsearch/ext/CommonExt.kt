package com.lxy.songsearch.ext

import android.util.Log
import com.google.gson.Gson

val gson = Gson()
inline fun <reified T> T.extToJsonString(): String = gson.toJson(this)


inline fun <reified T> T.extLogPrint(
    msg: String,
    level: Int = Log.ERROR,
    tag: String? = null,
) {
    when (level) {
        Log.VERBOSE -> Log.v(tag ?: T::class.java.simpleName, msg)
        Log.ERROR -> Log.e(tag ?: T::class.java.simpleName, msg)
        Log.INFO -> Log.i(tag ?: T::class.java.simpleName, msg)
        Log.WARN -> Log.w(tag ?: T::class.java.simpleName, msg)
        Log.DEBUG -> Log.d(tag ?: T::class.java.simpleName, msg)
    }
}
