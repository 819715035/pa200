package com.ys.pa200.ui.mainui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;

import com.ys.pa200.R;
import com.ys.pa200.ui.baseui.BaseActivity;

import java.util.ArrayList;

/**
 * 启动页面
 * Created by Administrator on 2016/9/18.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener
{

    private long showTime;
    private ArrayList<View> list;
    private CountDownTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //防止用户安装应用时点击打开而不是完成，按home键时导致应用重启的问题
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        initView();

    }

    private void initView()
    {
        showTime = 3 * 1000;
        openTime();
        timer.start();
    }

    private void openTime()
    {
        timer = new CountDownTimer(showTime, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {

            }

            @Override
            public void onFinish()
            {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

            }
        };
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fl_bottom:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
