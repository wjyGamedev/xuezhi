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

import android.content.Context;
import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.taixinkanghu_client.data_module.register_account.msg_handler.AnswerRegisterEvent;
import com.taixinkanghu_client.data_module.register_account.msg_handler.RegisterAccountMsgHandler;
import com.taixinkanghu_client.data_module.register_account.msg_handler.RequestRegisterEvent;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;
import com.taixinkanghu_client.work_flow.register_flow.ui.RegisterActivity;

public class RegisterActivityMsg extends BaseUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public RegisterActivityMsg(RegisterActivity registerActivity)
	{
		super(registerActivity);
	}

	public void initSmsAutho(Context context)
	{
		RegisterAccountMsgHandler.GetInstance().initSmsAutho(context);
	}


	public void requestSupportedCountriesAction()
	{
		RegisterAccountMsgHandler.GetInstance().requestSupportedCountriesAction();
	}

	//接受注册成功event
	public void onEventMainThread(AnswerRegisterEvent event)
	{
		//01. 关闭当前页面
		RegisterActivity registerActivity = (RegisterActivity)m_context;
		registerActivity.finish();

		//02. 返回到主页面
		registerActivity.startActivity(new Intent(registerActivity, MainActivity.class));
		return;
	}

	//
	public void requestRegisterAction(RequestRegisterEvent reqRegisterEvent)
	{
		RegisterAccountMsgHandler.GetInstance().requestRegisterAction(reqRegisterEvent);
	}
}
