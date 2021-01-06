package com.example.nethelper2011.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";
    public MyIntentService() {
        super("MyIntentService");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //打印当前线程的id
        Log.d(TAG, "Thread id is"+ Thread.currentThread().getId());

    }
}
