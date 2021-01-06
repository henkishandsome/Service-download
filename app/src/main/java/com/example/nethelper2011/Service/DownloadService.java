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
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.nethelper2011.DownloadActivity;
import com.example.nethelper2011.MainActivity;
import com.example.nethelper2011.R;
import com.example.nethelper2011.ServiceActivity;
import com.example.nethelper2011.Util.DownloadListener;
import com.example.nethelper2011.javaclass.DownloadTask;

import java.io.File;

public class DownloadService extends Service {
    public DownloadService() {
    }

    private DownloadTask downloadTask;

    private String downloadUrl;

   private DownloadListener listener = new DownloadListener() {
       @Override
       public void onProgress(int progress) {
           getNotificationManager().notify(1,getNotification("Downloading...",progress));
       }

       @Override
       public void onSuccess() {
           downloadTask = null;
           //下载成功时将前台服务关闭，并创建一个下载成功的通知
           stopForeground(true);
           getNotificationManager().notify(1,getNotification("Download Success",-1));
           Toast.makeText(DownloadService.this,"Download Success",Toast.LENGTH_LONG).show();

       }

       @Override
       public void onFailed() {
           downloadTask = null;
           //下载失败时将前台服务关闭，并创建一个下载失败的通知
           stopForeground(true);
           getNotificationManager().notify(1,getNotification("Download failed",-1));
           Toast.makeText(DownloadService.this,"Download failed",Toast.LENGTH_LONG).show();

       }

       @Override
       public void onPaused() {
           downloadTask = null;
           Toast.makeText(DownloadService.this,"paused",Toast.LENGTH_LONG).show();

       }

       @Override
       public void onCanceled() {
           downloadTask = null;
           stopForeground(true);
           Toast.makeText(DownloadService.this,"Cancled",Toast.LENGTH_LONG).show();

       }
   };

   private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
       return mBinder;
    }

    public class DownloadBinder extends Binder {

        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                downloadTask.execute(downloadUrl);
                startForeground(1,getNotification("Downloading...",0));
                Toast.makeText(DownloadService.this,"Downloading...",Toast.LENGTH_LONG).show();
            }
        }

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancleDownload();
            } else {
                if (downloadUrl != null) {
                    //取消下载时需将文件删除，并将通知关闭
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory+fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this,"Canceled",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title,int progress) {
//        Intent intent = new Intent(DownloadService.this, DownloadActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
//        builder.setContentIntent(pi);
//        builder.setContentTitle(title);
//        if (progress > 0) {
//            //当progress大于或等于0时才需显示下载进度
//            builder.setContentText(progress+"%");
//            builder.setProgress(100,progress,false);
//        }
//        return builder.build();
        NotificationManager manager;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, ServiceActivity.class);
        PendingIntent pi =PendingIntent.getActivity(this,0,intent,0);
        String CHANNEL_ID = "1234";//应用频道Id唯一值， 长度若太长可能会被截断，
        String CHANNEL_NAME = "android";//最长40个字符，太长会被截断
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        if (progress > 0) {
            //当progress大于或等于0时才需显示下载进度
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
//        builder.build();
        //Android 8.0 以上需包添加渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(notificationChannel);
        }
        return builder.build();
    }
}
