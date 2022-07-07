package br.com.davidcastro.githubrepositories.helpers

import android.content.Context
import com.orhanobut.hawk.Hawk

object HawkUtil {

    fun init(context: Context) = Hawk.init(context).build()

    fun <T> add(key: String, obj: T) = Hawk.put(key, obj)

    fun <T> get(key: String): T = Hawk.get(key)

    fun contains(key: String) : Boolean = Hawk.contains(key)
}