package com.ys.pa200.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ys.pa200.R;
import com.ys.pa200.bean.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/25.
 */

public class SearchTextviewAdapter extends BaseAdapter implements Filterable
{

    private ArrayFilter mFilter;
    private List<Patient> mList;
    private ArrayList<Patient> mUnfilteredData; //过滤前的数据
    private Context context;
    private String keyworld;
    public SearchTextviewAdapter(ArrayList<Patient> mUnfilteredData, Context context)
    {
        this.mUnfilteredData = mUnfilteredData;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View converView, ViewGroup viewGroup)
    {
        Holder holder;
        if (converView==null){
            holder = new Holder();
            converView = View.inflate(context, R.layout.item_search,null);
            holder.tv = (TextView) converView.findViewById(R.id.search_tv);
            converView.setTag(holder);
        }else {
            holder = (Holder) converView.getTag();
        }
        String word = mList.get(i).getNumber().replaceAll(keyworld,"<font color='red'>"+keyworld+"</font>");
        holder.tv.setText(Html.fromHtml(word));
        return converView;
    }

    class Holder{
        private TextView tv;
    }

    @Override
    public Filter getFilter()
    {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    class ArrayFilter extends Filter{

        //过滤
        @Override
        protected FilterResults performFiltering(CharSequence prefix)
        {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<Patient>(mList);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<Patient> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();
                ArrayList<Patient> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();
                ArrayList<Patient> newValues = new ArrayList<Patient>(count);
                for (int i = 0; i < count; i++) {
                    Patient patient = unfilteredValues.get(i);
                    String pc = unfilteredValues.get(i).getNumber();
                    if (pc.contains(prefixString)){
                        keyworld = prefixString;
                        newValues.add(patient);
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        //显示过滤结果
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults)
        {
            //noinspection unchecked
            mList = (List<Patient>) filterResults.values;
            if (filterResults.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
