/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.data_module.register_account.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/7		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.register_account.msg_handler;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequestForm;
import com.module.net.BaseMsgHandler;
import com.xuezhi_client.net.config.config.NetConfig;
import com.third.part.sms.SmsAutho;
import com.third.part.sms.SmsConfig;

import java.util.HashMap;

public class RegisterAccountMsgHandler extends BaseMsgHandler
{
	private static RegisterAccountMsgHandler s_registerAccountMsgHandler = new RegisterAccountMsgHandler();

	private AnswerRegisterHandler m_answerRegisterHandler = new AnswerRegisterHandler();

	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	private RegisterAccountMsgHandler()
	{
		super();
	}

	public static RegisterAccountMsgHandler GetInstance()
	{
		return s_registerAccountMsgHandler;
	}


	public void initSmsAutho(Context context)
	{
		SmsAutho.GetInstance().init(context);
	}

	public void requestSupportedCountriesAction()
	{
		RequestSupportedCountriesEvent event = new RequestSupportedCountriesEvent();
		m_eventBus.post(event);
	}

	//请求获取支持国家列表
	public void onEventAsync(RequestSupportedCountriesEvent event)
	{
		SmsAutho.GetInstance().getSupportedCountries();
	}

	public void requestRegisterAction(RequestRegisterEvent reqRegisterEvent)
	{
		m_eventBus.post(reqRegisterEvent);
	}

	//请求注册event
	public void onEventAsync(RequestRegisterEvent event)
	{
		String countryZipCode = event.getCountryZipCode();
		String phoneNum       = event.getPhoneNum();
		String authCode       = event.getAuthCode();

		HashMap<String, String> registerData = new HashMap<String, String>();
		registerData.put(SmsConfig.ZONE_KEY, countryZipCode);
		registerData.put(SmsConfig.PHONE_KEY, phoneNum);
		registerData.put(SmsConfig.CODE_KEY, authCode);

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.s_registerAddress,
																registerData,
																m_answerRegisterHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}



}
