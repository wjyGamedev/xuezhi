/**
 * Copyright (c) 213Team
 *
 * @className : app.frame.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${泰心医护APP的数据wrapper类。}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/5		WangJY		1.0.0		create
 */

package com.module.frame;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.module.data.DGlobal;
import com.module.net.BaseHttp;
import com.module.storage.StorageWrapper;
import com.third.part.xinge_tengxun.XinGe;

import java.util.List;

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

		// 在主进程设置信鸽相关的内容
		if (isMainProcess())
		{
			XinGe.GetInstance().init();
		}
	}

	private void onModuleClearup()
	{

	}

	public boolean isMainProcess()
	{
		ActivityManager                             am              = ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE));
		List<ActivityManager.RunningAppProcessInfo> processInfos    = am.getRunningAppProcesses();
		String                                      mainProcessName = getPackageName();
		int                                         myPid           = android.os.Process.myPid();
		for (ActivityManager.RunningAppProcessInfo info : processInfos)
		{
			if (info.pid == myPid && mainProcessName.equals(info.processName))
			{
				return true;
			}
		}
		return false;
	}

}
