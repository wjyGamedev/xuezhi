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
 * 2015/9/2		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.net;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import event.EventBus;

public class NetService extends Service
{
	private EventBus m_eventBus = EventBus.getDefault();

	@Override
	public void onCreate()
	{
		super.onCreate();
		initModule();
	}

	@Override
	public void onDestroy()
	{
		cleanupModule();
		super.onDestroy();
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	private void initModule()
	{
		m_eventBus.register(this);
	}

	private void cleanupModule()
	{
		m_eventBus.unregister(this);
	}

}
