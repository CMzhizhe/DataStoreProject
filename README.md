#### 完成功能
1、将sharedpreferences迁移到dataStore
<br/>
2、含有提交，异步提交
<br/>
3、支持动态创建dataStore
<br/>
4、提供提前加载dataStore
<br/>
5、支持提交对象、数组、字符串等

#### 博客
[关于dataStore原理](https://www.jianshu.com/p/d92bc69177d7?v=1696403044219)


#### 未做的点，与未来想要做的
多进程存储

#### 依赖配置
```
  maven { url "https://jitpack.io" }
```
```
implementation 'com.github.CMzhizhe:DataStoreProject:1.0.3'
```

#### 功能展示
```
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化
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

如果想解决ANR问题，可以有2种方案

1、异步提交
 //第一个参数为 你的dataStore名称，第二参数为你存储的key，第三个参数为需要存储的值
DataStoreUtil.getInstance().applyPutString(SHARE_KEY_USER,KEY_SHARE_NAME,"23")

2、对象提交
  val testModel = TestModel(1, "王五-${Random().nextInt()}")
  DataStoreUtil.getInstance().put(SHARE_KEY_USER, KEY_SHARE_OBJ, testModel)

读取数据
//第一个参数为 你的dataStore名称，第二参数为你存储的key，第三个参数为默认值
DataStoreUtil.getInstance().getString(SHARE_KEY_USER, KEY_SHARE_NAME, "")
```


