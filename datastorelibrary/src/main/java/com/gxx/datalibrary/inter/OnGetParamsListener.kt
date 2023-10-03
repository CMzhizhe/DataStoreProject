package com.gxx.datalibrary.inter

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface OnGetParamsListener {

    fun <T> get(dataStore: DataStore<Preferences>,dataStoreName: String, key: String,resultClass:Class<*>):T?

    fun getString(dataStore: DataStore<Preferences>,dataStoreName: String, key: String):String?

    fun getInt(dataStore: DataStore<Preferences>,dataStoreName: String, key: String):Int?

    fun getLong(dataStore: DataStore<Preferences>,dataStoreName: String, key: String):Long?

    fun getBoolean(dataStore: DataStore<Preferences>,dataStoreName: String, key: String):Boolean?

    fun getFloat(dataStore: DataStore<Preferences>,dataStoreName: String, key: String):Float?

    fun getDouble(dataStore: DataStore<Preferences>,dataStoreName: String, key: String):Double?

}