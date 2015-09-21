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
 * 2015/9/22		WangJY		1.0.0		create
 */

package com.module.event;

public class BaseUIEvent extends BaseEvent
{
	public BaseUIEvent()
	{
		super(EventID.UI_DEFAULT);
	}

	public BaseUIEvent(int iEventID)
	{
		super(iEventID);
	}
}
