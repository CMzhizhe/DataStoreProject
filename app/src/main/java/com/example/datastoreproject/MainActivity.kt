package com.example.datastoreproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gxx.datalibrary.DataStoreUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    val KEY_SHARE_AGE = "age"
    val KEY_SHARE_NAME = "name"
    val KEY_SHARE_OBJ = "obj"

    val KEY_SHARE_BRAND = "brand"
    val KEY_SHARE_NUMBERPLATE = "numberplate"

    companion object{
        val SHARE_KEY_USER = "share_user_info"
        val SHARE_KEY_CAR = "share_car_info"
    }


    //用户share
    val userInfoSharePre: SharedPreferences by lazy {
        this.getSharedPreferences(SHARE_KEY_USER, Context.MODE_PRIVATE)
    }
    //汽车share
    val carInfoSharePre: SharedPreferences by lazy {
        this.getSharedPreferences(SHARE_KEY_CAR, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testStringClass = String::class.java
        if (testStringClass.isAssignableFrom(String::class.java)) {
            Log.d(TAG, "你输入的是String类型")
        }

        //插入值
        this.findViewById<Button>(R.id.user_info_shared_insert_int_string).setOnClickListener {
            userInfoSharePre.edit().apply {
                putInt(KEY_SHARE_AGE, Random().nextInt())
                putString(KEY_SHARE_NAME, "用户名字-${Random().nextInt()}")
                commit()
            }
        }

        //插入值
        this.findViewById<Button>(R.id.car_info_shared_insert_int_string).setOnClickListener {
            carInfoSharePre.edit().apply {
                putString(KEY_SHARE_BRAND, "品牌型号-${Random().nextInt()}")
                putString(KEY_SHARE_NUMBERPLATE, "车牌号川-${Random().nextInt()}")
                commit()
            }
        }

        //迁移userInfo
        this.findViewById<Button>(R.id.user_sharefr_migrate_data_store).setOnClickListener {
            DataStoreUtil.getInstance().preferencesMigrationDataStore(SHARE_KEY_USER)
        }

        //迁移完userInfo，往里面插入一条int类型的值
        this.findViewById<Button>(R.id.user_sharefr_migrate_data_store_insert_int)
            .setOnClickListener {
                DataStoreUtil.getInstance().put(SHARE_KEY_USER, KEY_SHARE_AGE, Random().nextInt())
            }

        //迁移完userInfo，往里面插入一条String类型的值
        this.findViewById<Button>(R.id.user_sharefr_migrate_data_store_insert_string)
            .setOnClickListener {
                DataStoreUtil.getInstance()
                    .put(SHARE_KEY_USER, KEY_SHARE_NAME, "新插入的用户名字-${Random().nextInt()}")
            }

        //读取参数
        this.findViewById<Button>(R.id.user_read_data_store_value).setOnClickListener {
            val result = DataStoreUtil.getInstance().getString(SHARE_KEY_USER, KEY_SHARE_NAME, "")
            Log.d(TAG, "result=${result}")
        }

        //往dataStore里面的 share_user_info 插入对象，然后读取
        this.findViewById<Button>(R.id.user_insert_user_info_obj).setOnClickListener {
            lifecycleScope.launch {
                val testModel = TestModel(1, "王五-${Random().nextInt()}")
                //插入
                withContext(Dispatchers.IO) {
                    DataStoreUtil.getInstance().put(SHARE_KEY_USER, KEY_SHARE_OBJ, testModel)
                }
                //再次读取处理
                withContext(Dispatchers.IO) {
                    val saveModel = DataStoreUtil.getInstance()
                        .getAny<TestModel>(SHARE_KEY_USER, KEY_SHARE_OBJ, TestModel::class.java)
                    Log.d(TAG, "name=${saveModel?.name}")
                    Log.d(TAG, "sex=${saveModel?.sex}")
                }

                Log.d(TAG, "完成")


            }

        }
    }


}