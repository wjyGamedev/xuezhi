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
 * 2015/8/20		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_order_list.msg_handler;


import android.text.TextUtils;

import com.module.event.EventID;
import com.taixinkanghu_client.net.config.NurseOrderConfig;
import com.taixinkanghu_client.net.event.BaseNetEvent;

import java.util.HashMap;

public class RequestNurseOrderCheckEvent extends BaseNetEvent
{
	private String m_userID = null;
	private String m_orderID = null;
	private String m_type = null;

	public RequestNurseOrderCheckEvent()
	{
		super(EventID.QUEST_NURSE_ORDER_CHECK);
	}

	public String getUserID()
	{
		return m_userID;
	}

	public void setUserID(String userID)
	{
		m_userID = userID;
	}

	public String getOrderID()
	{
		return m_orderID;
	}

	public void setOrderID(String orderID)
	{
		m_orderID = orderID;
	}

	public String getType()
	{
		return m_type;
	}

	public void setType(String type)
	{
		m_type = type;
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> orderCheck = new HashMap<String, String>();

		if (!TextUtils.isEmpty(m_userID))
		{
			orderCheck.put(NurseOrderConfig.USER_ID, m_userID);
		}
		if (!TextUtils.isEmpty(m_orderID))
		{
			orderCheck.put(NurseOrderConfig.ORDER_ID, m_orderID);
		}
		if (!TextUtils.isEmpty(m_type))
		{
			orderCheck.put(NurseOrderConfig.CHECK_TYPE, m_type);
		}
		return orderCheck;
	}

}
