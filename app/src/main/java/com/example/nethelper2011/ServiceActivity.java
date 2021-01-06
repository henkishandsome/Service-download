package com.example.nethelper2011;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.nethelper2011.Service.MyIntentService;
import com.example.nethelper2011.Service.MyService;

public class ServiceActivity extends AppCompatActivity {
    private static final String TAG = "ServiceActivity";
private Button btn_startservice,btn_stopservice,btn_bindservice,btn_unbindservice,btn_intenservice;
private MyService.DownloadBinder downloadBinder;
private ServiceConnection connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        downloadBinder = (MyService.DownloadBinder) service;
        downloadBinder.startDownload();
        downloadBinder.getProgress();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        btn_startservice=findViewById(R.id.btn_startservice);
        btn_stopservice=findViewById(R.id.btn_stopservice);
        btn_bindservice=findViewById(R.id.btn_bindservice);
        btn_unbindservice=findViewById(R.id.btn_unbindservice);
        btn_intenservice=findViewById(R.id.btn_intenservice);
        btn_startservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent=new Intent(ServiceActivity.this, MyService.class);
                startService(startIntent);
            }
        });

        btn_stopservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(ServiceActivity.this,MyService.class);
                stopService(stopIntent);
            }
        });

        btn_bindservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindIntent = new Intent(ServiceActivity.this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE); //绑定服务

            }
        });

        btn_unbindservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection); //解绑服务
            }
        });

        btn_intenservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Thread id is"+Thread.currentThread().getId());
                Intent intentService = new Intent(ServiceActivity.this, MyIntentService.class);
                startService(intentService);
            }
        });
    }
}