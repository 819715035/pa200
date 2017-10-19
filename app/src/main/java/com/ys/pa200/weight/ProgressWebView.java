package com.ys.pa200.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ys.pa200.R;

/**
 * 带进度条的WebView
 * @author zhangzhizhi
 */
@SuppressLint("SetJavaScriptEnabled")
public class ProgressWebView extends WebView
{
	private ProgressBar progressbar;

	@SuppressWarnings("deprecation")
	public ProgressWebView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 10, 0, 0));
		Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_horizontal);
		progressbar.setProgressDrawable(drawable);
		addView(progressbar);

		// webView: 类WebView的实例
		WebSettings webSettings = getSettings();
		webSettings.setBuiltInZoomControls(false);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 开启DomStorage缓存
		webSettings.setDomStorageEnabled(true);
		// 就是这句
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// 支持js脚本
		webSettings.setJavaScriptEnabled(true);
		// 缓存
		webSettings.setAppCacheEnabled(true);
		// 设置缓存模式
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		// 启用数据库  
		webSettings.setDatabaseEnabled(true);    
		// 设置定位的数据库路径  
		String dir = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
		webSettings.setGeolocationDatabasePath(dir);   
		// 启用地理定位  
		webSettings.setGeolocationEnabled(true);  
		
//		// 提供的传入js的方法
//		this.addJavascriptInterface(new JavaScriptHelper(context), "JavaScriptHelper");
//
//		setWebViewClient(new MyWebClient(context));
	}

	/**
	 * 处理progress回调显示
	 * @param view
	 * @param newProgress
     */
	public void onProgressChanged(WebView view, int newProgress)
	{
		if (newProgress == 100)
		{
			progressbar.setVisibility(GONE);
		}
		else
		{
			if (progressbar.getVisibility() == GONE)
			{
				progressbar.setVisibility(VISIBLE);
			}
			progressbar.setProgress(newProgress);
		}
	}

	/**
	 * 隐藏加载时的进度条
	 */
	public void removeProgressBar()
	{
		if (progressbar != null)
		{
			removeView(progressbar);
		}
	}

	@SuppressWarnings("deprecation")
	protected void onScrollChanged(int l, int t, int oldl, int oldt) 
	{
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}
}