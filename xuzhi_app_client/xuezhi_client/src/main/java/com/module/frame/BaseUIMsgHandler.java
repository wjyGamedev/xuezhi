/**
 * Copyright (c) 213Team
 *
 * @className : com.module.frame.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/7		WangJY		1.0.0		create
 */

package com.module.frame;

import android.content.Context;

import event.EventBus;

public class BaseUIMsgHandler
{
	protected EventBus m_eventBus = EventBus.getDefault();

	protected Context m_context = null;

	public BaseUIMsgHandler(Context context)
	{
		m_context = context;
		init();
	}

	protected void init()
	{
	}

}
