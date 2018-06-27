package com.zy.logcatdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zy.logcat.LogCatControl;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Timer timer;
    private TimerTask task;
    private boolean isStop = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        LogCatControl.getBuilder(this)
//                .setTitle("自定义标题")
//                .setSearchContent("自定义搜索内容")
//                .setSearchTag("自定义Tag")
//                /*设置显示级别
//                * 0 所有，1 系统，2 警告,3 错误
//                * */
//                .setShowGrade(3)
//                .show();
    }

    /**
     * 默认
     *
     * @param view
     */
    public void onTest(View view) {
        LogCatControl.getBuilder(this).clear();
        LogCatControl.getBuilder(this).show();
    }

    /**
     * 自定义标题
     *
     * @param view
     */
    public void onTest2(View view) {
        LogCatControl.getBuilder(this).clear();
        LogCatControl.getBuilder(this).setTitle("test").show();
    }


    /**
     * 自定义搜索内容
     *
     * @param view
     */
    public void onTest3(View view) {
        LogCatControl.getBuilder(this).clear();
        LogCatControl.getBuilder(this).setSearchContent("search").show();
    }


    /**
     * 自定义tag
     *
     * @param view
     */
    public void onTest4(View view) {
        LogCatControl.getBuilder(this).clear();
        LogCatControl.getBuilder(this).setSearchTag("MainActivity").show();
    }


    /**
     * 自定义log级别
     *
     * @param view
     */
    public void onTest5(View view) {
        LogCatControl.getBuilder(this).clear();
        LogCatControl.getBuilder(this).setShowGrade(3).show();
    }

    /**
     * 清除dialog
     *
     * @param view
     */
    public void onClear(View view) {
        LogCatControl.getBuilder(this).clear();
    }

    /**
     * 启动定时器
     *
     * @param view
     */
    public void onTimer(View view) {
        startTimer();
    }

    /**
     * 停止定时器
     *
     * @param view
     */
    public void onTimeroff(View view) {
        stopTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
        LogCatControl.getBuilder(this).clear();
    }

    private void startTimer() {
        if (isStop) {
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    Log.i(TAG, "log i " + System.currentTimeMillis());
                    Log.d(TAG, "log d " + System.currentTimeMillis());
                    Log.v(TAG, "log v " + System.currentTimeMillis());
                    Log.w(TAG, "log w " + System.currentTimeMillis());
                    Log.wtf(TAG, "log wtf " + System.currentTimeMillis());
                    Log.e(TAG, "log e " + System.currentTimeMillis());
                }
            };
            timer.schedule(task, 0, 2000);
            isStop = false;
        }

    }

    private void stopTimer() {
        if (!isStop) {
            task.cancel();
            timer.cancel();
            task = null;
            timer = null;
            isStop = true;
        }
    }
}