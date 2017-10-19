package com.ys.pa200.web;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.ys.pa200.R;
import com.ys.pa200.ui.baseui.BaseActivity;
import com.ys.pa200.weight.HeadColumnView;
import com.ys.pa200.weight.ProgressWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * WebView的activity
 */
public class WebViewActivity extends BaseActivity
{

	/** webView控件需要加载的链接 */
	private String mUrl;
	/** 头部导航栏title */
	private String mActionBarTitle = "";
	private boolean mIsRefresh;
	public final static String WEB_URL = "url";
	public final static String ACTIVITY_TITLE = "title";

	@BindView(R.id.adv_jump)
	ProgressWebView webView;

	@BindView(R.id.webview_headColumnView)
	HeadColumnView webview_headColumnView;

	public void setIsRefresh(boolean isRefresh)
	{
		this.mIsRefresh = isRefresh;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		init();
		setStatusBar();
	}

	private void init()
	{
		ButterKnife.bind(this);
		Bundle bundle = this.getIntent().getExtras();
		mUrl = bundle.getString(WEB_URL);
		mActionBarTitle = bundle.getString(ACTIVITY_TITLE);


		if (TextUtils.isEmpty(mActionBarTitle))
		{
			mActionBarTitle = "杜曼医疗";
		}

		webview_headColumnView.setTabTitle(mActionBarTitle);

		loadPageWithUrl();
		setView();
		onClick();
	}

	/**
	 * 载入指定URL
	 */
	private void loadPageWithUrl()
	{
		webView.loadUrl(mUrl);
	}

	@SuppressLint("NewApi")
	protected void onResume()
	{
		super.onResume();

		// 刷新
		if (mIsRefresh == true)
		{
			loadPageWithUrl();
			mIsRefresh = false;
		}
	}


	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{

			if (webView.canGoBack())
			{
				// 返回前一个页面
				webView.goBack();
			}
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}

	public void onClick()
	{
		webview_headColumnView.setLeftBtnClick(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				WebViewActivity.this.finish();
			}
		});
//		int viewID = view.getId();
//		switch (viewID)
//		{
//			case R.id.tab_actionbar_left:
//			{	// 返回按钮
//				handleClickBack();
//				break;
//			}
//
//			case R.id.tab_actionbar_close:
//			{	// 关闭按钮
//				handleClickClose();
//				break;
//			}
//
//			case R.id.tab_actionbar_rightBtn:
//			{	// 右上角按钮
//				handleRightBtnClick();
//				break;
//			}
//
//			default:
//				break;
//		}
	}

	private void setView()
	{
		webView.setWebChromeClient(new WebChromeClient()
		{
			// The undocumented magic method override
			// Eclipse will swear at you if you try to put @Override here
			// For Android 3.0+
			public void openFileChooser(ValueCallback<Uri> uploadMsg)
			{
			}

			// For Android 3.0+
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void openFileChooser( ValueCallback uploadMsg, String acceptType )
			{
			}

			// For Android 4.1
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
			{
			}

			/**
			 * 处理进度条更新
			 * @param view
			 * @param newProgress
			 */
			@Override
			public void onProgressChanged(WebView view, int newProgress)
			{
				webView.onProgressChanged(view, newProgress);
				super.onProgressChanged(view, newProgress);
			}

			/**
			 * 处理网页icon回调
			 * @param view
			 * @param icon
			 */
			@Override
			public void onReceivedIcon(WebView view, Bitmap icon)
			{
				super.onReceivedIcon(view, icon);
			}

			/**
			 * 处理定位权限提示
			 * @param origin
			 * @param callback
			 */
			@Override
			public void onGeolocationPermissionsShowPrompt(String origin,
														   GeolocationPermissions.Callback callback)
			{
				callback.invoke(origin, true, false);
				super.onGeolocationPermissionsShowPrompt(origin, callback);
			}
		});
	}
}
