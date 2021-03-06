package com.xuezhi_client.work_flow.user_protocal_page.ui;

import android.view.View;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.user_protocal_page.msg_handler.UserProtocalMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/9/23.
 */
public class UserProtocalActivity extends BaseActivity
{
	//widget
	private                                   HeaderCommon m_headerCommon            = null;
	private                                   BottomCommon m_bottomCommon            = null;
	@Bind (R.id.user_information_text_legion) TextView     m_userInformationLegionTV = null;

	//logical
	private UserProtocalMsgHandler m_userProtocalMsgHandler = new UserProtocalMsgHandler(this);
	private ClickBottomBtn         m_clickBottomBtn         = new ClickBottomBtn();

	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_user_protocal);
		return this;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
	}

	@Override
	public void onDestoryAction()
	{

	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.user_information_page_title_text);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);
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
			m_userProtocalMsgHandler.go2MainPage();
		}
	}

}