package com.ys.pa200.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ys.pa200.R;
import com.ys.pa200.bean.Patient;
import com.ys.pa200.model.CaseModel;
import com.ys.pa200.ui.homeui.ConnectUltrasonicActivity;
import com.ys.pa200.utils.MyToast;

import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 档案的dialog
 * @author zzz
 */
public class FileDialog extends Dialog implements android.view.View.OnClickListener {
	private OnDialogClickListener listener;
	private Button continue_pay_btn;  // 继续支付
	private TextView continue_pay_cancel_btn; // 取消

	private EditText dialog_bingli_id;
	private EditText dialog_bingli_name;
	private RadioGroup bingli_radioGroup;
	private RadioButton dialog_bingli_man;
	private RadioButton dialog_bingli_nv;
	private EditText dialog_bingli_nianling;
	private EditText dialog_bingli_weightkg;
	private EditText dialog_bingli_editxq;

	private String sexStr;
	private Patient patient;
	private String checkBody ="外围血管";


	public FileDialog(Context context, OnDialogClickListener listener) {
		super(context, R.style.viewDialog);
		this.listener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_new_bingli);
		initView();
		initDatas();
		initListener();
	}

	private void initView() {
		continue_pay_btn = (Button) findViewById(R.id.continue_pay_btn);
		continue_pay_cancel_btn = (TextView) findViewById(R.id.continue_pay_cancel_btn);

		dialog_bingli_id = (EditText) findViewById(R.id.dialog_bingli_id);
		dialog_bingli_name = (EditText) findViewById(R.id.dialog_bingli_name);
		bingli_radioGroup = (RadioGroup) findViewById(R.id.bingli_radioGroup);
		dialog_bingli_man = (RadioButton) findViewById(R.id.dialog_bingli_man);
		dialog_bingli_nv = (RadioButton) findViewById(R.id.dialog_bingli_nv);
		dialog_bingli_nianling = (EditText) findViewById(R.id.dialog_bingli_nianling);
		dialog_bingli_weightkg = (EditText) findViewById(R.id.dialog_bingli_weightkg);
		dialog_bingli_editxq = (EditText) findViewById(R.id.dialog_bingli_editxq);
	}

	private void initDatas() {
		sexStr = "男";
		setFragmentIndicator();
	}

	public void commitText(final TextView a, final TextView b) {
		//save2Bmob(a, b);
		save2Sqlite(a,b);
		dismiss();
	}

	//保存到本地
	private void save2Sqlite(TextView a, TextView b) {
		patient = new Patient();
		patient.setNumber(dialog_bingli_id.getText().toString().trim());
		patient.setName(dialog_bingli_name.getText().toString().trim());
		patient.setSex(sexStr);
		patient.setAge(dialog_bingli_nianling.getText().toString().toString());
		patient.setWeight(dialog_bingli_weightkg.getText().toString()+" kg");
		patient.setDate(new Date().getTime());
		a.setText("姓名: " + dialog_bingli_name.getText().toString().trim());
		b.setText("编号: " + dialog_bingli_id.getText().toString());
		checkBody = dialog_bingli_editxq.getText().toString().trim();
	}

	//保存到本地
	private void save2Bmob(final TextView a, final TextView b) {
		CaseModel caseModel = new CaseModel();
		caseModel.setCaseId(dialog_bingli_id.getText().toString());
		caseModel.setName(dialog_bingli_name.getText().toString());
		caseModel.setSex(sexStr);
		caseModel.setAge(dialog_bingli_nianling.getText().toString());
		caseModel.setWeight(dialog_bingli_weightkg.getText().toString());
		caseModel.setXiangmu(dialog_bingli_editxq.getText().toString());
		caseModel.save(new SaveListener<String>() {
			@Override
			public void done(String objectId, BmobException e) {
				if (e == null) {
					dismiss();
					a.setText("姓名: " + dialog_bingli_name.getText().toString().trim());
					b.setText("编号: " + dialog_bingli_id.getText().toString());
					dialog_bingli_id.setText("");
					dialog_bingli_name.setText("");
					dialog_bingli_nianling.setText("");
					dialog_bingli_weightkg.setText("");
					dialog_bingli_editxq.setText("");
					MyToast.getInstance(getContext()).show("添加数据成功", 1);
				} else {
					dismiss();
					MyToast.getInstance(getContext()).show("创建数据失败", 1);
				}
			}
		});
	}

	public Patient getPatient()
	{
		return this.patient;
	}

	public String getCheckBody(){
		return checkBody;
	}

	private void initListener()
	{
		continue_pay_btn.setOnClickListener(this);
		continue_pay_cancel_btn.setOnClickListener(this);
	}

	/**
	 * 底部导航按钮点击
	 */
	private void setFragmentIndicator()
	{
		bingli_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				switch (checkedId)
				{
					case R.id.dialog_bingli_man:
					{
						sexStr = dialog_bingli_man.getText().toString();
						break;
					}

					case R.id.dialog_bingli_nv:
					{
						sexStr = dialog_bingli_nv.getText().toString();
						break;
					}

					default:
						break;
				}
			}
		});
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.continue_pay_btn:
			{
				if (listener == null)
				{
					return;
				}
				listener.continuePay();
//				commitText();
				break;
			}

			case R.id.continue_pay_cancel_btn:
			{
				if (listener == null)
				{
					return;
				}
				listener.cancel();
				break;
			}

			default:
				break;
		}
	}

	public interface OnDialogClickListener
	{
		/**确定*/
		void continuePay();
		/**取消*/
		void cancel();
	}
}
