package com.zy.logcatdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zy.logcat.LogCatDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                i++;
//                System.out.println("sys----" + i);
//                Log.v("tj", "111111111111-" + i);
//                Log.d("hyd", "00000000000000---------" + i);
//                Log.w("lv", "onCreate(MainActivity.java:42):https://www.baidu.com/title=" + i);
//                Log.e("lcl", "onCreate(MainActivity.java:43):https://www.baidu.com");
//            }
//        }, 0, 1000);
//        Log.e("hdltag", "onCreate(Main2Activity.java:29):大哥");
    }

    public void onTest(View view) {
        new LogCatDialog(this).show();
    }
}