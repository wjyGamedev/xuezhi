/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.event.send.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/24		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_order_list.msg_handler;

import com.module.event.EventID;
import com.taixinkanghu_client.net.config.NurseOrderConfig;
import com.taixinkanghu_client.net.event.BaseNetEvent;

import java.util.HashMap;

public class RequestNurseOrderConfirmInChangeNurse extends BaseNetEvent
{
	private String m_nurseID = null;
	private String m_orderID = null;
	private String m_beginDate = null;

	public RequestNurseOrderConfirmInChangeNurse()
	{
		super(EventID.QUEST_NURSE_ORDER_CONFIRM_FOR_CHANGE_NURSE);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> nurseOrderConfirm = new HashMap<String, String>();
		if (m_nurseID != null)
		{
			nurseOrderConfirm.put(NurseOrderConfig.NURSE_ID, m_nurseID);
		}
		if (m_orderID != null)
		{
			nurseOrderConfirm.put(NurseOrderConfig.ORDER_ID, m_orderID);
		}
		if (m_beginDate != null)
		{
			nurseOrderConfirm.put(NurseOrderConfig.BEGIN_DATE_FOR_CHANGE_NURSE, m_beginDate);
		}
		return nurseOrderConfirm;

	}

	public String getNurseID()
	{
		return m_nurseID;
	}

	public void setNurseID(String nurseID)
	{
		m_nurseID = nurseID;
	}

	public String getOrderID()
	{
		return m_orderID;
	}

	public void setOrderID(String orderID)
	{
		m_orderID = orderID;
	}

	public String getBeginDate()
	{
		return m_beginDate;
	}

	public void setBeginDate(String beginDate)
	{
		m_beginDate = beginDate;
	}
}
