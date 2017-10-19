package com.ys.pa200.ui.homeui;

import android.os.Bundle;
import android.view.View;

import com.ys.pa200.R;
import com.ys.pa200.ui.baseui.BaseActivity;
import com.ys.pa200.weight.HeadColumnView;
import com.ys.pa200.weight.ProgressWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司简介的activity
 */
public class CompanyproActivity extends BaseActivity
{


	@BindView(R.id.companypro_headColumnView)
	HeadColumnView companypro_headColumnView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_companypro);
		init();
		setStatusBar();
	}

	private void init()
	{
		ButterKnife.bind(this);
		onClick();
	}
	
	private void onClick()
	{
		companypro_headColumnView.setLeftBtnClick(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				CompanyproActivity.this.finish();
			}
		});
	}

}
