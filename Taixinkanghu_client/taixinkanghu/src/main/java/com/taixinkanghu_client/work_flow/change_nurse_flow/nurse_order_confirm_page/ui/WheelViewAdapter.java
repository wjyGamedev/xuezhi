/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.select_date.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/12		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_order_confirm_page.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.module.widget.wheelview.TosGallery;

import java.util.ArrayList;

public class WheelViewAdapter extends BaseAdapter
{
	private ArrayList<SelectDateFragment.DateEle> m_data = null;
	private Context m_context = null;
	private int m_width = 0;
	private int m_height = 0;


	public WheelViewAdapter(Context content, ArrayList<SelectDateFragment.DateEle> data)
	{
		m_context = content;
		m_data = data;
		m_width = ViewGroup.LayoutParams.MATCH_PARENT;
		float scale = m_context.getResources().getDisplayMetrics().density;
		m_height = (int) (28 * scale + 0.5f);
	}

	public void setData(ArrayList<SelectDateFragment.DateEle> data) {
		m_data = data;
		notifyDataSetChanged();
	}

	public void setItemSize(int width, int height) {
		m_width = width;
		m_height = height;
	}

	@Override
	public int getCount()
	{
		return (null != m_data) ? m_data.size() : 0;
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TextView textView = null;

		if (null == convertView)
		{
			convertView = new TextView(m_context);
			convertView.setLayoutParams(new TosGallery.LayoutParams(m_width, m_height));
			textView = (TextView)convertView;
			textView.setGravity(Gravity.CENTER);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
			textView.setTextColor(Color.BLACK);
		}

		if (null == textView)
		{
			textView = (TextView)convertView;
		}

		if (position >= m_data.size())
		{
			return null;
		}

		SelectDateFragment.DateEle info = m_data.get(position);
		textView.setText(info.m_content);
		textView.setTextColor(info.m_color);

		return textView;
	}
}
