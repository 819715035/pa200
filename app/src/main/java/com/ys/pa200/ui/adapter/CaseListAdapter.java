package com.ys.pa200.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ys.pa200.R;
import com.ys.pa200.model.CaseModel;
import com.ys.pa200.utils.StringUtils;

import java.util.List;

/**
 *  adapter
 */
public class CaseListAdapter extends BaseAdapter
{
	private Context context;
	private LayoutInflater inflater;
	private List<CaseModel> caseModels;

	public CaseListAdapter(Context context, List<CaseModel> caseModels)
	{
		super();
		if (context == null)
		{
			return;
		}
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.caseModels = caseModels;
	}

	/**
	 * 更新列表状态
     */
	public void changeStatue(List<CaseModel> caseModels)
	{
		this.caseModels = caseModels;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		if (caseModels == null || caseModels.size() <= 0)
		{
			return 0;
		}
		return caseModels.size();
	}

	@Override
	public Object getItem(int position)
	{
		return caseModels.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final CaseModel refundProgress = caseModels.get(position);
		ViewHolder viewHolder;
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.case_list_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.case_list_item_text = (TextView) convertView.findViewById(R.id.case_list_item_text);
			viewHolder.case_list_item_time = (TextView) convertView.findViewById(R.id.case_list_item_time);
//			viewHolder.refund_progress_falseimage = (ImageView) convertView.findViewById(R.id.refund_progress_falseimage);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.case_list_item_text.setText(StringUtils.getStringText(refundProgress.getName()));
		viewHolder.case_list_item_time.setText(StringUtils.getStringText(refundProgress.getCreatedAt()));
//		if(position == 0)
//		{
//			viewHolder.refund_progress_falseimage.setImageResource(R.drawable.icon_refund_progress_true);
//			viewHolder.refund_progress_text.setTextColor(0xff28bc93);
//			viewHolder.refund_progress_linearone.setVisibility(View.GONE);
//			viewHolder.refund_progress_lineartwo.setVisibility(View.VISIBLE);
//		}
//		else if(position == refundProgresses.size() - 1)
//		{
//			viewHolder.refund_progress_linearone.setVisibility(View.VISIBLE);
//			viewHolder.refund_progress_lineartwo.setVisibility(View.GONE);
//		}
//		else
//		{
//			viewHolder.refund_progress_falseimage.setImageResource(R.drawable.icon_refund_progress_false);
//			viewHolder.refund_progress_text.setTextColor(0xff999999);
//			viewHolder.refund_progress_linearone.setVisibility(View.VISIBLE);
//			viewHolder.refund_progress_lineartwo.setVisibility(View.VISIBLE);
//		}
		return convertView;
	}



	private static class ViewHolder
	{
		private TextView case_list_item_text;
		private TextView case_list_item_time;
//		private ImageView refund_progress_falseimage;
	}
}
