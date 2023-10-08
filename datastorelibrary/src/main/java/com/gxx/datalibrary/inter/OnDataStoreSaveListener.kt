package com.gxx.datalibrary.inter

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface OnDataStoreSaveListener {
    /**
     * @author gaoxiaoxiong
     * SharedPreferences 数据迁移
     * @param sharedPreferName SharedPreferences名称，不需要带 .xml后缀
     */
    fun preferencesMigrationDataStore(
        sharedPreferName: String,
        listener: OnSharePreferencesMigrationDataFinish? = null
    )

    /**
     * @author gaoxiaoxiong
     * @param dataStoreName store名称
     */
    fun getDataStore(dataStoreName: String): DataStore<Preferences>?

    /**
     * @author gaoxiaoxiong
     * 同步
     */
    suspend fun <T> put(dataStoreName: String, key: String, value: T)

    suspend  fun putInt(dataStoreName: String, key: String, value: String)

    suspend fun putString(dataStoreName: String, key: String, value: String)

    suspend fun putDouble(dataStoreName: String, key: String, value: String)

    suspend fun putBoolean(dataStoreName: String, key: String, value: String)

    suspend fun putFloat(dataStoreName: String, key: String, value: String)

    suspend fun putLong(dataStoreName: String, key: String, value: String)

    suspend fun <T> putAny(dataStoreName: String, map: MutableMap<String, T>)


    /**
     * @author gaoxiaoxiong
     * 异步
     */
     fun <T> applyPut(dataStoreName: String, key: String, value: T)

     fun applyPutInt(dataStoreName: String, key: String, value: String)

     fun applyPutString(dataStoreName: String, key: String, value: String)

     fun applyPutDouble(dataStoreName: String, key: String, value: String)

     fun applyPutBoolean(dataStoreName: String, key: String, value: String)

     fun applyPutFloat(dataStoreName: String, key: String, value: String)

     fun applyPutLong(dataStoreName: String, key: String, value: String)

     fun <T> applyPutAny(dataStoreName: String, map: MutableMap<String, T>)



    fun getString(dataStoreName: String, key: String, defaultValue: String?):String?

    fun getInt(dataStoreName: String, key: String, defaultValue: Int):Int

    fun getDouble(dataStoreName: String, key: String, defaultValue: Double):Double

    fun getBoolean(dataStoreName: String, key: String, defaultValue: Boolean):Boolean

    fun getFloat(dataStoreName: String, key: String, defaultValue: Float):Float

    fun getLong(dataStoreName: String, key: String, defaultValue: Long):Long

    fun <T> getAny(dataStoreName: String, key: String, resultClass: Class<*>):T?
}