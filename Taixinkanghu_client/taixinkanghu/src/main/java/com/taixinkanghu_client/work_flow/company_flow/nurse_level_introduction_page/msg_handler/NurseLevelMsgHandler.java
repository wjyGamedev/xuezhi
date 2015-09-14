/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.company_flow.nurse_level_introduction_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/11		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.company_flow.nurse_level_introduction_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.taixinkanghu_client.work_flow.company_flow.nurse_level_introduction_page.ui.NurseLevelActivity;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;

public class NurseLevelMsgHandler extends BaseUIMsgHandler
{
	public NurseLevelMsgHandler(NurseLevelActivity activity)
	{
		super(activity);
	}

	public void go2MainPage()
	{
		NurseLevelActivity activity = (NurseLevelActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}
}
