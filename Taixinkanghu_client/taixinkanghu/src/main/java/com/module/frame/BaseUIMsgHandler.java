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

	private boolean m_initFlag = false;
	protected Context m_context = null;

	public BaseUIMsgHandler(Context context)
	{
		m_context = context;
		checkInitAndTry();
	}

	protected void init()
	{
		m_eventBus.register(this);
		m_initFlag = true;
	}

	protected void clearup()
	{
		m_eventBus.unregister(this);
	}

	protected void checkInitAndTry()
	{
		if (m_initFlag == true)
			return;

		if (m_initFlag == false)
		{
			init();
		}
	}
}
