/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.widget.fragmenttabhostex.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/15		WangJY		1.0.0		create
 */

package com.module.widget.fragment.fragmenttabhostex;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

public class FragmentTabHostEx extends FragmentTabHost
{
	public FragmentTabHostEx(Context context)
	{
		super(context);
	}

	public FragmentTabHostEx(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}


	public interface OnBeforeTabChangeListener
	{
		boolean onBeforeTabChanged(String tabId);
	}

	public interface OnAfterTabChangeListener
	{
		void onAfterTabChanged(String tabId);
	}

	public interface OnTabClickListener
	{
		void onTabClick(int index);
	}

	@Override
	public void onTabChanged(String tabId)
	{
		boolean bReturnFlag = false;
		if (m_onBeforeTabChangeListener != null)
		{
			bReturnFlag = m_onBeforeTabChangeListener.onBeforeTabChanged(tabId);
		}

		if (bReturnFlag == true)
		{
			super.onTabChanged(tabId);
		}

		if (m_onAfterTabChangeListener != null)
		{
			m_onAfterTabChangeListener.onAfterTabChanged(tabId);
		}
	}

	@Override
	public void setCurrentTab(int index)
	{
		if (m_onTabClickListener != null)
		{
			m_onTabClickListener.onTabClick(index);
		}

		super.setCurrentTab(index);
	}

	public void setOnBeforeTabChangeListener(OnBeforeTabChangeListener listener)
	{
		m_onBeforeTabChangeListener = listener;
	}

	public void setOnAfterTabChangeListener(OnAfterTabChangeListener listener)
	{
		m_onAfterTabChangeListener = listener;
	}

	public void setOnTabClickListener(OnTabClickListener listener)
	{
		m_onTabClickListener = listener;
	}

	private OnBeforeTabChangeListener m_onBeforeTabChangeListener = null;
	private OnAfterTabChangeListener  m_onAfterTabChangeListener  = null;
	private OnTabClickListener        m_onTabClickListener        = null;
}
