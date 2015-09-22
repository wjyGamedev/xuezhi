/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.nurse_order_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.nurse_order_flow.msg_handler;

import android.content.Intent;

import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.AnswerNurseOrderListEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.NurseOrderListHandler;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestNurseOrderCancelInWaitPayEvent;
import com.taixinkanghu_client.work_flow.comment_nurse_order_flow.ui.CommentNurseOrderActivity;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;
import com.taixinkanghu_client.work_flow.nurse_order_flow.BaseNurseOrderFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.nurse_order_flow.ui.NurseOrderActivity;
import com.taixinkanghu_client.work_flow.nurse_order_pay_in_wait_pay_flow.ui.NurseOrderPayInWaitPayActivity;
import com.taixinkanghu_client.work_flow.nurse_order_pay_more_flow.nurse_order_pay_more_page.ui.NurseOrderPayMoreActivity;

public class NurseOrderMsgHandler extends BaseNurseOrderFlowUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public NurseOrderMsgHandler(NurseOrderActivity activity)
	{
		super(activity);
	}

	//01. 跳转到订单详细信息页面
	public void go2NurseInfoAction(int nurseOrderID)
	{
		//跳转到详细订单页面。
		//		Intent intent = new Intent(nurseOrderActivity, NurseInfoActivity.class);
		//		nurseOrderActivity.startActivity(intent);
		return;

	}

	//02. 跳转到主页面
	public void go2MainAction()
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}

	//03. 接收nurse order list的返回消息
	public void onEventMainThread(AnswerNurseOrderListEvent event)
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;

		//01. 关闭等待对话框
		activity.stopWaitDialog();

		//02. update UI
		activity.updateContent();
		return;
	}

	//04. get/set selected nurse id
	public int getSelectedNurseOrderID()
	{
		return m_dNurseOrderFlow.getSelectedNurseOrderID();
	}

	public void setSelectedNurseOrderID(int selectedNurseOrderID)
	{
		m_dNurseOrderFlow.setSelectedNurseOrderID(selectedNurseOrderID);
		return;
	}

	//05. nurse order的业务流程
	//0501. 取消订单在待支付的情况下
	public void cancelOrderInWaitPayAction()
	{
		//弹出提示框
		NurseOrderActivity activity = (NurseOrderActivity)m_context;
		activity.popDialog_CancelOrderInWaitPay();
		return;
	}

	//0502. 在待支付情况下，跳转到支付页面
	public void readyPayOrderInWaitPayAction()
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;
		activity.startActivity(new Intent(activity, NurseOrderPayInWaitPayActivity.class));
		return;
	}

	//0503. 在订单处于完成的状态下，进行评论。
	public void commentAction()
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;
		activity.startActivity(new Intent(activity, CommentNurseOrderActivity.class));
		return;
	}

	//0504. 续订
	public void repeatOrderAction()
	{

	}

	//0505. 更换护理员
	public void changeNurseAction()
	{

	}

	//0506. 在服务状态下，取消订单
	public void cancelOrderInServiceAction()
	{

	}

	//0507. 补差价
	public void payMoreAction()
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;
		activity.startActivity(new Intent(activity, NurseOrderPayMoreActivity.class));
		return;
	}

	//06. 发送 nurse order cancel in wait_pay
	public void requestNurseOrderCancelInWaitPayAction()
	{
		RequestNurseOrderCancelInWaitPayEvent event = m_dNurseOrderFlow.constructRequestNurseOrderCancelInWaitPayEvent();
		NurseOrderListHandler.GetInstance().requestNurseOrderCancelInWaitPayAction(event);
		return;
	}
}
