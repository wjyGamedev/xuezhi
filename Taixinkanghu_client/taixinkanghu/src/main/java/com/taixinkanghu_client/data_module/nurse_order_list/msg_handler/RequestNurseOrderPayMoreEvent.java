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
 * 2015/8/25		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_order_list.msg_handler;

import com.module.event.EventID;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.net.config.NurseOrderConfig;
import com.module.event.BaseNetEvent;

import java.util.HashMap;

public class RequestNurseOrderPayMoreEvent extends BaseNetEvent
{
	private String m_userID       = null;
	private String m_orderID      = null;
	private int    m_totalPrice   = DataConfig.DEFAULT_VALUE;
	private String m_reasonOption = null;
	private String m_reasonValue  = null;

	public RequestNurseOrderPayMoreEvent()
	{
		super(EventID.QUEST_NURSE_ORDER_PAY_MORE);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> nurseOrderList = new HashMap<String, String>();

		nurseOrderList.put(NurseOrderConfig.USER_ID, m_userID);
		nurseOrderList.put(NurseOrderConfig.ORDER_ID, m_orderID);
		nurseOrderList.put(NurseOrderConfig.ORDER_PAY_MORE_PRICE, String.valueOf(m_totalPrice));
		nurseOrderList.put(NurseOrderConfig.ORDER_PAY_MORE_REASON_OPTION, m_reasonOption);
		nurseOrderList.put(NurseOrderConfig.ORDER_PAY_MORE_REASON_VALUE, m_reasonValue);
		return nurseOrderList;
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

	public int getTotalPrice()
	{
		return m_totalPrice;
	}

	public void setTotalPrice(int totalPrice)
	{
		m_totalPrice = totalPrice;
	}

	public String getReasonOption()
	{
		return m_reasonOption;
	}

	public void setReasonOption(String reasonOption)
	{
		m_reasonOption = reasonOption;
	}

	public String getReasonValue()
	{
		return m_reasonValue;
	}

	public void setReasonValue(String reasonValue)
	{
		m_reasonValue = reasonValue;
	}

}
