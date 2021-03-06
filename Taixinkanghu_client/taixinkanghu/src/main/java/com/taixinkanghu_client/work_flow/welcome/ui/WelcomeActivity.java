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

package com.taixinkanghu_client.work_flow.welcome.ui;

import android.content.Intent;
import android.os.Bundle;

import com.module.frame.BaseActivity;
import com.module.util.timer.TimerTaskWrapper;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;
import com.taixinkanghu_client.work_flow.welcome.msg_handler.WelcomeMsgHandler;

public class WelcomeActivity extends BaseActivity
{
	private final static long DELAY_TIME_MILLISENCENDS = 2000;

	private TimerTaskWrapper m_waitTimerTask    = new TimerTaskWrapper();
	private TimerTaskHandler m_timerTaskHandler = new TimerTaskHandler();

	private WelcomeMsgHandler m_welcomeMsgHandler = new WelcomeMsgHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		initWaitAction();
	}

	private void initWaitAction()
	{
		//01. 倒计时时间
		m_waitTimerTask.setTimerTaskListener(m_timerTaskHandler);
		m_waitTimerTask.schedule(DELAY_TIME_MILLISENCENDS);

		//02. 请求列表
		m_welcomeMsgHandler.requestHospitalList();
		m_welcomeMsgHandler.requestDepartmentList();

	}

	/**
	 * timer handler
	 */
	class TimerTaskHandler implements TimerTaskWrapper.TimerTaskListener
	{
		@Override
		public void execAction()
		{
			//01. 开启主页面
			Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
			startActivity(intent);
			//02. 关闭当前页面
			WelcomeActivity.this.finish();
		}
	}
}
