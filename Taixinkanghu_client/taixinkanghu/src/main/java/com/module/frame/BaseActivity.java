/**
 * Copyright (c) 213Team
 *
 * @className : com.common.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${所有activity的基类}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/1		WangJY		1.0.0		create
 */

package com.module.frame;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.module.data.DGlobal;
import com.module.event.BaseEvent;
import com.module.widget.dialog.TipsDialog;

import event.EventBus;

public class BaseActivity extends FragmentActivity
{
	//eventbus
	protected EventBus m_eventBus = EventBus.getDefault();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		m_eventBus.register(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy()
	{
		m_eventBus.unregister(this);
		super.onDestroy();
	}

	@Override
	protected void onStart()
	{
		initGlobalData();
		super.onStart();
	}

	@Override
	protected void onStop()
	{
		clearupGlobalData();
		super.onStop();
	}

	/**
	 * 将当前content，放入全局管理器
	 */
	private void initGlobalData()
	{
		DGlobal.GetInstance().setContext(this);
	}

	private void clearupGlobalData()
	{
		DGlobal.GetInstance().clearupContext(this);
	}

	/**
	 * eventbus handler
	 */
	public void onEventMainThread(BaseEvent event)
	{
		TipsDialog.GetInstance().setMsg("Please implementation eventbus handler!", this);
		TipsDialog.GetInstance().show();
		return;
	}

}



