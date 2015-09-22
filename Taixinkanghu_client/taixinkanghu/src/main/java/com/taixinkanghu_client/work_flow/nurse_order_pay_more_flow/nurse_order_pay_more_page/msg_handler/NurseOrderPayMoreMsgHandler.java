/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.nurse_order_pay_more_flow.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/22		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.nurse_order_pay_more_flow.nurse_order_pay_more_page.msg_handler;

import android.content.Intent;

import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.AnswerNurseOrderPayMoreEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.NurseOrderListHandler;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestNurseOrderPayMoreEvent;
import com.taixinkanghu_client.work_flow.nurse_order_flow.BaseNurseOrderFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.nurse_order_pay_more_flow.nurse_orde_pay_in_pay_more_page.ui.NurseOrderPayInPayMoreActivity;
import com.taixinkanghu_client.work_flow.nurse_order_pay_more_flow.nurse_order_pay_more_page.ui.NurseOrderPayMoreActivity;

public class NurseOrderPayMoreMsgHandler extends BaseNurseOrderFlowUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public NurseOrderPayMoreMsgHandler(NurseOrderPayMoreActivity activity)
	{
		super(activity);
	}

	public int getSelectedNurseOrderID()
	{
		return m_dNurseOrderFlow.getSelectedNurseOrderID();
	}


	public void requestNurseOrderPayMoreAction(RequestNurseOrderPayMoreEvent event)
	{
		NurseOrderListHandler.GetInstance().requestNurseOrderPayMoreAction(event);
		return;
	}

	public void onEventMainThread(AnswerNurseOrderPayMoreEvent event)
	{
		//01. 同步数据
		m_dNurseOrderFlow.setSelectedNurseOrderID(event.getOrderID());
		m_dNurseOrderFlow.setPayMorePrice(event.getPrice());

		//02. 关闭当前页面
		NurseOrderPayMoreActivity activity = (NurseOrderPayMoreActivity)m_context;
		activity.finish();

		//03. 跳转到支付页面（paymore）
		Intent intent = new Intent(activity, NurseOrderPayInPayMoreActivity.class);
		activity.startActivity(intent);
		return;
	}

}
