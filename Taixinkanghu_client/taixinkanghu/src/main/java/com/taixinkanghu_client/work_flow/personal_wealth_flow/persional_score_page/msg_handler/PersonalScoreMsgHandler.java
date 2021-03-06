/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.personal_wealth_flow.persional_score_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.personal_wealth_flow.persional_score_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;
import com.taixinkanghu_client.work_flow.personal_wealth_flow.persional_score_page.ui.PersonalScoreActivity;

public class PersonalScoreMsgHandler extends BaseUIMsgHandler
{
	public PersonalScoreMsgHandler(PersonalScoreActivity activity)
	{
		super(activity);
	}

	//01. 跳转到主页面
	public void go2MainPage()
	{
		PersonalScoreActivity activity = (PersonalScoreActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}
}
