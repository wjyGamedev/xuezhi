package com.xuezhi_client.work_flow.user_information_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.user_information_page.msg_handler.UserInformationMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/23.
 */
public class UserInformationAcitivity extends Activity
{
	//widget
	private                                   HeaderCommon m_headerCommon            = null;
	private                                   BottomCommon m_bottomCommon            = null;
	@Bind (R.id.user_information_text_legion) TextView     m_userInformationLegionTV = null;

	//logical
	private UserInformationMsgHandler m_userInformationMsgHandler = new UserInformationMsgHandler(this);
	private ClickBottomBtn            m_clickBottomBtn            = new ClickBottomBtn();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_information);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.user_information_page_title_text);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.go_back_home_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);

		m_userInformationLegionTV.setText(R.string.user_information_content_text);
	}

	/**
	 * overrider func
	 */
	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_userInformationMsgHandler.go2MainPage();
		}
	}

}