/**
 * Copyright (c) 213Team
 *
 * @className : com.module.event.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.module.event;

public class BaseLogicalEvent extends BaseEvent
{
	public BaseLogicalEvent()
	{
		super(EventID.LOGICAL_DEFAULT);
	}

	public BaseLogicalEvent(int iEventID)
	{
		super(iEventID);
	}
}
