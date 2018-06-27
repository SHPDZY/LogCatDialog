package com.zy.logcat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.zy.logcat.R;

class LogCatDialog extends Dialog {

    private String title = "Console";

    private static final int WHAT_NEXT_LOG = 778;
    private static final int WHAT_STATUS_LOG = 779;

    private TextView tvLog;

    private TextView tvStatus;

    private RadioGroup rgGrade;

    private TextView tvTitle;

    private List<String> contentList = new ArrayList<>();

    /*是否自动拉取到最底部*/
    private boolean isAutoFullScroll = true;


    private String searchContent = "";
    private String noContentHint = "没有log信息";
    private String searchHint = "正在获取log信息";

    /*显示级别，0 所有，1 系统，2 警告,3 错误*/
    private int showGrade = 0;


    private boolean isRuning = true;
    private ImageView ivDwon;
    private SearchEditText etContent;
    public String searchTag = "";//过滤tag


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_NEXT_LOG:
                    String line = (String) msg.obj;
                    if (!TextUtils.isEmpty(searchTag)) {
                        if (line.contains(searchTag)) {
                            if (!TextUtils.isEmpty(searchContent)) {
                                if (line.contains(searchContent)) {//同时搜索
                                    contentList.add(line);
                                    append(line);
                                }
                            } else {
                                contentList.add(line);//只搜索tag
                                append(line);
                            }
                        }
                    } else {
                        if (!TextUtils.isEmpty(searchContent)) {
                            if (line.contains(searchContent)) {//只搜索内容
                                contentList.add(line);
                                append(line);
                            }
                        } else {
                            contentList.add(line);//所有
                            append(line);
                        }
                    }
                    break;
                case WHAT_STATUS_LOG:
                    if (contentList.size()>0) {
                        tvStatus.setVisibility(View.GONE);
                        tvLog.setVisibility(View.VISIBLE);
                        if (isAutoFullScroll) {
                            refreshLogView();
                        }
                    } else {
                        tvStatus.setText(noContentHint);
                        tvStatus.setVisibility(View.VISIBLE);
                        tvLog.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };



    LogCatDialog(@NonNull Context context) {
        super(context);

    }

    public LogCatDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    void init() {
        initView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Process logcatProcess = null;
                BufferedReader bufferedReader = null;
                StringBuilder log = new StringBuilder();
                String line;
                try {
                    while (isRuning) {
                        logcatProcess = Runtime.getRuntime().exec("logcat");
                        bufferedReader = new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
                        while ((line = bufferedReader.readLine()) != null) {
                            log.append(line);
                            Message message = mHandler.obtainMessage();
                            message.what = WHAT_NEXT_LOG;
                            message.obj = line;
                            mHandler.sendMessage(message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 设置搜索的内容(默认没有)
     *
     * @param searchContent
     */
    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    /**
     * 设置目标tag（默认没有）
     *
     * @param searchTag
     */
    public void setSearchTag(String searchTag) {
        this.searchTag = searchTag;
    }

    /**
     * 设置显示级别
     *
     * @param showGrade 0 所有，1 系统，2 警告,3 错误
     */
    public void setShowGrade(int showGrade) {
        this.showGrade = showGrade;
    }

    /**
     * 设置是否显示级别过滤
     *
     * @param isShowGrade
     */
    public void setShowGrade(boolean isShowGrade) {
        if (isShowGrade) {
            rgGrade.setVisibility(View.VISIBLE);
        } else {
            rgGrade.setVisibility(View.GONE);
        }
    }

    /**
     * 追加内容
     *
     * @param line
     */
    private void append(String line) {
        if (showGrade == 0 || showGrade == 1) {
            if (line.contains(" E ")) {
                tvLog.append("\n\n");
                showError(line);
            } else if (line.contains(" W ")) {
                tvLog.append("\n\n");
                showWarning(line);
            } else {
                tvLog.append("\n\n" + line);
            }
        } else if (showGrade == 2) {
            if (line.contains(" W ")) {
                tvLog.append("\n\n");
                showWarning(line);
            }
        } else if (showGrade == 3) {
            if (line.contains(" E ")) {
                tvLog.append("\n\n");
                showError(line);
            }
        }
        mHandler.sendEmptyMessageDelayed(WHAT_STATUS_LOG,1000);

    }

    /**
     * 显示警告级别的日志
     *
     * @param line
     */
    private void showWarning(String line) {
        showLine(line, "#ba8a27");
    }

    /**
     * 显示错误级别的信息
     *
     * @param line
     */
    private void showError(String line) {
        showLine(line, "red");
    }

    private void showLine(String line, String color) {
        if (line.contains("http://") || line.contains("https://")) {
            String url = line.substring(line.indexOf("http"));
            tvLog.append(Html.fromHtml("<font color='" + color + "'>" + line.substring(0, line.indexOf("http")) + "</font>"));
            tvLog.append(Html.fromHtml("<a href='" + url + "'>" + url + "</a>"));
        } else {
            tvLog.append(Html.fromHtml("<font color='" + color + "'>" + line + "</font>"));
        }
    }

    /**
     * 关闭任务
     */
    public void closeTask() {
        isRuning = false;
    }

    @Override
    public void dismiss() {
        closeTask();
        super.dismiss();
    }

    void refreshLogView() {
        int offset = tvLog.getLineCount() * tvLog.getLineHeight();
        if (offset > tvLog.getHeight()) {
            tvLog.scrollTo(0, offset - tvLog.getHeight());
        }
    }

    private float y1 = 0;
    private float y2 = 0;

    private void initView() {
        View view = View.inflate(getContext(), R.layout.logcat_dialog, null);

        setContentView(view);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        //日志级别
        rgGrade = (RadioGroup) view.findViewById(R.id.rg_grade);
        rgGrade.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_all) {
                    showGrade = 0;
                    searchContent("");
                } else if (checkedId == R.id.rb_system_out) {
                    showGrade = 1;
                    searchContent("System.out");
                } else if (checkedId == R.id.rb_warming) {
                    showGrade = 2;
                    searchContent("");
                } else if (checkedId == R.id.rb_error) {
                    showGrade = 3;
                    searchContent("");
                }
            }
        });
        tvStatus = (TextView) view.findViewById(R.id.tv_status);
        etContent = (SearchEditText) view.findViewById(R.id.et_content);
        etContent.setOnClickOkListener(new SearchEditText.OnClickOkListener() {
            @Override
            public void onOk(String content) {
                tvStatus.setText(searchHint);
                tvStatus.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "正在搜索[" + content + "]", Toast.LENGTH_SHORT).show();
                searchContent(content);
            }
        });
        tvLog = (TextView) view.findViewById(R.id.tv_consol);
        tvLog.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvLog.setMovementMethod(LinkMovementMethod.getInstance());
        tvLog.setOnTouchListener(TvOnTouchListener);
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ivDwon = (ImageView) view.findViewById(R.id.iv_down);
        ivDwon.setVisibility(View.GONE);
        ivDwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoFullScroll = true;
                ivDwon.setVisibility(View.GONE);
            }
        });
          /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = width * 9 / 10; // 宽度
        lp.height = height * 8 / 10; // 高度
        dialogWindow.setAttributes(lp);
        setDefault();
    }

    void setDefault() {
        if (!TextUtils.isEmpty(searchContent)) {
            etContent.setText(searchContent);
            searchContent(searchContent);
        }
        switch (showGrade) {
            case 0:
                rgGrade.check(R.id.rb_all);
                break;
            case 1:
                rgGrade.check(R.id.rb_system_out);
                break;
            case 2:
                rgGrade.check(R.id.rb_warming);
                break;
            case 3:
                rgGrade.check(R.id.rb_error);
                break;

        }
    }

    /**
     * 设置title
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 搜索内容
     *
     * @param content
     */
    private void searchContent(String content) {
        searchContent = content;
        tvLog.setText("--------------search info------------\n");
        for (String item : contentList) {
            if (item.contains(content)) {
                append(item);
            }
        }

    }

    View.OnTouchListener TvOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //继承了Activity的onTouchEvent方法，直接监听点击事件
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //当手指按下的时候
                y1 = event.getY();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //当手指离开的时候
                y2 = event.getY();
                if (y2 - y1 > 50) {
                    isAutoFullScroll = false;
                    ivDwon.setVisibility(View.VISIBLE);
                }
            }
            return false;
        }
    };
}
