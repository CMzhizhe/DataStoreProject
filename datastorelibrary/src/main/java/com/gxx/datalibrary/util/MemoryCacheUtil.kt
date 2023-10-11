package com.gxx.datalibrary.util

import android.util.LruCache

/**
  * 内存缓存
  */
object MemoryCacheUtil {
    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private val cacheSize = maxMemory / 8
    private val mMemoryCache = LruCache<String, String>(cacheSize)

    /**
     * put参数
     */
    fun put(key: String, value: String) {
        mMemoryCache.put(key, value)
    }

    /**
     * get参数
     */
    fun get(key: String): String? {
        return mMemoryCache[key]
    }

    /**
     * 移除
     */
    fun remove(key: String) {
        mMemoryCache.remove(key)
    }

}