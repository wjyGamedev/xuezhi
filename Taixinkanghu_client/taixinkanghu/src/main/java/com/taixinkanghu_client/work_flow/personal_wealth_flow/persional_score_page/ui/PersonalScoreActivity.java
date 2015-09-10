package com.taixinkanghu_client.work_flow.personal_wealth_flow.persional_score_page.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.work_flow.personal_wealth_flow.persional_score_page.msg_handler.PersonalScoreMsgHandler;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/7/16.
 */
public class PersonalScoreActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;

	//logical
	private PersonalScoreMsgHandler m_personalScoreMsgHandler = new PersonalScoreMsgHandler(this);
	private FragmentManager         m_fragmentManager         = getFragmentManager();
	private ClickBottomCommon        m_clickBottomCommon        = new ClickBottomCommon();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_score);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * override func
	 */
	class ClickBottomCommon implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_personalScoreMsgHandler.go2MainPage();
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		//01. widget
		m_headerCommon = (HeaderCommon)m_fragmentManager.findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.personal_score_title);

		m_bottomCommon = (BottomCommon)m_fragmentManager.findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.content_main_page);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomCommon);
	}
}
