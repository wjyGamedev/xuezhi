/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_order_list.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.net.config.config.NurseOrderConfig;

import java.util.HashMap;

public class RequestCommentNurseOrderEvent extends BaseNetEvent
{
	private String m_userID = null;
	private String m_orderID = null;
	private int m_commentLevel = DataConfig.DEFAULT_VALUE;
	private String m_commentContent = null;

	public RequestCommentNurseOrderEvent()
	{
		super(EventID.QUEST_COMMENT_NURSE_ORDER);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> nurseOrderList = new HashMap<String, String>();
		nurseOrderList.put(NurseOrderConfig.USER_ID, m_userID);
		nurseOrderList.put(NurseOrderConfig.ORDER_ID, m_orderID);
		nurseOrderList.put(NurseOrderConfig.COMMENT_LEVEL, String.valueOf(m_commentLevel));
		nurseOrderList.put(NurseOrderConfig.COMMENT_CONTENT, m_commentContent);
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

	public int getCommentLevel()
	{
		return m_commentLevel;
	}

	public void setCommentLevel(int commentLevel)
	{
		m_commentLevel = commentLevel;
	}

	public String getCommentContent()
	{
		return m_commentContent;
	}

	public void setCommentContent(String commentContent)
	{
		m_commentContent = commentContent;
	}
}
