/**
 * Copyright (c) 213Team
 *
 * @className : app.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/9		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.welcome_page.ui;

import android.content.Intent;

import com.module.frame.BaseActivity;
import com.module.util.timer.TimerTaskWrapper;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;
import com.xuezhi_client.work_flow.welcome_page.msg_handler.WelcomeMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

public class WelcomeActivity extends BaseActivity
{
	private final static long DELAY_TIME_MILLISENCENDS = 4000;

	private TimerTaskWrapper m_waitTimerTask    = new TimerTaskWrapper();
	private TimerTaskHandler m_timerTaskHandler = new TimerTaskHandler();

	private WelcomeMsgHandler m_welcomeMsgHandler = new WelcomeMsgHandler(this);

	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_welcome);
		return this;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
	}

	@Override
	public void onDestoryAction()
	{

	}

	private void init()
	{
		//01. 倒计时时间
		m_waitTimerTask.setTimerTaskListener(m_timerTaskHandler);
		m_waitTimerTask.schedule(DELAY_TIME_MILLISENCENDS);

		//02. 请求消息
		m_welcomeMsgHandler.initAction();
	}

	/**
	 * timer handler
	 */
	class TimerTaskHandler implements TimerTaskWrapper.TimerTaskListener
	{
		@Override
		public void execAction()
		{
			//01. 如果注册成功，则跳转到主页面
			Intent intent = null;
//			if (DAccount.GetInstance().isRegisterSuccess())
//			{
				intent = new Intent(WelcomeActivity.this, MainActivity.class);
//			}
			//02. 否则，则跳转到注册页面。
//			else
//			{
//				intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
//			}
			startActivity(intent);
			//02. 关闭当前页面
			WelcomeActivity.this.finish();
		}
	}
}
