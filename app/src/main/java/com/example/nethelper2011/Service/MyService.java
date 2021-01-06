package com.example.nethelper2011.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.nethelper2011.R;
import com.example.nethelper2011.ServiceActivity;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private DownloadBinder mBinder = new DownloadBinder();
    public class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d(TAG, "startDownload: ");
        }
        public int getProgress() {
            Log.d(TAG, "getProgress: ");
            return 0;
        }
    }
//    public MyService() {
//    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        Notification notification;
        NotificationManager manager;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, ServiceActivity.class);
        PendingIntent pi =PendingIntent.getActivity(this,0,intent,0);
        String CHANNEL_ID = "1234";//应用频道Id唯一值， 长度若太长可能会被截断，
        String CHANNEL_NAME = "android";//最长40个字符，太长会被截断
        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("this is title")
                .setContentText("this is text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        //Android 8.0 以上需包添加渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(notificationChannel);
        }
        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
