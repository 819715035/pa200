package com.ys.pa200.ui.homeui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.ys.pa200.R;
import com.ys.pa200.model.CaseListModel;
import com.ys.pa200.model.CaseModel;
import com.ys.pa200.ui.adapter.CaseListAdapter;
import com.ys.pa200.ui.baseui.BaseActivity;
import com.ys.pa200.utils.MyToast;
import com.ys.pa200.utils.StringUtils;
import com.ys.pa200.weight.HeadColumnView;

import org.json.JSONArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 *
 */
public class CaseListActivity extends BaseActivity
{
	@BindView(R.id.caselist_headview)
	HeadColumnView caselist_headview;

	@BindView(R.id.caselist_list)
	ListView caselist_list;

	private CaseListAdapter caseListAdapter;
	private List<CaseModel> caseModels;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_caselist);
		init();
		setStatusBar();
	}

	private void init()
	{
		ButterKnife.bind(this);
		onClick();
		queryData();
	}
	private void onClick()
	{
		caselist_headview.setLeftBtnClick(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				CaseListActivity.this.finish();
			}
		});
	}

	/**
	 * 查询数据
	 */
	public void queryData(){
		BmobQuery<CaseModel> query = new BmobQuery<>();
		query.setLimit(10);
		//执行查询方法
		query.findObjects(new FindListener<CaseModel>() {
			@Override
			public void done(List<CaseModel> object, BmobException e) {
				if(e==null)
				{
					caseModels = object;
					showLitDatas();
				}
				else
				{
					Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
				}
			}
		});
	}

	/** 显示列表数据 */
	private void showLitDatas()
	{
		if (caseListAdapter == null)
		{
			caseListAdapter = new CaseListAdapter(this, caseModels);
			caselist_list.setAdapter(caseListAdapter);
		}
		else
		{
			caseListAdapter.changeStatue(caseModels);
		}
	}


}
