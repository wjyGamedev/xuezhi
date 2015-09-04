/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.third.party.sms.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/1		WangJY		1.0.0		create
 */

package com.third.part.sms;

import com.module.event.BaseEvent;

public class BaseSmsEvent extends BaseEvent
{
	private int m_event = 0;
	private int m_result = 0;
	private Object m_data = null;

	public BaseSmsEvent(int iEventID)
	{
		super(iEventID);
	}

	public void init(int event, int result, Object data)
	{
		m_event = event;
		m_result = result;
		m_data = data;
	}

	public int getEvent()
	{
		return m_event;
	}

	public int getResult()
	{
		return m_result;
	}

	public Object getData()
	{
		return m_data;
	}
}
