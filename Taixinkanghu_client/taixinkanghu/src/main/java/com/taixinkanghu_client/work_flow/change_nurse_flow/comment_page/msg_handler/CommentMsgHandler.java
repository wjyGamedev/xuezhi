/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.comment_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/9		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.change_nurse_flow.comment_page.msg_handler;

import android.content.Intent;

import com.taixinkanghu_client.work_flow.change_nurse_flow.BaseChangeNurseFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.change_nurse_flow.comment_page.ui.CommentActivity;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;

public class CommentMsgHandler extends BaseChangeNurseFlowUIMsgHandler
{
	public CommentMsgHandler(CommentActivity activity)
	{
		super(activity);
	}

	//01. 返回主页面
	public void go2MainPage()
	{
		CommentActivity commentActivity = (CommentActivity)m_context;
		commentActivity.startActivity(new Intent(commentActivity, MainActivity.class));

		m_dChangeNurseFlow.clearupAll();
		return;
	}


}
