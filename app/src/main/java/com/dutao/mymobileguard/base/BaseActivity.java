package com.dutao.mymobileguard.base;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Activity的基类，处理一些Activity共有的问题：
 * Title: <br>
 * Description: <br>
 * Copyright: Copyright (c) 2007<br>
 *
 * @author dutao
 * @version 1.0
 * @date 2015-5-20
 */
public class BaseActivity extends Activity {

    protected String TAG = getClass().getSimpleName();

    /**
     * 静态变量，可以存储所有启动过的Activity
     */
    private static ArrayList<Activity> mActivityList = new ArrayList<Activity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivityList.add(this);
    }

    /**
     * 精华啊，Activy生命周期终止则移除
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityList.remove(this);
    }

    /**
     * @param view 标题栏左侧按钮点击事件
     */
    public void goBack(View view) {
        onBackPressed();
    }

    /**
     * @param view 标题栏右侧按钮点击事件
     */
    public void goNext(View view) {
        showShortToast("自定义跳转页面");
    }

    /**
     * @return 返回栈顶Activity
     */
    public static Activity GetTopActivity() {
        if (mActivityList.size() <= 0)
            return null;
        return mActivityList.get(mActivityList.size() - 1);
    }

    /**
     * @param str 显示短时间toast信息
     */
    protected void showShortToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param str 显示长时间toast信息
     */
    protected void showLongToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
