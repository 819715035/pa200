package com.ys.pa200.ui.mainui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.ys.pa200.R;
import com.ys.pa200.ui.baseui.BaseFragment;

/**
 * 商城
 * @author dujingjing
 */
public class ShoppingFragment extends BaseFragment implements OnClickListener
{
	
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.fragment_shopping, container, false);
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
	}
	
	public void initDatas()
	{
	}

	@Override
	public void onClick(View view) {

	}
}
