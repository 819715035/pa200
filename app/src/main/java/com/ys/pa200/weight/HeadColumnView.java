package com.ys.pa200.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.pa200.R;

/**
 * Created by zzz
 * 顶部导航栏
 */
public class HeadColumnView extends RelativeLayout {
    private Context context;
    private TextView title,textDetails;
    private ImageButton ibBack;
    private boolean showLeftBtn;
    private String StrTitle;
    public HeadColumnView(Context context) {
        super(context);
        this.context = context;
    }

    public HeadColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
                init();
        getArrrs(attrs);
    }

    /**
     * 初始化空间
     */
    private void init(){
        View viewGroup = LayoutInflater.from(context).inflate(R.layout.view_head_blue_bg, this,true);
        title= (TextView) viewGroup.findViewById(R.id.title_title);
        ibBack= (ImageButton) viewGroup.findViewById(R.id.title_back);
        textDetails= (TextView) viewGroup.findViewById(R.id.text_details);
    }

    /**
     * 获取属性
     * @param attrs
     */
    private void getArrrs(AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadColumnView);
        showLeftBtn=typedArray.getBoolean(R.styleable.HeadColumnView_is_show_left_btn,true);
        StrTitle = typedArray.getString(R.styleable.HeadColumnView_title_context);
        boolean isShowRightText=typedArray.getBoolean(R.styleable.HeadColumnView_is_show_right_text,false);

        title.setText(StrTitle);
        if(!showLeftBtn){
            ibBack.setVisibility(INVISIBLE);
        }
        if(isShowRightText){
            textDetails.setVisibility(VISIBLE);
        }
        typedArray.recycle();
    }

    public void setTabTitle(String tabtitle)
    {
        title.setText(tabtitle);
    }
    /**
     * 设置左边ImageButton 的监听
     * @param onClickListener
     */
    public void setLeftBtnClick( OnClickListener onClickListener){
        ibBack.setOnClickListener(onClickListener);
    }

    /**
     * 设置右边文本的监听
     * @param onClickListener
     */
    public void setRightTextClick( OnClickListener onClickListener){
        textDetails.setOnClickListener(onClickListener);
    }


}
