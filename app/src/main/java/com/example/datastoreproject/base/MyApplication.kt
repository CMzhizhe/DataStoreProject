package com.example.datastoreproject.base

import android.app.Application
import com.example.datastoreproject.activity.MainActivity
import com.gxx.datalibrary.DataStoreUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataStoreUtil.init(this)
        GlobalScope.launch(Dispatchers.IO) {
            val list = mutableListOf<String>()
            list.add(MainActivity.SHARE_KEY_CAR)
            list.add(MainActivity.SHARE_KEY_USER)
            //目的是为了提前价值dataStore里面的值
            DataStoreUtil.getInstance().preloadDataStore(list)
        }
    }
}