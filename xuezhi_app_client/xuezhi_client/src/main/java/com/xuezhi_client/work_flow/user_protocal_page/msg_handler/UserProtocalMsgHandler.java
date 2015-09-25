package com.xuezhi_client.work_flow.user_protocal_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;
import com.xuezhi_client.work_flow.user_protocal_page.ui.UserProtocalActivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class UserProtocalMsgHandler extends BaseUIMsgHandler
{
	public UserProtocalMsgHandler(UserProtocalActivity activity)
	{
		super(activity);
	}

	public void go2MainPage()
	{
		UserProtocalActivity activity = (UserProtocalActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}
}
