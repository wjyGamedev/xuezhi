/**
 * Copyright (c) 213Team
 *
 * @className : com.module.util.timer.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/8		WangJY		1.0.0		create
 */

package com.module.util.timer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SoftInputTimerTask extends TimerTaskWrapper
{
	private Activity m_activity = null;
	private View m_view = null;

	public SoftInputTimerTask(Activity activity, View view)
	{
		m_activity = activity;
		m_view = view;
		setTimerTaskListener(new SoftInputTimerTaskHandler());
	}

	class SoftInputTimerTaskHandler implements TimerTaskListener
	{
		@Override
		public void execAction()
		{
			//	弹出软键盘
			InputMethodManager imm = (InputMethodManager)m_activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(m_view, InputMethodManager.RESULT_SHOWN);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		}
	}
}
