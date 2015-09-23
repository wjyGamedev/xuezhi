package com.xuezhi_client.work_flow.user_information_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;
import com.xuezhi_client.work_flow.user_information_page.ui.UserInformationAcitivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class UserInformationMsgHandler extends BaseUIMsgHandler
{
	public UserInformationMsgHandler(UserInformationAcitivity activity)
	{
		super(activity);
	}

	public void go2MainPage()
	{
		UserInformationAcitivity activity = (UserInformationAcitivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}
}
