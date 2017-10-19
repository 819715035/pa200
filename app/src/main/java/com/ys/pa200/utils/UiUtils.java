package com.ys.pa200.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.pa200.dialog.HintDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by zzz
 */
public class UiUtils {
    private UiUtils() {

    }

    /**
     * 获取Rescources
     *
     * @return
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * dp too px
     *
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        float density = getResources(context).getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * px too dp
     *
     * @param px
     * @return
     */
    public static int px2dp(Context context, int px) {
        float density = getResources(context).getDisplayMetrics().density;
        return (int) (px * density + 0.5f);
    }


    /**
     * 得到系统当前时间：时间格式 2016-08-15 12：00：00
     *
     * @return
     */
    public static CharSequence getCurrentTime() {
        return android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date());
    }

    /**
     * 移除view的父容器
     *
     * @param view
     */
    public static void removerParent(View view) {
        ViewParent parent = view.getParent();
        //当fragment销毁后通过反射调用无参construction创建新的fragment实例时，parent为空
        if (parent != null && parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
    }

    public static String getStringfromStream(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        try {
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static String getString(TextView v) {
        return v.getText().toString().trim();
    }

    public static String getString(EditText v) {
        return v.getText().toString().trim();
    }


    /**
     * 重新计算Listview高度，解决scrollview嵌套冲突
     *
     * @param listView
     */
    public static void setListViewHeightByChild(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 重新计算GridView高度，解决scrollview嵌套冲突
     * @param gridview
     */
    public static void setGridViewHeight(GridView gridview) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int numColumns= gridview.getNumColumns(); //5
        int totalHeight = 0;
        // 计算每一列的高度之和
        for (int i = 0; i < listAdapter.getCount(); i += numColumns) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        params.height = totalHeight;
        gridview.setLayoutParams(params);
    }
    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
    /**
     * 获取屏幕宽度
     * @param activity
     * @return
     */
    public static int  getDisplayWid(Activity activity) {

        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = screenWidth = display.getWidth();
        int screenHeight = screenHeight = display.getHeight();
        return screenWidth;
    }

    /**
     * 增加弹框
     * @param context
     * @param content
     * @param listener
     * @return
     */
    public static HintDialog showDialog(Context context, String content, String btnStr,
                                        View.OnClickListener listener)
    {
        if (context == null)
        {
            return null;
        }

        HintDialog dialog = new HintDialog(context, "提示", content, btnStr, listener);
        dialog.show();

        return dialog;
    }

    /**
     * 增加提示对话框
     * @param context
     */
    public static HintDialog showDialog(Context context, String content,
                                        View.OnClickListener listener)
    {
        return showDialog(context, content, "确定", listener);
    }

    /**
     * 增加提示对话框
     * @param context
     */
    public static void showDialog(Context context, String text)
    {
        if (context == null)
        {
            return;
        }
        HintDialog dialog = new HintDialog(context, text);
        showDialog(context, dialog);
    }

    /**
     * 设置EditText光标位置 为文本末端
     *
     * @param editText
     */
    public static void setEditTextCursor(EditText editText)
    {
        if (editText == null)
        {
            return;
        }

        editText.setSelection(editText.getText().toString().length());
    }

    /**
     * hide the keyBoard from the context
     * @param activity
     * @return
     */
    public static void hideKeyboard(Activity activity)
    {
        if (activity == null)
        {
            return;
        }
        if (activity.getCurrentFocus() != null &&
                activity.getCurrentFocus().getWindowToken() != null)
        {
            InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示键盘
     * @param context
     */
    public static void showKeyboard(Context context)
    {
        if (context == null)
        {
            return;
        }
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * show dialog
     * @param context
     * @param dialog
     */
    public static void showDialog(Context context, Dialog dialog)
    {
        if (context == null || dialog == null)
        {
            return;
        }

        if (!((Activity)context).isFinishing())
        {
            dialog.show();
        }
    }

    /**
     * dismiss dialog
     * @param context
     * @param dialog
     */
    public static void dismissDialog(Context context, Dialog dialog)
    {
        if (context == null || dialog == null || !dialog.isShowing())
        {
            return;
        }

        if (!((Activity)context).isFinishing())
        {
            dialog.dismiss();
        }
    }

    private static final SimpleDateFormat numFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 获得没有连接符的时间 时间格式19970101000000
     *
     * @return
     */
    public static String dateAndTime() {
        return numFormat.format(new Date());
    }

    /**
     * 格式化日期时间 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String formatDatetime(long date) {
        Date d = new Date(date);
        return datetimeFormat.format(d);
    }

}
