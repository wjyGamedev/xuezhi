/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.nurse_order_comment.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.comment_nurse_order_flow.msg_handler;

import android.content.Intent;

import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.AnswerCommentNurseOrderEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.NurseOrderListHandler;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestCommentNurseOrderEvent;
import com.taixinkanghu_client.data_module.register_account.data.DAccount;
import com.taixinkanghu_client.work_flow.comment_nurse_order_flow.ui.CommentNurseOrderActivity;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;
import com.taixinkanghu_client.work_flow.nurse_order_flow.BaseNurseOrderFlowUIMsgHandler;

public class CommentNurseOrderMsgHandler extends BaseNurseOrderFlowUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public CommentNurseOrderMsgHandler(CommentNurseOrderActivity activity)
	{
		super(activity);
	}

	//01. 跳转到主页面
	public void go2MainPage()
	{
		CommentNurseOrderActivity activity = (CommentNurseOrderActivity)m_context;

		//01. 清楚DNurseOrderFlow数据
		m_dNurseOrderFlow.clearupAll();

		//02. 跳转到主页面
		activity.startActivity(new Intent(activity, MainActivity.class));

		return;
	}

	//02 request comment nurse order action
	public void requestCommentNurseOrderAction()
	{
		CommentNurseOrderActivity activity = (CommentNurseOrderActivity)m_context;

		//01. comment nurse order
		RequestCommentNurseOrderEvent event = new RequestCommentNurseOrderEvent();

		String userID = DAccount.GetInstance().getId();
		event.setUserID(userID);

		int orderID = m_dNurseOrderFlow.getSelectedNurseOrderID();
		event.setOrderID(String.valueOf(orderID));

		EnumConfig.CommentLevel commentLevel = activity.getCommentLevel();
		event.setCommentLevel(commentLevel.getId());

		String commentContent = activity.getCommentContent();
		event.setCommentContent(commentContent);

		NurseOrderListHandler.GetInstance().requestCommentNurseOrderAction(event);

		return;

	}

	//03. comment nurse order 返回结果
	public void onEventMainThread(AnswerCommentNurseOrderEvent event)
	{
		backAction();
	}

	//04. 关闭当前页面
	public void backAction()
	{
		//01. 清楚DNurseOrderFlow数据
		m_dNurseOrderFlow.clearupAll();

		//02. 关闭当前activity
		CommentNurseOrderActivity activity = (CommentNurseOrderActivity)m_context;
		activity.finish();
	}
}
