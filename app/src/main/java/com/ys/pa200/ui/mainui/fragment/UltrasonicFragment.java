package com.ys.pa200.ui.mainui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.ys.pa200.R;
import com.ys.pa200.ui.baseui.BaseFragment;
import com.ys.pa200.ui.homeui.CaseListActivity;
import com.ys.pa200.ui.homeui.LocalhostListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商城
 * @author dujingjing
 */
public class UltrasonicFragment extends BaseFragment implements OnClickListener
{
	
	private View view;
	@BindView(R.id.home_ultconnect_chakanbinli)
	Button home_ultconnect_chakanbinli;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.fragment_ultrasonic, container, false);
		}
		else
		{
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
			{
				parent.removeView(view);
			}
		}
		initView();
		return view;
	}
	
	private void initView()
	{
		ButterKnife.bind(this, view);
	}
	
	public void initDatas()
	{
	}

	@OnClick(R.id.home_ultconnect_chakanbinli)
	public void onClick(View view)
	{
		int viewID = view.getId();
		switch (viewID)
		{
			case R.id.home_ultconnect_chakanbinli:
			{
				startActivity(new Intent(getActivity() , LocalhostListActivity.class));
				break;
			}


			default:
				break;
		}
	}
}
