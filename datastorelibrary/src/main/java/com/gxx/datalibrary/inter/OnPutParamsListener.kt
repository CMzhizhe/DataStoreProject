package com.gxx.datalibrary.inter

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface OnPutParamsListener {

    suspend fun <T> putAny(dataStore:DataStore<Preferences>,map: MutableMap<String,T>)

    fun <T> applyPutAny(dataStore:DataStore<Preferences>,map: MutableMap<String,T>)
}