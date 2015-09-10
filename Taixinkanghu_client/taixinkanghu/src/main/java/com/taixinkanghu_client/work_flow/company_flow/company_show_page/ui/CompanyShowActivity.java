/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.company_show.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/27		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.company_flow.company_show_page.ui;

import android.os.Bundle;
import android.view.View;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.work_flow.company_flow.company_show_page.msg_handler.CompanyShowMsgHandler;

import butterknife.ButterKnife;

public class CompanyShowActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;

	//logical
	private CompanyShowMsgHandler m_companyShowMsgHandler = new CompanyShowMsgHandler(this);
	private ClickBottomBtn        m_clickBottomBtn        = new ClickBottomBtn();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_show);
		ButterKnife.bind(this);

		init();

	}

	/**
	 * overrider func
	 */
	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_companyShowMsgHandler.go2MainPage();
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.company_show_title);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.company_show_bottom);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);

	}

}
