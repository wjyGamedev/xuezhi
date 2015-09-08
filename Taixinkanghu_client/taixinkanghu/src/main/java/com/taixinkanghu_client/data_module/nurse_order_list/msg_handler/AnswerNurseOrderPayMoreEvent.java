/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.event.recv.${type_name}
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
import com.taixinkanghu_client.net.event.BaseNetEvent;

public class AnswerNurseOrderPayMoreEvent extends BaseNetEvent
{
	private int    m_orderID        = DataConfig.DEFAULT_VALUE;
	private String m_orderSerialNum = null;
	private int    m_price          = DataConfig.DEFAULT_VALUE;

	public AnswerNurseOrderPayMoreEvent()
	{
		super(EventID.FINISHED_NURSE_ORDER_PAY_MORE);
	}

	public int getOrderID()
	{
		return m_orderID;
	}

	public void setOrderID(int orderID)
	{
		m_orderID = orderID;
	}

	public String getOrderSerialNum()
	{
		return m_orderSerialNum;
	}

	public void setOrderSerialNum(String orderSerialNum)
	{
		m_orderSerialNum = orderSerialNum;
	}

	public int getPrice()
	{
		return m_price;
	}

	public void setPrice(int price)
	{
		m_price = price;
	}
}
