package com.ys.pa200.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ys.pa200.R;
import com.ys.pa200.utils.StringUtils;

public class HintDialog extends Dialog implements OnClickListener
{
	private String content;
	private String btnStr;
	private String title;
	
	private android.view.View.OnClickListener listener = null;

	public HintDialog(Context context, String content)
	{
		super(context, R.style.dialog);
		this.content = content;
		listener = null;
	}

	public HintDialog(Context context, String title, String content, android.view.View.OnClickListener listener)
	{
		super(context, R.style.dialog);
		this.content = content;
		this.listener = listener;
		this.title = title;
	}

	public HintDialog(Context context, String title, String content, String btnStr, android.view.View.OnClickListener listener)
	{
		super(context, R.style.dialog);
		this.content = content;
		this.btnStr = btnStr;
		this.listener = listener;
		this.title = title;
	}

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hint_dialog);
		if (listener == null)
		{
			findViewById(R.id.dialog_sure).setOnClickListener(this);
		}
		else
		{
			findViewById(R.id.dialog_sure).setOnClickListener(this.listener);
		}
		
		if (StringUtils.isEmpty(title) == false)
		{
			((TextView) findViewById(R.id.dialog_title)).setText(title);
		}
		
		if (StringUtils.isEmpty(btnStr) == false)
		{
			((TextView) findViewById(R.id.dialog_sure)).setText(btnStr);
		}
		
		if (StringUtils.isEmpty(content) == false)
		{
			((TextView) findViewById(R.id.dialog_content)).setText(content);
		}
	}

	public void onClick(View view)
	{
		dismiss();
	}
}
