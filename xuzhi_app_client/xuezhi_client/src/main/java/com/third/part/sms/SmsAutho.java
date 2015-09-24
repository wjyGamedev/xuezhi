/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.third.party.sms.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/1		WangJY		1.0.0		create
 */

package com.third.part.sms;

import android.content.Context;

import com.module.widget.dialog.TipsDialog;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.Date;

import cn.smssdk.SMSSDK;

public class SmsAutho
{
	private static SmsAutho          s_smsAutho             = new SmsAutho();

	private        HandlerEventOnSms m_handlerEventOnSms    = null;
	private        Long              m_NextVerificationTime = 0L;
	private        Date              m_date                 = new Date();
	private 		boolean			 m_InitFlag = false;
	private 		Context	m_context = null;

	private SmsAutho()
	{
	}

	public static SmsAutho GetInstance()
	{
		return s_smsAutho;
	}

	public void init(Context context)
	{
		m_context = context;

		SMSSDK.initSDK(context, SmsConfig.APPKEY, SmsConfig.APPSECRET);

		m_handlerEventOnSms = new HandlerEventOnSms(context);
		m_handlerEventOnSms.init();
		SMSSDK.registerEventHandler(m_handlerEventOnSms);
		m_InitFlag = true;
	}

	public void destory()
	{
		m_handlerEventOnSms.clearup();
		SMSSDK.unregisterAllEventHandler();
	}

	public boolean isInitFlag()
	{
		return m_InitFlag;
	}

	public boolean checkInit()
	{
		if (isInitFlag())
			return true;

		TipsDialog.GetInstance().setMsg(m_context.getResources().getString(R.string.err_info_no_init_sms), m_context);
		TipsDialog.GetInstance().show();
		return false;
	}
	//request event
	public void getSupportedCountries()
	{
		if (!checkInit())
		{
			return;
		}
		SMSSDK.getSupportedCountries();
	}

	public boolean getVerificationCode(String country, String phoneNum)
	{
		if (!checkInit())
		{
			return false;
		}

		Long currTime = m_date.getTime();
//		if (m_NextVerificationTime < currTime)
//		{
			SMSSDK.getVerificationCode(country, phoneNum);
			m_NextVerificationTime = currTime;
			return true;
//		}
//		return false;
	}

	public void submitVerificationCode(String country, String phoneNum, String auth)
	{
		SMSSDK.submitVerificationCode(country, phoneNum, auth);
	}
}
