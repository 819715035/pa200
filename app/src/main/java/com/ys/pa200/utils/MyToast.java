package com.ys.pa200.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 不循环提示的Toast
 * @author zzz
 */
public class MyToast
{
	private static Context mContext;
	private Toast mToast;
	private static MyToast myToast;

	public MyToast(Context context)
	{
		mContext = context;

		mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		//居中显示
		mToast.setGravity(17, 0, -30); 
	}
	
	public static MyToast getInstance(Context context)
	{
		mContext = context;
		if(myToast == null)
		{
			myToast = new MyToast(mContext);
		}
		return myToast;
	}


	public void show(CharSequence s, int duration)
	{
		mToast.setDuration(duration);
		mToast.setText(s);
		mToast.show();
	}

	public void cancel() 
	{
		mToast.cancel();
	}
}

