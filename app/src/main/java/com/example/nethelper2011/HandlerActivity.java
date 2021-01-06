package com.example.nethelper2011;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class HandlerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int UPDATE_TEXT=1;
private TextView tv_change;
private Button btn_change;
//private Handler mhandler = new Handler() {
//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        switch (msg.what) {
//            case UPDATE_TEXT:
//                tv_change.setText("handler异步更新");
//        }
//    }
//};
    private MHandler mhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        mhandler = new MHandler(this);
        tv_change=findViewById(R.id.tv_change);
        tv_change.setOnClickListener(this);
        btn_change=findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.what=UPDATE_TEXT;
                        mhandler.sendMessage(message);
                    }
                }).start();
        }
    }

    private static class MHandler extends Handler {
        private final WeakReference<HandlerActivity> mActivity;

        public MHandler(HandlerActivity activity) {
            mActivity = new WeakReference<HandlerActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (mActivity.get() == null) {
                return;
            }
            //mActivity.get().todo();
            switch (msg.what) {
                case UPDATE_TEXT:
                    mActivity.get().tv_change.setText("handler异步更新");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler.removeCallbacksAndMessages(null);
    }
}