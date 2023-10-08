package com.example.datastoreproject.activity;

import static com.example.datastoreproject.activity.MainActivity.KEY_SHARE_OBJ;
import static com.example.datastoreproject.activity.MainActivity.SHARE_KEY_USER;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gxx.datalibrary.util.JavaContinuation;
import com.example.datastoreproject.R;
import com.example.datastoreproject.model.TestModel;
import com.gxx.datalibrary.DataStoreUtil;

import java.util.Random;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class JavaActivity extends AppCompatActivity{
    private String TAG = "JavaActivity";
    int count = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        TextView textView = this.findViewById(R.id.tv_java_time_show);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    count++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("count="+count);
                        }
                    });
                }

            }
        }).start();

        this.findViewById(R.id.bt_get_data_store_user_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               TestModel testModel =  DataStoreUtil.Companion.getInstance().getAny(SHARE_KEY_USER,KEY_SHARE_OBJ, TestModel.class);
                Log.d(TAG,"testModel.name="+testModel.getName());
            }
        });


        this.findViewById(R.id.bt_insert_user_info_obj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestModel testModel = new TestModel(new Random().nextInt(),"张三--"+new Random().nextInt());
                DataStoreUtil.Companion.getInstance().put(SHARE_KEY_USER, KEY_SHARE_OBJ, testModel, new JavaContinuation<Object>() {
                    @Override
                    public void resume(Object value) {
                       Log.d(TAG,"resume回调");
                    }

                    @Override
                    public void resumeWithException(@NonNull Throwable exception) {

                    }

                    @NonNull
                    @Override
                    public CoroutineContext getContext() {
                        return EmptyCoroutineContext.INSTANCE;
                    }
                });
            }
        });

    }
}
