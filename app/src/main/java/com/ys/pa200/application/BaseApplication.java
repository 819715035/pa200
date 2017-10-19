package com.ys.pa200.application;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import cn.bmob.v3.Bmob;

/**
 * Created by zzz
 */
public class BaseApplication extends LitePalApplication
{

    public static Context context;
    private static BaseApplication instance;

    public List<AppCompatActivity> activitys = new LinkedList<>();
    public List<Service> services = new LinkedList<>();

    @Override
    public void onCreate()
    {
        super.onCreate();
        Bmob.initialize(this, "ee2ad5936d23239edd34532b2ccb4130","pa200");
    }

    public static BaseApplication getInstance() {
        return instance;
    }


    public void addActivity(AppCompatActivity activity) {
        activitys.add(activity);
    }

    public void removeActivity(AppCompatActivity activity) {
        activitys.remove(activity);
    }

    public void closeApplication() {
        closeActivity();
        closeService();
    }

    public void closeService() {
        ListIterator<Service> listIterator = services.listIterator();
        while (listIterator.hasNext()) {
            Service service = listIterator.next();
            if (service != null) {
                stopService(new Intent(this, service.getClass()));
            }
        }
    }

    public void closeActivity() {
        ListIterator<AppCompatActivity> listIterator = activitys.listIterator();
        while (listIterator.hasNext()) {
            AppCompatActivity activity = listIterator.next();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 关掉最后加入进来的Activity
     *
     * @param counts
     */
    public void closeLastAddActivity(int counts) {
        for (int i = activitys.size() - counts; i < activitys.size(); i++) {
            AppCompatActivity activity = activitys.get(i);
            if (activity != null) {
                activity.finish();
            }
        }

    }
    /**
     * 关掉最后加入进来的Activity
     *
     * @param counts                 关掉最后加进来的个数
     * @param isCloseLastAddActivity 是否关掉最后一个加入近来的 activity
     */
    public void closeLastAddActivity(int counts, boolean isCloseLastAddActivity) {
        for (int i = activitys.size() - counts; i < activitys.size(); i++) {
            AppCompatActivity activity = activitys.get(i);
            if (isCloseLastAddActivity) {
                if (i == activitys.size()) {
                } else {
                    if (activity != null) {
                        activity.finish();
                    }
                }
            }
        }
    }

    public void finishPreviousActivity(Activity nowAct) {
        ListIterator<AppCompatActivity> listIterator = activitys.listIterator();
        while (listIterator.hasNext()) {
            AppCompatActivity activity = listIterator.next();
            if (activity != null && activity != nowAct) {
                activity.finish();
            }
        }
    }

    public static Context getContext() {
        return context;
    }

}
