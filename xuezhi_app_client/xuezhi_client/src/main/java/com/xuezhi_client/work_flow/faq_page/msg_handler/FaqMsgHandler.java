package com.xuezhi_client.work_flow.faq_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.work_flow.faq_page.ui.FaqActivity;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class FaqMsgHandler extends BaseUIMsgHandler
{
	public FaqMsgHandler(FaqActivity activity)
	{
		super(activity);
	}

	public void go2MainPage()
	{
		FaqActivity activity = (FaqActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}
}
