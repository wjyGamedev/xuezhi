/**
 * Copyright (c) 213Team
 *
 * @className : app.model.Event.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/6		WangJY		1.0.0		create
 */

package com.module.event;

public class BaseEvent implements IEvent
{
	public BaseEvent(int iEventID)
	{
		m_iEventID = iEventID;
	}

	public int GetEventID()
	{
		return  m_iEventID;
	}
	private int m_iEventID = 0;
}
