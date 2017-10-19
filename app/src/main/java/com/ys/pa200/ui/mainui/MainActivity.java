package com.ys.pa200.ui.mainui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ys.pa200.R;
import com.ys.pa200.application.BaseApplication;
import com.ys.pa200.ui.baseui.BaseActivity;
import com.ys.pa200.ui.baseui.BaseFragment;
import com.ys.pa200.ui.mainui.fragment.HomeFragment;
import com.ys.pa200.ui.mainui.fragment.ShoppingFragment;
import com.ys.pa200.ui.mainui.fragment.UltrasonicFragment;
import com.ys.pa200.ui.mainui.fragment.UserFragment;
import com.ys.pa200.utils.ImageFactory;
import com.ys.pa200.utils.MyToast;
import com.ys.pa200.utils.ToastUtils;
import com.ys.pa200.weight.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener
{
    @BindView(R.id.main_pager)
    NoScrollViewPager mainPager;

    @BindView(R.id.text_home)
    TextView text_home;

    @BindView(R.id.text_task_ultas)
    TextView text_task_ultas;

    @BindView(R.id.text_shopping)
    TextView text_shopping;

    @BindView(R.id.text_user)
    TextView text_user;

    @BindView(R.id.img_home)
    ImageView img_home;

    @BindView(R.id.img_task_ultas)
    ImageView img_task_ultas;

    @BindView(R.id.img_shopping)
    ImageView img_shopping;

    @BindView(R.id.img_user)
    ImageView img_user;

    private HomeFragment homeFragment;
    private UltrasonicFragment ultrasonicFragment;
    private ShoppingFragment shoppingFragment;
    private UserFragment userFragment;
    private ArrayList<BaseFragment> fragments;
    private long exitTime = 0;
//    private Probe probe;
//    private static String cfgRoot = "cfg";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setStatusBar();

    }

    private void initView()
    {
        ButterKnife.bind(this);
//        probe = WifiProbe.init(cfgRoot, getAssets());

//        probe.setSystemListener(this);

        fragments = new ArrayList<>();
        homeFragment = new HomeFragment();
        ultrasonicFragment = new UltrasonicFragment();
        shoppingFragment = new ShoppingFragment();
        userFragment = new UserFragment();
        fragments.add(homeFragment);
        fragments.add(ultrasonicFragment);
        fragments.add(shoppingFragment);
        fragments.add(userFragment);

        mainPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        mainPager.setCurrentItem(0);
        mainPager.setOffscreenPageLimit(1);
        setTextColor(1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                //没有授权
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
        }

    }

    @OnClick({R.id.rl_home, R.id.rl_task_car, R.id.rl_shopping, R.id.rl_me})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.rl_home:

                mainPager.setCurrentItem(0);
                setTextColor(1);
                break;
            case R.id.rl_task_car:
                mainPager.setCurrentItem(1);
                setTextColor(2);
                break;
            case R.id.rl_shopping:
                mainPager.setCurrentItem(2);
                setTextColor(3);
                break;
            case R.id.rl_me:
                mainPager.setCurrentItem(3);
                setTextColor(4);
                break;
        }
    }

    private void setTextColor(int Type)
    {
        img_home.setBackground(ImageFactory.IntToDrawble(MainActivity.this,R.mipmap.icon_home_normal));
        img_task_ultas.setBackground(ImageFactory.IntToDrawble(MainActivity.this,R.mipmap.icon_devices_normal));
        img_shopping.setBackground(ImageFactory.IntToDrawble(MainActivity.this,R.mipmap.icon_music_normal));
        img_user.setBackground(ImageFactory.IntToDrawble(MainActivity.this,R.mipmap.icon_user_normal));
        text_home.setSelected(false);
        text_task_ultas.setSelected(false);
        text_shopping.setSelected(false);
        text_user.setSelected(false);

        if(Type==1)
        {
            img_home.setBackground(ImageFactory.IntToDrawble(MainActivity.this,R.mipmap.icon_home_selected));
            text_home.setSelected(true);
        }
        else if(Type==2)
        {
            img_task_ultas.setBackground(ImageFactory.IntToDrawble(MainActivity.this,R.mipmap.icon_devices_selected));
            text_task_ultas.setSelected(true);
        }
        else if(Type==3)
        {
            img_shopping.setBackground(ImageFactory.IntToDrawble(MainActivity.this,R.mipmap.icon_music_selected));
            text_shopping.setSelected(true);
        }
        else if(Type==4)
        {
            img_user.setBackground(ImageFactory.IntToDrawble(MainActivity.this,R.mipmap.icon_user_selected));
            text_home.setSelected(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showString(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                closeApplication();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出APP
     */
    private void closeApplication()
    {
        BaseApplication application = (BaseApplication) getApplication();
        application.closeApplication();
    }

    public class MainAdapter extends FragmentPagerAdapter
    {
        public MainAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public int getCount()
        {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position)
        {
            return fragments.get(position);
        }
    }

//    @Override
//    public void onInitialized() {
////        ToastMgr.show("connected");
//    }
//
//    @Override
//    public void onInitializationError(String message) {
////        ToastMgr.show("connect failed: " + message);
//    }
//
//    @Override
//    public void onInitialingLowVoltageError(String message)
//    {
////        ToastMgr.show(message);
//    }
//
//    @Override
//    public void onSystemError(String message)
//    {
////        ToastMgr.show("System Error: " + message);
//    }
//
//    public static Intent newIntent(Context packageContext) {
//        Intent intent = new Intent(packageContext, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        return intent;
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else{
                MyToast.getInstance(this).show("没有读取内存卡的权限，将无法截图",0);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
