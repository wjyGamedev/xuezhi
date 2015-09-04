/**
 * Copyright (c) 213Team
 *
 * @className : app.model.Event.net.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/9		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.net.event;

import com.module.event.BaseEvent;

public class BaseNetEvent extends BaseEvent
{
	public BaseNetEvent(int iEventID)
	{
		super(iEventID);
	}
}
