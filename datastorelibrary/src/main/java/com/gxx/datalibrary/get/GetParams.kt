package com.gxx.datalibrary.get

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.gxx.datalibrary.inter.OnGetParamsListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class GetParams : OnGetParamsListener {
    private val mGson = Gson()
    override fun <T> get(
        dataStore: DataStore<Preferences>,
        dataStoreName: String,
        key: String,
        resultClass: Class<*>
    ): T? = runBlocking {
        val tResult:T? = withContext(coroutineContext){
             val storeValue = dataStore.data.map {
                 it[stringPreferencesKey(key)]
            }.first()
            mGson.fromJson<T?>(storeValue,resultClass)
        }
         tResult
    }


    override fun getString(
        dataStore: DataStore<Preferences>,
        dataStoreName: String,
        key: String
    ): String? {
        return get(dataStore,dataStoreName,key,String::class.java)
    }

    override fun getInt(
        dataStore: DataStore<Preferences>,
        dataStoreName: String,
        key: String
    ): Int? {
        return get(dataStore,dataStoreName,key,Int::class.java)
    }

    override fun getLong(
        dataStore: DataStore<Preferences>,
        dataStoreName: String,
        key: String
    ): Long? {
        return get(dataStore,dataStoreName,key,Long::class.java)
    }

    override fun getBoolean(
        dataStore: DataStore<Preferences>,
        dataStoreName: String,
        key: String
    ): Boolean? {
        return get(dataStore,dataStoreName,key,Boolean::class.java)
    }

    override fun getFloat(
        dataStore: DataStore<Preferences>,
        dataStoreName: String,
        key: String
    ): Float? {
        return get(dataStore,dataStoreName,key,Float::class.java)
    }

    override fun getDouble(
        dataStore: DataStore<Preferences>,
        dataStoreName: String,
        key: String
    ): Double? {
        return get(dataStore,dataStoreName,key,Double::class.java)
    }



}