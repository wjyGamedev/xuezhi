/**
 * Copyright (c) 213Team
 *
 * @className : com.module.widget.fragment.select_fragment_list.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/20		WangJY		1.0.0		create
 */

package com.module.widget.fragment.select_list_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.widget.fragment.config.FragmentConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class SelectListFragment extends Fragment implements View.OnTouchListener
{
	//widget
	@Bind (R.id.header_bg_ll) protected      LinearLayout m_headerBgLL        = null;
	@Bind (R.id.content_tips) protected      TextView     m_contentTipsTV     = null;
	@Bind (R.id.more_content_tips) protected TextView     m_moreContentTipsTV = null;
	@Bind (R.id.widget_region_gl) protected  GridLayout   m_gridLayout        = null;
	@Bind (R.id.bottom_bg_ll) protected      LinearLayout m_bottomBgLL        = null;

	protected LayoutInflater    m_layoutInflater = null;
	protected View              m_view           = null;
	protected ArrayList<Button> m_buttons        = new ArrayList<>();

	protected int m_maxColunms = FragmentConfig.SELECT_LIST_FRAGMENT_MAX_COLUMN;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_layoutInflater = inflater;
		m_view = m_layoutInflater.inflate(R.layout.fragment_select_list, container, false);
		ButterKnife.bind(this, m_view);

		init();
		initListeners();
		return m_view;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		updateContent();
	}

	//防止点击穿透
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		return true;
	}

	@OnClick (R.id.header_bg_ll)
	public void clickHeaderRegion()
	{
		impClickHeaderRegion();
		return;
	}

	@OnClick (R.id.bottom_bg_ll)
	public void clickBottomRegion()
	{
		impClickBottomRegion();
		return;
	}

	/**
	 * inner func
	 */
	private void initListeners()
	{
		m_view.setOnTouchListener(this);
		initWidgetListener();
	}

	/**
	 * outter func
	 */
	public int getMaxColunms()
	{
		return m_maxColunms;
	}

	public void setMaxColunms(int maxColunms)
	{
		m_maxColunms = maxColunms;
	}

	/**
	 * outter override func
	 */
	public abstract void init();

	public abstract void initWidgetListener();

	public abstract void updateContent();

	public abstract void impClickHeaderRegion();

	public abstract void impClickBottomRegion();

}
