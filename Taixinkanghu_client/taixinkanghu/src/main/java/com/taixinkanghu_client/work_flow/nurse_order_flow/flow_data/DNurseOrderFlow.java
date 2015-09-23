/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.nurse_order_flow.flow_data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.nurse_order_flow.flow_data;

import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurseOrder;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurserOrderList;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestNurseOrderCancelInWaitPayEvent;
import com.taixinkanghu_client.data_module.register_account.data.DAccount;

public class DNurseOrderFlow
{
	private static DNurseOrderFlow s_dNurseOrderFlow = new DNurseOrderFlow();

	//01. 选择订单,即通过点击跳转到订单详细信息页面。(NurseInfoActivity)
	private int    m_selectedNurseOrderID = DataConfig.DEFAULT_VALUE;
	private Object m_syncNurseOrderID     = new Object();

	//02. 补差价的信息
	private int m_payMorePrice = DataConfig.DEFAULT_VALUE;
	private Object m_syncPayMorePrice = new Object();

	private int    m_payMoreOrderID = DataConfig.DEFAULT_VALUE;
	private Object m_syncPayMoreOrderID     = new Object();


	private DNurseOrderFlow()
	{}

	public static DNurseOrderFlow GetInstance()
	{
		return s_dNurseOrderFlow;
	}

	public void clearupAll()
	{
		synchronized (m_syncNurseOrderID)
		{
			m_selectedNurseOrderID = DataConfig.DEFAULT_VALUE;
		}

		synchronized (m_syncPayMorePrice)
		{
			m_payMorePrice = DataConfig.DEFAULT_VALUE;
		}

		synchronized (m_syncPayMoreOrderID)
		{
			m_payMoreOrderID = DataConfig.DEFAULT_VALUE;
		}

	}

	/**
	 * construct
	 */
	public RequestNurseOrderCancelInWaitPayEvent constructRequestNurseOrderCancelInWaitPayEvent()
	{
		RequestNurseOrderCancelInWaitPayEvent event   = new RequestNurseOrderCancelInWaitPayEvent();
		String                                userID  = DAccount.GetInstance().getId();
		int                                   orderID = getSelectedNurseOrderID();
		event.setUserID(userID);
		event.setNurseOrderID(String.valueOf(orderID));

		return event;
	}

	/**
	 * func:get
	 */
	public int getSelectedNurseOrderID()
	{
		synchronized (m_syncNurseOrderID)
		{
			return m_selectedNurseOrderID;
		}
	}

	public void setSelectedNurseOrderID(int selectedNurseOrderID)
	{
		synchronized (m_syncNurseOrderID)
		{
			m_selectedNurseOrderID = selectedNurseOrderID;
		}
	}

	public DNurseOrder getDNurseOrder()
	{
		int selectedNurseOrderID = getSelectedNurseOrderID();
		return DNurserOrderList.GetInstance().getNurseOrderByOrderID(selectedNurseOrderID);
	}

	public int getNurseID()
	{
		DNurseOrder dNurseOrder = getDNurseOrder();
		if (dNurseOrder == null)
		{
			TipsDialog.GetInstance().setMsg("dNurseOrder == null![m_selectedNurseOrderID:=" + getSelectedNurseOrderID() + "]");
			TipsDialog.GetInstance().show();
			return DataConfig.DEFAULT_VALUE;
		}

		return dNurseOrder.getNurseID();
	}

	public String getOrderSerialNum()
	{
		DNurseOrder dNurseOrder = getDNurseOrder();
		if (dNurseOrder == null)
		{
			TipsDialog.GetInstance().setMsg("dNurseOrder == null![m_selectedNurseOrderID:=" + getSelectedNurseOrderID() + "]");
			TipsDialog.GetInstance().show();
			return null;
		}

		return dNurseOrder.getOrderSerialNum();
	}

	public int getTotalPrice()
	{
		DNurseOrder dNurseOrder = getDNurseOrder();
		if (dNurseOrder == null)
		{
			TipsDialog.GetInstance().setMsg("dNurseOrder == null![m_selectedNurseOrderID:=" + getSelectedNurseOrderID() + "]");
			TipsDialog.GetInstance().show();
			return DataConfig.DEFAULT_VALUE;
		}

		return dNurseOrder.getTotalCharge();

	}

	public int getPayMorePrice()
	{
		synchronized (m_syncPayMorePrice)
		{
			return m_payMorePrice;
		}
	}

	public void setPayMorePrice(int payMorePrice)
	{
		synchronized (m_syncPayMorePrice)
		{
			m_payMorePrice = payMorePrice;
		}
	}

	public int getPayMoreOrderID()
	{
		synchronized (m_syncPayMoreOrderID)
		{
			return m_payMoreOrderID;
		}
	}

	public void setPayMoreOrderID(int payMoreOrderID)
	{
		synchronized (m_syncPayMoreOrderID)
		{
			m_payMoreOrderID = payMoreOrderID;
		}
	}
}
