/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/17		WangJY		1.0.0		create
 */

package com.module.data;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class DGlobal
{
	private static DGlobal s_dGlobal = new DGlobal();

	//app的content
	private Context m_appContext        = null;
	private Object  m_syncAppContextObj = new Object();

	//当前显示的activity
	private Context m_context        = null;
	private Object  m_syncContextObj = new Object();

	//应用区域高度
	private Integer m_appRegionHeight        = -1;
	private Object  m_syncAppRegionHeightObj = new Object();

	private DGlobal()
	{}

	public static DGlobal GetInstance()
	{
		return s_dGlobal;
	}

	public Context getContext()
	{
		synchronized (m_syncContextObj)
		{
			return m_context;
		}
	}

	public void setContext(Context context)
	{
		synchronized (m_syncContextObj)
		{
			m_context = context;
		}
	}

	public Context getAppContext()
	{
		synchronized (m_syncAppContextObj)
		{
			return m_appContext;
		}
	}

	public void setAppContext(Context appContext)
	{
		synchronized (m_syncAppContextObj)
		{
			m_appContext = appContext;
		}
	}

	public void clearupContext(Context context)
	{
		synchronized (m_syncContextObj)
		{
			if (m_context == context)
			{
				m_context = null;
			}
		}

	}

	public Integer getAppRegionHeight()
	{
		synchronized (m_syncAppRegionHeightObj)
		{
			return m_appRegionHeight;
		}
	}

	public void setAppRegionHeight(Integer appRegionHeight)
	{
		synchronized (m_syncAppRegionHeightObj)
		{
			m_appRegionHeight = appRegionHeight;
		}
	}

	public String getMCC()
	{
		TelephonyManager tm = (TelephonyManager) m_appContext.getSystemService(Context.TELEPHONY_SERVICE);
		// 返回当前手机注册的网络运营商所在国家的MCC+MNC. 如果没注册到网络就为空.
		String networkOperator = tm.getNetworkOperator();
		if (!TextUtils.isEmpty(networkOperator)) {
			return networkOperator;
		}

		// 返回SIM卡运营商所在国家的MCC+MNC. 5位或6位. 如果没有SIM卡返回空
		return tm.getSimOperator();
	}
}
