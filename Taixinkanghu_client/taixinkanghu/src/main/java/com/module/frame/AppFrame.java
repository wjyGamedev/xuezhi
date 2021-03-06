/**
 * Copyright (c) 213Team
 *
 * @className : app.frame.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${泰心医护APP的数据wrapper类。}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/5		WangJY		1.0.0		create
 */

package com.module.frame;

import android.app.Application;

import com.module.data.DGlobal;
import com.module.storage.StorageWrapper;
import com.module.net.BaseHttp;

public class AppFrame extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		init();
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();
	}

	private void init()
	{
		onDataInit();
		onModuleInit();
	}

	private void clearup()
	{
		onDataClearup();
		onModuleClearup();
	}

	private void onDataInit()
	{

	}
	private void onDataClearup()
	{

	}

	private void onModuleInit()
	{
		DGlobal.GetInstance().setAppContext(this);
		BaseHttp.getInstance().init(this);
		StorageWrapper.GetInstance().init(this);
	}
	private void onModuleClearup()
	{

	}


}
