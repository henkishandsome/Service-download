package com.example.nethelper2011;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nethelper2011.Util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
private Button btn_handler,btn_asynctask,btn_service,btn_downloadservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpUtil.sendOkHttpRequest("http://www.baidu.com",new okhttp3.Callback(){
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData=response.body().string();
                //startActivity(new Intent(MainActivity.this,HandlerActivity.class));
                //Toast.makeText(MainActivity.this,"网络正常",Toast.LENGTH_LONG).show();
            }
        });
        btn_handler=findViewById(R.id.btn_handler);
        btn_asynctask=findViewById(R.id.btn_asynctask);
        btn_service=findViewById(R.id.btn_service);
        btn_downloadservice=findViewById(R.id.btn_downloadservice);
        btn_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HandlerActivity.class));
            }
        });
        btn_asynctask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AsyncTaskActivity.class));
            }
        });
        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ServiceActivity.class));
            }
        });
        btn_downloadservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DownloadActivity.class));
            }
        });
    }
}