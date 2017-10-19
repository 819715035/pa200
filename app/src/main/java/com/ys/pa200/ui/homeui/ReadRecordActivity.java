package com.ys.pa200.ui.homeui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.pa200.R;
import com.ys.pa200.bean.CheckProgrem;
import com.ys.pa200.bean.Patient;
import com.ys.pa200.ui.adapter.CheckProgremAdapter;
import com.ys.pa200.ui.adapter.PicAdapter;
import com.ys.pa200.ui.baseui.BaseActivity;

import org.litepal.crud.DataSupport;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ys.pa200.R.id.check_lv;
import static com.ys.pa200.R.id.nocheck_tv;

public class ReadRecordActivity extends BaseActivity {

    @BindView(R.id.patient_tv)
    TextView patientTv;
    @BindView(R.id.pic_rv)
    RecyclerView picRv;
    @BindView(check_lv)
    ListView checkLv;
    @BindView(nocheck_tv)
    TextView nocheckTv;
    @BindView(R.id.nopic_tv)
    TextView nopicTV;
    private Patient patient;
    private CheckProgremAdapter checkProgremAdapter;
    private CheckProgrem picdatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_record);
        initData();
        setListener();
    }

    private void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Patient p = (Patient) intent.getSerializableExtra("patient");
        patient = DataSupport.find(Patient.class, p.getId(), true);
        if (patient!=null){
            patientTv.setText(patient.toString());
            getCheckProgrem();
        }
    }

    /**
     *
     *设置检查项目的适配器
     */
    private void getCheckProgrem()
    {
        if(patient.getCheckProgrems()!=null&&patient.getCheckProgrems().size()>0){
            Collections.reverse(patient.getCheckProgrems());
            checkProgremAdapter = new CheckProgremAdapter(this,patient.getCheckProgrems());
            checkLv.setAdapter(checkProgremAdapter);
            setPicdataAdapter(0);
            nocheckTv.setVisibility(View.GONE);
        }else{
            nocheckTv.setVisibility(View.VISIBLE);
        }
    }
    private void setListener() {
        checkLv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                setPicdataAdapter((int) l);
                //设置点击listview变背景颜色
                checkProgremAdapter.setSelectPosition(i);
            }
        });
    }

    /**
     * 设置图片
     */
    private void setPicdataAdapter(int id)
    {
        picdatas = DataSupport.find(CheckProgrem.class,patient.getCheckProgrems().get(id).getId(),true);
        if (picdatas!=null && picdatas.getPic()!=null && picdatas.getPic().size()>0)
        {
            nopicTV.setVisibility(View.GONE);
        }else{
            nopicTV.setVisibility(View.VISIBLE);
        }
        picRv.setAdapter(new PicAdapter(this,picdatas));
        picRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }
}
