package com.ys.pa200.ui.homeui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ys.pa200.R;
import com.ys.pa200.bean.PicData;
import com.ys.pa200.ui.adapter.PicViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadPicActivity extends AppCompatActivity {

    private ArrayList<PicData> picdatas;
    private int position;
    @BindView(R.id.pic_vp)
    ViewPager picVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_pic);
        initData();
    }

    public void initData()
    {
        ButterKnife.bind(this);
        picdatas = (ArrayList<PicData>) getIntent().getSerializableExtra("picdata");
        position = getIntent().getIntExtra("position",0);
        if (picdatas!=null && picdatas.size()>0){
            picVp.setAdapter(new PicViewPagerAdapter(this,picdatas));
            picVp.setCurrentItem(position);
        }
    }
}
