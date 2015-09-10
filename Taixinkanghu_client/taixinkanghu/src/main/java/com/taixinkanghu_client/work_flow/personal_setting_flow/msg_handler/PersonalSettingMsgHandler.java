/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.personal_setting_flow.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.personal_setting_flow.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.module.storage.OwnerPreferences;
import com.module.storage.StorageWrapper;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.data_module.register_account.data.DAccount;
import com.taixinkanghu_client.data_module.register_account.msg_handler.AnswerRegisterEvent;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;
import com.taixinkanghu_client.work_flow.personal_setting_flow.ui.PersonalSettingActivity;
import com.taixinkanghu_client.work_flow.register_flow.ui.RegisterActivity;

import org.json.JSONException;

public class PersonalSettingMsgHandler extends BaseUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public PersonalSettingMsgHandler(PersonalSettingActivity activity)
	{
		super(activity);
	}

	//01. 跳转到主页面
	public void go2MainPage()
	{
		PersonalSettingActivity activity = (PersonalSettingActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}

	//02. 登入/登出
	public void loginoutAction()
	{
		PersonalSettingActivity activity = (PersonalSettingActivity)m_context;

		boolean loginFlag = DAccount.GetInstance().isRegisterSuccess();

		//01. 未注册
		if (loginFlag == false)
		{
			activity.startActivity(new Intent(activity, RegisterActivity.class));
			return;
		}

		//02. 注册
		activity.popLogoutDialog();
		return;

	}

	//03. 登出
	public void requestLogoutAction()
	{
		PersonalSettingActivity activity = (PersonalSettingActivity)m_context;

		//01. 注销登录数据
		OwnerPreferences setting = StorageWrapper.GetInstance().getOwnerPreferences();
		try
		{
			setting.logout();
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString(), m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		//02. update ui
		activity.logoutUI();
		return;

	}

	//04. 同步数据到UI
	public void updateContent()
	{
		PersonalSettingActivity activity = (PersonalSettingActivity)m_context;

		boolean loginFlag = DAccount.GetInstance().isRegisterSuccess();

		//01. 未注册,则显示登出页面
		if (loginFlag == false)
		{
			activity.logoutUI();
			return;
		}

		//02. 注册则显示登入页面
		activity.loginUI();
		return;

	}

	//05. 注册成功
	public void onEventMainThread(AnswerRegisterEvent event)
	{
		updateContent();
		return;
	}
}
