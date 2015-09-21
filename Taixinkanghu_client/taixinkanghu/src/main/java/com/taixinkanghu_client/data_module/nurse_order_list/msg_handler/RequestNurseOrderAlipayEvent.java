/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.event.send.${type_name}
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

import android.app.Activity;

import com.module.event.EventID;
import com.module.event.BaseNetEvent;

public class RequestNurseOrderAlipayEvent extends BaseNetEvent
{
	private String m_payInfo = null;
	private Activity m_activity = null;

	public RequestNurseOrderAlipayEvent()
	{
		super(EventID.QUEST_NURSE_ORDER_ALIPAY);
	}

	public String getPayInfo()
	{
		return m_payInfo;
	}

	public void setPayInfo(String payInfo)
	{
		m_payInfo = payInfo;
	}

	public Activity getActivity()
	{
		return m_activity;
	}

	public void setActivity(Activity activity)
	{
		m_activity = activity;
	}
}
