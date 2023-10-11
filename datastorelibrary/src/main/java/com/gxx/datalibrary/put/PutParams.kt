package com.gxx.datalibrary.put

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.google.gson.Gson
import com.gxx.datalibrary.inter.OnPutParamsListener
import com.gxx.datalibrary.util.MemoryCacheUtil
import kotlinx.coroutines.*

class PutParams: OnPutParamsListener {
    private val mGson = Gson()
    private val mDefaultIo =  CoroutineScope(Dispatchers.Default + SupervisorJob())
    override suspend fun <T> putAny(
        dataStore: DataStore<Preferences>,
        map: MutableMap<String, T>
    ) {
        val newMap = mutableMapOf<String,String>()
        for (mutableEntry in map.iterator()) {
            val key = mutableEntry.key
            val cacheValue = MemoryCacheUtil.get(key)
            val json = mGson.toJson(mutableEntry.value)
            if (cacheValue == json){
                continue
            }
            newMap[key] = json
            MemoryCacheUtil.put(key,json)
        }

        if (newMap.isNotEmpty()){
            dataStore.edit{
                for (mutableEntry in map.iterator()) {
                    val key = mutableEntry.key
                    it[stringPreferencesKey(key)] = mGson.toJson(mutableEntry.value)
                }
            }
        }
    }

    override  fun <T> applyPutAny(
        dataStore: DataStore<Preferences>,
        map: MutableMap<String, T>
    ) {
        mDefaultIo.launch {
            dataStore.edit{
                for (mutableEntry in map.iterator()) {
                    val key = mutableEntry.key
                    it[stringPreferencesKey(key)] = mGson.toJson(mutableEntry.value)
                }
            }
        }
    }

}