package com.ys.pa200.ui.homeui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.pa200.R;
import com.ys.pa200.bean.CheckProgrem;
import com.ys.pa200.bean.Patient;
import com.ys.pa200.ui.adapter.PatientAdapter;
import com.ys.pa200.ui.adapter.SearchTextviewAdapter;
import com.ys.pa200.ui.baseui.BaseActivity;
import com.ys.pa200.utils.MyToast;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import leltek.viewer.model.WifiProbe;

public class LocalhostListActivity extends BaseActivity {

    @BindView(R.id.search_record_at)
    AutoCompleteTextView searchRecordEt;
    @BindView(R.id.caselist_list)
    ListView patientLv;
    @BindView(R.id.norecord_tv)
    TextView norecordTv;

    private ArrayList<Patient> patients;
    private PatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localhost_list);
        initData();
        setListener();
    }

    private void setListener() {
        searchRecordEt.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                startReadPatientActivity((Patient) adapterView.getItemAtPosition((int) l));
                searchRecordEt.setText("");
            }
        });
        patientLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startReadPatientActivity(patients.get(i));
            }
        });
        patientLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                startOpenScanActivity(i);
                return true;
            }
        });
    }

    private void startOpenScanActivity(final int position)
    {
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("您确定要为"+patients.get(position).getName()+"病人做检查吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if (WifiProbe.getDefault().isConnected())
                        {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("patient", patients.get(position));
                            openActivity(ConnectUltrasonicActivity.class, bundle);
                        }else{
                            MyToast.getInstance(LocalhostListActivity.this).show("无线探头连接异常，请连接成功后再操作",0);
                            WifiProbe.getDefault().initialize();
                        }
                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();

    }

    /**
     * 跳转到查看病人界面
     * @param patient
     */
    private void startReadPatientActivity(Patient patient)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("patient",patient);
        openActivity(ReadRecordActivity.class,bundle);
    }

    private void initData() {
        ButterKnife.bind(this);
        getDataFromSqlite();
    }

    private void getDataFromSqlite() {
        patients = (ArrayList<Patient>) DataSupport.findAll(Patient.class,true);
        if (patients!=null && patients.size()>0){
            showData();
            //隐藏文本
            norecordTv.setVisibility(View.GONE);
        }else{
            //显示没有数据
            norecordTv.setVisibility(View.VISIBLE);
        }
    }

    private void showData() {
        adapter = new PatientAdapter(this, patients, new PatientAdapter.OnPatientItemClickListener() {
            @Override
            public void onDelectListener(Patient patient) {
                delectPatientData(patient);
            }
        });
        patientLv.setAdapter(adapter);
        searchRecordEt.setAdapter(new SearchTextviewAdapter(patients,this));
    }

    /**
     * 删除病人信息
     * @param patient
     */
    private void delectPatientData(final Patient patient) {

        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("您确定要删除病人及其图片吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position)
                    {
                        delectPatient(patient);
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }

    /**
     * 删除病人信息
     * @param patient
     */
    private void delectPatient(Patient patient)
    {
        Patient p = DataSupport.find(Patient.class,patient.getId(),true);
        for (int i=0;i<p.getCheckProgrems().size();i++){
            //得到检查项目
            CheckProgrem checkProgrem = DataSupport.find(CheckProgrem.class,p.getCheckProgrems().get(i).getId(),true);
            for (int j=0;j<checkProgrem.getPic().size();j++){
                String path = checkProgrem.getPic().get(j).getPath();
                Log.d("localhostlistavtivity","图片路径"+path);
                File file = new File(path);
                if (file.exists()){
                    file.delete();
                }
            }
        }
        DataSupport.delete(Patient.class,p.getId());
        patients.remove(patient);
        adapter.notifyDataSetChanged();
        if (patients!=null && patients.size()<=0){
            norecordTv.setVisibility(View.VISIBLE);
        }
    }
}
