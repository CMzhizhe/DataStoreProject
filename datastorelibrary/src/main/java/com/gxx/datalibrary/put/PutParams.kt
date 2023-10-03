package com.gxx.datalibrary.put

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.google.gson.Gson
import com.gxx.datalibrary.inter.OnPutParamsListener
import kotlinx.coroutines.*

class PutParams: OnPutParamsListener {
    private val mGson = Gson()
    private val mDefaultIo =  CoroutineScope(Dispatchers.Default + SupervisorJob())
    /**
     * @author gaoxiaoxiong
     * 直接put 一个map进来
     */
    override fun <T> putAny(
        dataStore: DataStore<Preferences>,
        map: MutableMap<String, T>
    ) {
        runBlocking {
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