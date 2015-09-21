/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.event.recv.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/21		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_order_list.msg_handler;


import com.module.event.EventID;
import com.module.event.BaseNetEvent;

public class AnswerNurseOrderAlipayEvent extends BaseNetEvent
{
	private String m_result = null;

	public AnswerNurseOrderAlipayEvent()
	{
		super(EventID.FINISHED_NURSE_ORDER_ALIPAY);
	}

	public String getResult()
	{
		return m_result;
	}

	public void setResult(String result)
	{
		m_result = result;
	}
}
