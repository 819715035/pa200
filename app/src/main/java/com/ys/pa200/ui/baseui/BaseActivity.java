package com.ys.pa200.ui.baseui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ys.pa200.R;
import com.ys.pa200.application.BaseApplication;
import com.ys.pa200.utils.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by zzz
 */
public class BaseActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        BaseApplication application = (BaseApplication) getApplication();
        application.addActivity(this);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    public void setStatusBar()
    {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.my_0888cf), 0);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        BaseApplication application = (BaseApplication) getApplication();
        application.removeActivity(this);
    }

    /**
     * Activity跳转
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * Activity跳转
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        openActivity(pClass, pBundle, null);
    }

    /**
     * Activity跳转
     *
     * @param pClass
     * @param pBundle
     * @param uri
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle, Uri uri) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
    }

    /**
     * Activity跳转
     *
     * @param pAction
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * Activity跳转
     *
     * @param pAction
     * @param pBundle
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        openActivity(pAction, pBundle, null);
    }

    /**
     * Activity跳转
     *
     * @param pAction
     * @param pBundle
     * @param uri
     */
    protected void openActivity(String pAction, Bundle pBundle, Uri uri) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
    }

}
