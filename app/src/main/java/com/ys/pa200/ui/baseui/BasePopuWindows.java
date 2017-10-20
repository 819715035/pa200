package com.ys.pa200.ui.baseui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.ys.pa200.R;

public class BasePopuWindows {
	public static final int TYPE_WRAP_CONTENT = 0, TYPE_MATCH_PARENT = 1,TYPE_HEIGHT_MATCH_PARENT = 2;
	public PopupWindow mPopupWindow;
	private View popupView;
	private Context context;
	private Boolean isCancelable;
	/**
	 * 
	 * @param context
	 * @param type 选择弹出内容类型，TYPE_WRAP_CONTENT，TYPE_MATCH_PARENT，TYPE_HEIGHT_MATCH_PARENT
	 * @param popupView 弹出的布局
	 * @param isCancelable 设置是否点击其他地方消失
	 */
	public BasePopuWindows(Context context, int type, View popupView, Boolean isCancelable) {
		this.popupView = popupView;
		this.context = context;
		this.isCancelable = isCancelable;
		initPopupWindow(context,type, popupView, isCancelable);
	}

	/**
	 * 相对控件右上方向弹出
	 * @param v    相对控件弹出
	 * @param xoff 左负右正
	 * @param yoff 上负下正
	 */
	public void showAsRightTop(View v,int xoff,int yoff){
		initPopupWindow(context,TYPE_WRAP_CONTENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationUpPopup);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		int height = popupView.getMeasuredWidth()+v.getHeight();
		mPopupWindow.showAsDropDown(v, v.getWidth() + xoff, -height + yoff);
	}
	/**
	 * 相对控件右下方向弹出
	 * @param v    相对控件弹出
	 * @param xoff 左负右正
	 * @param yoff 上负下正
	 */
	public void showAsRightBottom(View v,int xoff,int yoff){
		initPopupWindow(context,TYPE_WRAP_CONTENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationUpPopup);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.showAsDropDown(v, v.getWidth() + xoff, yoff);
	}

	/**
	 * 相对控件右中方向弹出
	 * @param v    相对控件弹出
	 * @param xoff 左负右正
	 * @param yoff 上负下正
	 */
	public void showAsRightMiddle(View v,int xoff,int yoff){
		initPopupWindow(context,TYPE_WRAP_CONTENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationUpPopup);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		int height = popupView.getHeight()+v.getHeight();
		mPopupWindow.showAsDropDown(v, v.getWidth() + xoff, -height/2-yoff);
	}

	/**
	 * 相对控件左下方向弹出
	 * @param v    相对控件弹出
	 * @param xoff 左负右正
	 * @param yoff 上负下正
	 */
	public void showAsLeftDown(View v,int xoff,int yoff){
		initPopupWindow(context,TYPE_WRAP_CONTENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationFromButtom);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.showAsDropDown(v, -popupView.getMeasuredWidth()-xoff, yoff);
	}

	/**
	 * 相对控件左中方向弹出
	 * @param v    相对控件弹出
	 * @param xoff 左负右正
	 * @param yoff 上负下正
	 */
	public void showAsLeftMiddle(View v,int xoff,int yoff){
		initPopupWindow(context,TYPE_WRAP_CONTENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationFromButtom);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		int height = popupView.getMeasuredWidth()+v.getHeight();
		mPopupWindow.showAsDropDown(v, -popupView.getMeasuredWidth()-xoff, -height/2+yoff);
	}

	/**
	 * 相对控件左上方向弹出
	 * @param v    相对控件弹出
	 * @param xoff 左负右正
	 * @param yoff 上负下正
	 */
	public void showAsLeftTop(View v,int xoff,int yoff){
		initPopupWindow(context,TYPE_WRAP_CONTENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationFromButtom);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		int height = popupView.getMeasuredWidth()+v.getHeight();
		mPopupWindow.showAsDropDown(v, -popupView.getMeasuredWidth()-xoff, -height+yoff);
	}

	/**
	 * 相对控件中上方向弹出
	 * @param v    相对控件弹出
	 * @param xoff 左负右正
	 * @param yoff 上负下正
	 */
	public void showAsMiddleTop(View v,int xoff,int yoff){
		initPopupWindow(context,TYPE_WRAP_CONTENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationFromButtom);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		int height = popupView.getMeasuredWidth()+v.getHeight();
		int width = Math.abs(popupView.getMeasuredWidth()-v.getWidth());
		mPopupWindow.showAsDropDown(v, -width/2-xoff, -height+yoff);
	}
	/**
	 * 相对控件中下方向弹出
	 * @param v    相对控件弹出
	 * @param xoff 左负右正
	 * @param yoff 上负下正
	 */
	public void showAsMiddleButtom(View v,int xoff,int yoff){
		initPopupWindow(context,TYPE_WRAP_CONTENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationFromButtom);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		int width = Math.abs(popupView.getMeasuredWidth()-v.getWidth());
		mPopupWindow.showAsDropDown(v, -width/2-xoff, yoff);
	}
	/**
	 * 窗口顶部方向弹出
	 */
	public void showAsFromTop(View v){
		initPopupWindow(context,TYPE_MATCH_PARENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationFromTop);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.showAtLocation(v, Gravity.LEFT | Gravity.TOP, 0, getStatusBarHeight());
	}
	/**
	 * 窗口底部部方向弹出
	 */
	public void showAsFromBottom(View v){
		initPopupWindow(context,TYPE_MATCH_PARENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationFromButtom);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.showAtLocation(v, Gravity.LEFT | Gravity.BOTTOM, 0, 0);
	}
	/**
	 * 窗口左边方向弹出
	 */
	public void showAsFromLeft(View v){
		initPopupWindow(context,TYPE_HEIGHT_MATCH_PARENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationFromButtom);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.showAtLocation(v, Gravity.LEFT|Gravity.TOP, 0, 0);
	}
	
	/**
	 * 窗口右边边方向弹出
	 */
	public void showAsFromRight(View v){
		initPopupWindow(context,TYPE_HEIGHT_MATCH_PARENT,popupView,isCancelable);
		mPopupWindow.setAnimationStyle(R.style.AnimationFromButtom);
		popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.showAtLocation(v, Gravity.RIGHT|Gravity.TOP, 0, 0);
	}

	public void initPopupWindow(Context context,int type,View popupView,Boolean isCancelable) {
		if (type == TYPE_WRAP_CONTENT) {
			mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}else if (type == TYPE_MATCH_PARENT) {
			mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}else if (type == TYPE_HEIGHT_MATCH_PARENT) {
			mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		}
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		setCancelable(isCancelable);
	}

	public void setCancelable(boolean isCancelable) {
		if (isCancelable) {
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setFocusable(true);
		}else {
			mPopupWindow.setOutsideTouchable(false);
			mPopupWindow.setFocusable(false);
		}
	}

	public void dismiss() {
		if (mPopupWindow.isShowing()){
			mPopupWindow.dismiss();
		}
	}

	private int getStatusBarHeight() {
		return Math.round(25 * Resources.getSystem().getDisplayMetrics().density);
	}
}
