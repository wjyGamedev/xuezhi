/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.widget.tab_item.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/22		WangJY		1.0.0		create
 */

package com.module.widget.fragmenttabhostex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.module.exception.RuntimeExceptions.WidgetRTException;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;

public class TabItem
{

	public TabItem(Context context)
	{
		m_context = context;
		init();
	}

	private void init()
	{
		if (m_context == null)
		{
			throw new WidgetRTException("m_context == null");
		}

		m_view = LayoutInflater.from(getContext()).inflate(R.layout.main_tab_item, null);
		m_textView = (TextView)m_view.findViewById(R.id.main_tab_textview);
		m_imageView = (ImageView)m_view.findViewById(R.id.main_tab_imgview);

		m_textView.setText(R.string.default_text);
		m_imageView.setImageResource(R.drawable.default_tab_item);
	}

	public void setText(String strText)
	{
		m_textView.setText(strText);
	}

	public void setImageResourceByID(int iID)
	{
		m_imageView.setImageResource(iID);
	}

	public final Context getContext()
	{
		return m_context;
	}

	public View getView()
	{
		return m_view;
	}

	private Context   m_context   = null;
	private View      m_view      = null;
	private TextView  m_textView  = null;
	private ImageView m_imageView = null;
}
