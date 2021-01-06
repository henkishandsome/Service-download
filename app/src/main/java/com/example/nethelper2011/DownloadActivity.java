package com.example.nethelper2011;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.example.nethelper2011.Service.DownloadService;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {
    private Button start_download,pause_download,cancel_download;

    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        start_download=findViewById(R.id.start_download);
        pause_download=findViewById(R.id.pause_download);
        cancel_download=findViewById(R.id.cancel_download);
        start_download.setOnClickListener(this);
        pause_download.setOnClickListener(this);
        cancel_download.setOnClickListener(this);
        Intent intent=new Intent(DownloadActivity.this,DownloadService.class);
        startService(intent); //启动服务
        bindService(intent,connection,BIND_AUTO_CREATE); //绑定服务
        if (ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DownloadActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onClick(View v) {
        if (downloadBinder == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.start_download:
                //String url ="https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                String url = "http://192.168.130.1:8080/Android 第一行代码(第2版).pdf";
                downloadBinder.startDownload(url);
                break;
            case R.id.pause_download:
                downloadBinder.pauseDownload();
                break;
            case R.id.cancel_download:
                downloadBinder.cancelDownload();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}