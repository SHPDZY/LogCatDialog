package com.zy.logcat;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by ZY on 2018/6/22.
 */

public class LogCatControl {
    private static LogCatControl logCatControl = null;
    private Context context;
    private LogCatDialog catDialog;

    public static LogCatControl getBuilder(Context context) {
        if (logCatControl == null) {
            synchronized (LogCatControl.class) {
                if (logCatControl == null) {
                    logCatControl = new LogCatControl(context).builder();
                }
            }
        }
        return logCatControl;
    }

    public LogCatControl(Context context) {
        this.context = context;
    }

    private LogCatControl builder() {
        catDialog = new LogCatDialog(context,R.style.dialog);
        return this;
    }

    public void show() {
        catDialog.init();
        catDialog.show();
    }

    /**
     * 设置title
     *
     * @param title
     */
    public LogCatControl setTitle(String title) {
        catDialog.setTitle(title);
        return this;
    }

    /**
     * 设置搜索的内容(默认没有)
     *
     * @param searchContent
     */
    public LogCatControl setSearchContent(String searchContent) {
        catDialog.setSearchContent(searchContent);
        return this;
    }


    /**
     * 设置目标tag（默认没有）
     *
     * @param searchTag
     */
    public LogCatControl setSearchTag(String searchTag) {
        catDialog.setSearchTag(searchTag);
        return this;
    }

    /**
     * 设置显示级别
     *
     * @param showGrade 0 所有，1 系统，2 警告,3 错误
     */
    public LogCatControl setShowGrade(int showGrade) {
        catDialog.setShowGrade(showGrade);
        return this;
    }

    /**
     * 设置是否显示级别过滤
     *
     * @param isShowGrade
     */
    public LogCatControl setShowGrade(boolean isShowGrade) {
        catDialog.setShowGrade(isShowGrade);
        return this;
    }

    private void cleardialog( ) {
        catDialog.dismiss();
        catDialog.cancel();
        catDialog = null;
    }


    /**
     * 清除dialog
     */
    public void clear() {
        if (logCatControl==null){
            return;
        }
        cleardialog();
        logCatControl = null;
    }
}
