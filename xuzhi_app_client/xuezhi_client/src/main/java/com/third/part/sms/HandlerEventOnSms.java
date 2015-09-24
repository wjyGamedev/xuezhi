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

import android.app.Activity;
import android.content.Context;

import com.module.event.EventID;
import com.module.widget.dialog.TipsDialog;

import java.util.ArrayList;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import event.EventBus;

public class HandlerEventOnSms extends EventHandler
{
	private EventBus m_eventBus = EventBus.getDefault();
	private Activity m_activity = null;
	private Context  m_context  = null;

	public HandlerEventOnSms(Context context)
	{
		m_context = context;
	}

	public void init()
	{
		m_eventBus.register(this);
	}

	public void clearup()
	{
		m_eventBus.unregister(this);
	}

	public void onEventAsync(BaseSmsEvent baseSmsEvent)
	{
		int    event  = baseSmsEvent.getEvent();
		int    result = baseSmsEvent.getResult();
		Object data   = baseSmsEvent.getData();

		//失败的提示框
		if (result == SMSSDK.RESULT_ERROR)
		{
			TipsDialog.GetInstance().setMsg(data.toString(), m_context);
			TipsDialog.GetInstance().show();
			//			这里不退return，在下面处理里面对错误进行二次处理。
			//			return;
		}

		switch (event)
		{
			case SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES:
			{
				handleGetSupportedCountries(result, data);
			}
			break;
			case SMSSDK.EVENT_GET_VERIFICATION_CODE:
			{
				handleGetVerificationCode(result, data);
			}
			break;
			case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
			{
				handleSubmitVerificationCode(result, data);
			}
			break;
			case SMSSDK.EVENT_GET_CONTACTS:
			{
				handleGetContacts(result, data);
			}
			case SMSSDK.EVENT_SUBMIT_USER_INFO:
			{
				handleSubmitUserInfo(result, data);
			}
			case SMSSDK.EVENT_GET_FRIENDS_IN_APP:
			{
				handleGetFriendsInApp(result, data);
			}
			case SMSSDK.EVENT_GET_NEW_FRIENDS_COUNT:
			{
				handleGetNewFriendsCount(result, data);
			}
			case SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE:
			{
				handleGetVoiceVerificationCode(result, data);
			}
			break;
			default:
			{
				//TODO:error
				handleInvalid(event, result, data);
			}
			break;
		}
	}

	@Override
	public void onRegister()
	{
		super.onRegister();
	}

	@Override
	public void beforeEvent(int event, Object data)
	{
		super.beforeEvent(event, data);
	}

	@Override
	public void afterEvent(int event, int result, Object data)
	{
		BaseSmsEvent baseSmsEvent = new BaseSmsEvent(EventID.SMS_EVENT_DEFAULT);
		baseSmsEvent.init(event, result, data);
		m_eventBus.post(baseSmsEvent);
	}

	@Override
	public void onUnregister()
	{
		super.onUnregister();
	}

	/**
	 * imp event
	 */
	private void handleResultCompliete(int result, Object data)
	{

	}

	private void handleResultError(int result, Object data)
	{

	}

	private void handleGetSupportedCountries(int result, Object data)
	{
		DSmsAutho.GetInstance().setCountryCodeMaps((ArrayList<HashMap<String, String>>)data);
	}

	private void handleGetVerificationCode(int result, Object data)
	{
		if (result == SMSSDK.RESULT_ERROR)
		{
			//TODO:失败
			return;
		}
		else if (result == SMSSDK.RESULT_COMPLETE)
		{
			//TODO:成功
			return;
		}
		else
		{
			//TODO:异常
			return;
		}
	}

	private void handleSubmitVerificationCode(int result, Object data)
	{

	}

	private void handleGetContacts(int result, Object data)
	{

	}

	private void handleSubmitUserInfo(int result, Object data)
	{

	}

	private void handleGetFriendsInApp(int result, Object data)
	{

	}

	private void handleGetNewFriendsCount(int result, Object data)
	{

	}

	private void handleGetVoiceVerificationCode(int result, Object data)
	{

	}

	private void handleInvalid(int event, int result, Object data)
	{

	}
}
