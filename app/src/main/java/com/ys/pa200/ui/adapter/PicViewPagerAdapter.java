package com.ys.pa200.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ys.pa200.bean.PicData;
import com.ys.pa200.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by admin on 2017/09/26 0026.
 */

public class PicViewPagerAdapter extends PagerAdapter
{
    private Context context;
    private ArrayList<PicData> picDatas;

    public PicViewPagerAdapter(Context context, ArrayList<PicData> picDatas)
    {
        this.picDatas = picDatas;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return picDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        ImageView imageView = new ImageView(context);
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.width = ScreenUtils.getScreenWidth(context);
        params.height = ScreenUtils.getScreenWidth(context);
        imageView.setLayoutParams(params);
        imageView.setImageURI(Uri.parse(picDatas.get(position).getPath()));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }
}
