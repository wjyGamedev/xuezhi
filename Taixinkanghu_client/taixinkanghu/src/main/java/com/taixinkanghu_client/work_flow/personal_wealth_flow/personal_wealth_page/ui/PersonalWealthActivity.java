package com.taixinkanghu_client.work_flow.personal_wealth_flow.personal_wealth_page.ui;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.work_flow.personal_wealth_flow.personal_wealth_page.msg_handler.PersonalWealthMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2015/7/16.
 */
public class PersonalWealthActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.persional_score_region_ll) LinearLayout m_persionalScoreRegionLL = null;

	private BottomCommon m_bottomCommon = null;

	//logcal
	private PersonalWealthMsgHandler m_personalWealthMsgHandler = new PersonalWealthMsgHandler(this);
	private FragmentManager          m_fragmentManager          = getFragmentManager();
	private ClickBottomCommon        m_clickBottomCommon        = new ClickBottomCommon();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_wealth);
		ButterKnife.bind(this);

		init();

	}

	/**
	 * widget event
	 */
	@OnClick (R.id.persional_score_region_ll)
	public void clickPersionalScoreRegion()
	{
		m_personalWealthMsgHandler.go2PersonalScorePage();
	}

	/**
	 * override func
	 */
	class ClickBottomCommon implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_personalWealthMsgHandler.go2MainPage();
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		//01. widget
		m_headerCommon = (HeaderCommon)m_fragmentManager.findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.personal_wealth_title);

		m_bottomCommon = (BottomCommon)m_fragmentManager.findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.personal_wealth_bottom_content);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomCommon);
	}
}
