/**
 * Copyright (c) 213Team
 *
 * @className : com.common.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${所有activity的基类}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/1		WangJY		1.0.0		create
 */

package com.module.frame;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.module.data.DGlobal;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

abstract public class BaseActivity extends FragmentActivity
{
	//eventbus
	//	protected EventBus m_eventBus = EventBus.getDefault();
	protected BaseActivity m_activity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//		m_eventBus.register(this);
		super.onCreate(savedInstanceState);

		//setContentView
		m_activity = onCreateAction();
		ButterKnife.bind(m_activity);
		//init
		onAfterCreateAction();
	}

	@Override
	protected void onDestroy()
	{
		//		m_eventBus.unregister(this);
		onDestoryAction();
		ButterKnife.unbind(m_activity);
		super.onDestroy();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onStart()
	{
		initGlobalData();
		super.onStart();
	}

	@Override
	protected void onStop()
	{
		clearupGlobalData();
		super.onStop();
	}

	//作用：点击EditText文本框之外任何地方隐藏键盘
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		if (ev.getAction() == MotionEvent.ACTION_DOWN)
		{
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev))
			{

				InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
				if (imm != null)
				{
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev))
		{
			return true;
		}
		return onTouchEvent(ev);
	}

	/**
	 * 将当前content，放入全局管理器
	 */
	private void initGlobalData()
	{
		DGlobal.GetInstance().setContext(this);
	}

	private void clearupGlobalData()
	{
		DGlobal.GetInstance().clearupContext(this);
	}

	private boolean isShouldHideInput(View v, MotionEvent event)
	{
		if (v != null && (v instanceof EditText))
		{
			int[] leftTop = {0, 0};
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom)
			{
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			}
			else
			{
				return true;
			}
		}
		return false;
	}
	//	/**
	//	 * eventbus handler
	//	 */
	//	public void onEventMainThread(BaseEvent event)
	//	{
	//		TipsDialog.GetInstance().setMsg("Please implementation eventbus handler!", this);
	//		TipsDialog.GetInstance().show();
	//		return;
	//	}
	abstract public BaseActivity onCreateAction();

	abstract public void onAfterCreateAction();

	abstract public void onDestoryAction();

}