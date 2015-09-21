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
import com.taixinkanghu_client.config.DataConfig;
import com.module.event.BaseNetEvent;

public class AnswerNurseOrderCheckEvent extends BaseNetEvent
{
	private int m_httpStatus = DataConfig.DEFAULT_VALUE;

	public AnswerNurseOrderCheckEvent()
	{
		super(EventID.FINISHED_NURSE_ORDER_CHECK);
	}

	public int getHttpStatus()
	{
		return m_httpStatus;
	}

	public void setHttpStatus(int httpStatus)
	{
		m_httpStatus = httpStatus;
	}
}
