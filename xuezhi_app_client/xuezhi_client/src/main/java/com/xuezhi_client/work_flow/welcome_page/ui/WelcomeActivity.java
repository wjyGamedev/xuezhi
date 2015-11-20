/**
 * Copyright (c) 213Team
 *
 * @className : app.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/9		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.welcome_page.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.module.frame.BaseActivity;
import com.module.util.timer.TimerTaskWrapper;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;
import com.xuezhi_client.work_flow.welcome_page.msg_handler.WelcomeMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;

public class WelcomeActivity extends BaseActivity
{
	@Bind (R.id.welcome_icon)  ImageView m_welcomeIcomIV  = null;
	@Bind (R.id.welcome_text1) ImageView m_welcomeText1IV = null;
	@Bind (R.id.welcome_text2) ImageView m_welcomeText2IV = null;

	private final static long ACTIVITY_SHOW_TIME_LENGTH = 8000;
	private final static long AFTER_TIME_SHOW_ICON      = 500;
	private final static long AFTER_TIME_SHOW_TEXT_1    = 3000;
	private final static long AFTER_TIME_SHOW_TEXT_2    = 5000;
	private final static int  SHOW_ITEM_TIME_LENGTH     = 2000;

	private TimerTaskWrapper    m_waitTimerTask             = new TimerTaskWrapper();
	private ActivityShwoTimeEnd m_activityShwoTimeEnd       = new ActivityShwoTimeEnd();

	private WelcomeMsgHandler m_welcomeMsgHandler = new WelcomeMsgHandler(this);

	private AlphaAnimation m_showIconAnimation = null;
	private AlphaAnimation m_showText1Animation = null;
	private AlphaAnimation m_showText2Animation = null;

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

	@Override
	protected void onStart()
	{
		super.onStart();
		new Handler().postDelayed(new Runnable()
								  {
									  public void run()
									  {
										  setShowAnimation(m_showIconAnimation,m_welcomeIcomIV, SHOW_ITEM_TIME_LENGTH);
									  }
								  }, AFTER_TIME_SHOW_ICON
								 );
		new Handler().postDelayed(new Runnable()
								  {
									  public void run()
									  {
										  setShowAnimation(m_showText1Animation,m_welcomeText1IV, SHOW_ITEM_TIME_LENGTH);
									  }
								  }, AFTER_TIME_SHOW_TEXT_1
								 );
		new Handler().postDelayed(new Runnable()
								  {
									  public void run()
									  {
										  setShowAnimation(m_showText2Animation,m_welcomeText2IV, SHOW_ITEM_TIME_LENGTH);
									  }
								  }, AFTER_TIME_SHOW_TEXT_2);
	}

	private void init()
	{
		//01. 倒计时时间
		m_waitTimerTask.setTimerTaskListener(m_activityShwoTimeEnd);
		m_waitTimerTask.schedule(ACTIVITY_SHOW_TIME_LENGTH);

		//02. 请求消息
		m_welcomeMsgHandler.initAction();
	}


	/**
	 * View渐现动画效果
	 */
	private void setShowAnimation(AlphaAnimation showAnimation, View view, int duration)
	{
		if (null == view || duration < 0)
		{
			return;
		}

		if (null != showAnimation)
		{
			showAnimation.cancel();
		}

		showAnimation = new AlphaAnimation(0.0f, 1.0f);
		showAnimation.setDuration(duration);
		showAnimation.setFillAfter(true);
		view.startAnimation(showAnimation);
	}

	/**
	 * timer handler
	 */
	class ActivityShwoTimeEnd implements TimerTaskWrapper.TimerTaskListener
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
