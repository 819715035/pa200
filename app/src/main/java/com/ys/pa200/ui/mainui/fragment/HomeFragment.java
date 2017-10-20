package com.ys.pa200.ui.mainui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.ys.pa200.R;
import com.ys.pa200.dialog.HintDialog;
import com.ys.pa200.ui.baseui.BaseFragment;
import com.ys.pa200.ui.homeui.CompanyproActivity;
import com.ys.pa200.ui.homeui.ConnectUltrasonicActivity;
import com.ys.pa200.utils.MyToast;
import com.ys.pa200.utils.NetworkUtls;
import com.ys.pa200.utils.ToastUtils;
import com.ys.pa200.utils.UiUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import leltek.viewer.model.Probe;
import leltek.viewer.model.WifiProbe;

/**
 * 首页
 * @author dujingjing
 */
public class HomeFragment extends BaseFragment implements OnClickListener , Probe.SystemListener
{
	
	private View view;
	private HintDialog dialog;
	private Probe probe;
	private static String cfgRoot = "cfg";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.fragment_home, container, false);
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
		initDatas();
		return view;
	}
	
	private void initView()
	{
		ButterKnife.bind(this, view);
	}
	
	public void initDatas()
	{
		probe = WifiProbe.init(cfgRoot, getActivity().getAssets());
		probe.setSystemListener(this);
		if (probe.isRequesting()) {
			MyToast.getInstance(getContext()).show("Processing previous request.",0);
			return;
		} else if (!probe.isConnected()) {
			MyToast.getInstance(getContext()).show("connecting to probe",0);
			//初始化
			probe.initialize();
		}
	}

	@OnClick({R.id.home_company , R.id.home_ultconnect  , R.id.home_userguide ,
			R.id.home_onlinevideo , R.id.home_callservice})
	public void onClick(View view)
	{
		int viewID = view.getId();
		switch (viewID)
		{
			case R.id.home_ultconnect:
			{
				if(NetworkUtls.iwWifiConnected(getActivity()) == true)
				{
					if (probe.isConnected()) {
						//跳转到扫描页面
						Intent intent = new Intent(getActivity(), ConnectUltrasonicActivity.class);
						startActivity(intent);
						return;
					}else{
						if (!probe.isRequesting()) {
							MyToast.getInstance(getContext()).show("connecting to probe",0);
							probe.initialize();
						}
					}

				}
				else
				{
					dialog = UiUtils.showDialog(getActivity(), "打开wifi,请连接后进入app!", "确定"  , new OnClickListener()
					{
						@Override
						public void onClick(View view)
						{
							dialog.dismiss();
							NetworkUtls.openWifiSetting(getActivity());
						}
					});
				}
				break;
			}

			case R.id.home_userguide:
			{
				ToastUtils.showNextDO(getActivity());
				break;
			}
			case R.id.home_company:
			{
//				Intent intent = new Intent(getActivity(), WebViewActivity.class);
//				intent.putExtra(WebViewActivity.WEB_URL, "");
//				intent.putExtra(WebViewActivity.ACTIVITY_TITLE, "公司简介");
				Intent intent = new Intent(getActivity(), CompanyproActivity.class);
				startActivity(intent);
				break;
			}

			case R.id.home_onlinevideo:
			{
				ToastUtils.showNextDO(getActivity());
				break;
			}

			case R.id.home_callservice:
			{
				ToastUtils.showNextDO(getActivity());
				break;
			}

			default:
				break;
		}
	}

	@Override
	public void onInitialized()
	{

	}

	@Override
	public void onInitializationError(String message)
	{
//		ToastUtils.showString(getActivity() , "设备初始化不成功,请多试几次!");
		return;
	}

	@Override
	public void onInitialingLowVoltageError(String message)
	{

	}

	@Override
	public void onSystemError(String message)
	{
//		ToastUtils.showString(getActivity() ,"发生不可预知状况，请重启超声设备重新连接!");
		return;
	}
}
