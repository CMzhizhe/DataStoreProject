package com.gxx.datalibrary

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import com.gxx.datalibrary.get.GetParams
import com.gxx.datalibrary.inter.OnDataStoreSaveListener
import com.gxx.datalibrary.inter.OnSharePreferencesMigrationDataFinish
import com.gxx.datalibrary.put.PutParams
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

class DataStoreUtil : OnDataStoreSaveListener {
    private val TAG = "DataStoreUtil"

    companion object {
        @Volatile
        private var INSTANCE: DataStoreUtil? = null
        lateinit var mContext: Context
        private val lock = Any()

        fun init(context: Context) {
            this.mContext = context
        }

        fun getInstance(): DataStoreUtil {
            return INSTANCE ?: synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = DataStoreUtil()
                }
                INSTANCE!!
            }
        }
    }

    private val mIoScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val mDataStoreMap = mutableMapOf<String, DataStore<Preferences>>()
    private val mPutParams = PutParams()
    private val mGetParams = GetParams()

    /**
     * @author gaoxiaoxiong
     * 预加载dataStore，方便快速读取
     */
   suspend fun preloadDataStore(dataStoreNames: MutableList<String>){
        for (dataStoreName in dataStoreNames) {
             val dataStore = createDataStore(dataStoreName)
            dataStore.data.first()
        }

    }

    /**
     * @author gaoxiaoxiong
     * SharedPreferences 数据迁移
     * @param sharedPreferName SharedPreferences名称，不需要带 .xml后缀
     */
    override fun preferencesMigrationDataStore(
        sharedPreferName: String,
        listener: OnSharePreferencesMigrationDataFinish?
    ) {
        synchronized(DataStoreUtil::class.java) {
            if (mDataStoreMap[sharedPreferName] != null) {
                return
            }
        }

        val dataStore = PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler<Preferences>(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(mContext, sharedPreferName)),
            scope = mIoScope) {
            mContext.preferencesDataStoreFile(sharedPreferName)
        }

        runBlocking {
            dataStore.updateData {
                it.toPreferences()
            }
        }

        mDataStoreMap[sharedPreferName] = dataStore

        listener?.onFinish()
    }

    /**
     * @author gaoxiaoxiong
     * @param dataStoreName store名称
     */
    override fun getDataStore(dataStoreName: String): DataStore<Preferences>? {
        return mDataStoreMap[dataStoreName]
    }

    /**
     * @author gaoxiaoxiong
     * @param dataStoreName 名称
     * @param key 存储参数名称
     * @param value 存储参数值
     * 同步方式插入
     */
    override suspend fun <T> put(dataStoreName: String, key: String, value: T) {
        val map = mutableMapOf<String, T>()
        map[key] = value
        mPutParams.putAny(createDataStore(dataStoreName), map)
    }

    override suspend fun putInt(dataStoreName: String, key: String, value: String) {
        this.put(dataStoreName, key, value)
    }

    override suspend fun putString(dataStoreName: String, key: String, value: String) {
        this.put(dataStoreName, key, value)
    }

    override suspend fun putDouble(dataStoreName: String, key: String, value: String) {
        this.put(dataStoreName, key, value)
    }

    override suspend fun putBoolean(dataStoreName: String, key: String, value: String) {
        this.put(dataStoreName, key, value)
    }

    override suspend fun putFloat(dataStoreName: String, key: String, value: String) {
        this.put(dataStoreName, key, value)
    }

    override suspend fun putLong(dataStoreName: String, key: String, value: String) {
        this.put(dataStoreName, key, value)
    }

    override suspend fun <T> putAny(dataStoreName: String, map: MutableMap<String, T>) {
        mPutParams.putAny(createDataStore(dataStoreName), map)
    }



    /**
     * @author gaoxiaoxiong
     * @param dataStoreName 名称
     * @param key 存储参数名称
     * @param value 存储参数值
     * 异步方式插入
     */
    override  fun <T> applyPut(dataStoreName: String, key: String, value: T) {
        val map = mutableMapOf<String, T>()
        map[key] = value
        mPutParams.applyPutAny(createDataStore(dataStoreName),map)
    }

    override  fun applyPutInt(dataStoreName: String, key: String, value: String) {
       this.applyPut(dataStoreName, key, value)
    }

    override  fun applyPutString(dataStoreName: String, key: String, value: String) {
        this.applyPut(dataStoreName, key, value)
    }

    override  fun applyPutDouble(dataStoreName: String, key: String, value: String) {
        this.applyPut(dataStoreName, key, value)
    }

    override  fun applyPutBoolean(dataStoreName: String, key: String, value: String) {
        this.applyPut(dataStoreName, key, value)
    }

    override  fun applyPutFloat(dataStoreName: String, key: String, value: String) {
        this.applyPut(dataStoreName, key, value)
    }

    override  fun applyPutLong(dataStoreName: String, key: String, value: String) {
        this.applyPut(dataStoreName, key, value)
    }

    override  fun <T> applyPutAny(dataStoreName: String, map: MutableMap<String, T>) {
        mPutParams.applyPutAny(createDataStore(dataStoreName),map)
    }

    override fun getString(dataStoreName: String, key: String, defaultValue: String):String {
        return this.getAny<String>(dataStoreName, key,String::class.java)?:defaultValue
    }

    override fun getInt(dataStoreName: String, key: String, defaultValue: Int): Int {
        return this.getAny<Int>(dataStoreName, key,Int::class.java)?:defaultValue
    }

    override fun getDouble(dataStoreName: String, key: String, defaultValue: Double): Double {
        return this.getAny<Double>(dataStoreName, key,Double::class.java)?:defaultValue
    }

    override fun getBoolean(dataStoreName: String, key: String, defaultValue: Boolean): Boolean {
        return this.getAny<Boolean>(dataStoreName, key,Boolean::class.java)?:defaultValue
    }

    override fun getFloat(dataStoreName: String, key: String, defaultValue: Float): Float {
        return this.getAny<Float>(dataStoreName, key,Float::class.java)?:defaultValue
    }

    override fun getLong(dataStoreName: String, key: String, defaultValue: Long): Long {
        return this.getAny<Long>(dataStoreName, key,Long::class.java)?:defaultValue
    }

    override fun <T> getAny(dataStoreName: String, key: String, resultClass: Class<*>): T? {
        return mGetParams.get(createDataStore(dataStoreName),dataStoreName,key,resultClass)
    }

    /**
     * @author gaoxiaoxiong
     * 构建DataStore
     */
    private fun createDataStore(dataStoreName: String): DataStore<Preferences> {
        if (mDataStoreMap[dataStoreName] == null) {
            synchronized(DataStoreUtil::class.java) {
                if (mDataStoreMap[dataStoreName] == null) {
                    mDataStoreMap[dataStoreName] = PreferenceDataStoreFactory.create(
                        corruptionHandler = ReplaceFileCorruptionHandler<Preferences>(
                            produceNewData = { emptyPreferences() }
                        ),
                        migrations = emptyList(),
                        scope = mIoScope) {
                        mContext.preferencesDataStoreFile(dataStoreName)
                    }
                }
            }
        }
        return mDataStoreMap[dataStoreName]!!
    }
}