package com.xuezhi_client.work_flow.calendar_flow.calender_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.work_flow.calendar_flow.calender_page.ui.CalenderActivity;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class CalenderMsgHandler extends BaseUIMsgHandler
{
	public CalenderMsgHandler(CalenderActivity activity)
	{
		super(activity);
	}

	public void go2MainPage()
	{
		CalenderActivity activity = (CalenderActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}
}
