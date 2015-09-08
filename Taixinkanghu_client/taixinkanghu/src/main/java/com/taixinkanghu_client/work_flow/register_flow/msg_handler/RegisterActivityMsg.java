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

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.data_module.register_account.msg_handler.AnswerRegisterEvent;
import com.taixinkanghu_client.data_module.register_account.msg_handler.RegisterAccountMsgHandler;
import com.taixinkanghu_client.work_flow.register_flow.ui.RegisterActivity;

public class RegisterActivityMsg extends BaseUIMsgHandler
{
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
		RegisterActivity registerActivity = (RegisterActivity)m_context;
		if (registerActivity == null)
		{
			TipsDialog.GetInstance().setMsg("registerActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		registerActivity.registerSuccessAction();
		return;
	}

}
