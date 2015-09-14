/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.net.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/7		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.net;

import com.android.volley.RequestQueue;
import com.taixinkanghu_client.net.handler.BaseErrorListener;

import event.EventBus;

public class BaseMsgHandler
{
	protected EventBus          m_eventBus          = EventBus.getDefault();
	protected RequestQueue      m_requestQueue      = BaseHttp.getInstance().getRequestQueue();
	protected BaseErrorListener m_baseErrorListener = new BaseErrorListener();

	public BaseMsgHandler()
	{
		init();
	}

	protected void init()
	{
	}

}
