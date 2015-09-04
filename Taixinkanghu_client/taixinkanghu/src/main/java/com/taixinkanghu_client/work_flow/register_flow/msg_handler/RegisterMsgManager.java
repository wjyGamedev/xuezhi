/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.register_flow.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/2		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.register_flow.msg_handler;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequestForm;
import com.module.net.NetConfig;
import com.taixinkanghu_client.net.BaseHttp;
import com.taixinkanghu_client.net.handler.BaseErrorListener;
import com.taixinkanghu_client.work_flow.register_flow.ui.RegisterActivity;
import com.third.part.sms.SmsAutho;
import com.third.part.sms.SmsConfig;

import java.util.HashMap;

import event.EventBus;

public class RegisterMsgManager
{
	private EventBus     m_eventBus     = EventBus.getDefault();
	private RequestQueue m_requestQueue = BaseHttp.getInstance().getRequestQueue();

	private BaseErrorListener     m_baseErrorListener     = new BaseErrorListener();
	private AnswerRegisterHandler m_answerRegisterHandler = new AnswerRegisterHandler();

	private RegisterActivity m_registerActivity = null;

	public RegisterMsgManager(RegisterActivity registerActivity)
	{
		m_registerActivity = registerActivity;
	}

	public void reqSupportedCountriesAction()
	{
		RequestSupportedCountriesEvent event = new RequestSupportedCountriesEvent();
		m_eventBus.post(event);
	}

	//请求获取支持国家列表
	public void onEventMainThread(RequestSupportedCountriesEvent event)
	{
		SmsAutho.GetInstance().getSupportedCountries();
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
																m_baseErrorListener);

		m_requestQueue.add(myReq);
	}

	//接受注册成功event
	public void onEventMainThread(AnswerRegisterEvent event)
	{
		m_registerActivity.registerSuccessAction();
		return;
	}

}
