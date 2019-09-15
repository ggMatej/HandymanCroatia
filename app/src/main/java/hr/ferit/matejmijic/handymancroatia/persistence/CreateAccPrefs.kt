package hr.ferit.matejmijic.handymancroatia.persistence

import android.preference.PreferenceManager
import hr.ferit.matejmijic.handymancroatia.HandymanApp

object CreateAccPrefs {

    const val KEY_NICKNAME = "KEY_NICKNAME"
    const val KEY_EMAIL = "KEY_EMAIL"
    const val KEY_PASSWORD = "KEY_PASSWORD"
    const val KEY_PHONENUM = "KEY_PHONENUM"
    const val KEY_ACCOUNT_TYPE = "KEY_ACCOUNT_TYPE"
    const val KEY_ACCOUNT_ADDRESS = "KEY_ACCOUNT_ADDRESS"


    private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(HandymanApp.getAppContext())

    fun store(key: String, value: String){
        sharedPrefs().edit().putString(key,value).apply()
    }

    fun getString(key: String, defaultValue: String): String? {
        return sharedPrefs().getString(key, defaultValue)
    }

    fun removeAll(){
        sharedPrefs().edit().clear().apply()
    }

}