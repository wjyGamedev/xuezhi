/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.widget.timer.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/26		WangJY		1.0.0		create
 */

package com.module.util.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskWrapper extends TimerTask
{
	private       TimerTaskListener m_timerTaskListener = null;
	private final Timer             m_timer             = new Timer();


	public void schedule(Date when)
	{
		m_timer.schedule(this, when);
	}

	public void schedule(long delay)
	{
		m_timer.schedule(this, delay);
	}

	@Override
	public void run()
	{
		if (m_timerTaskListener != null)
		{
			m_timerTaskListener.execAction();
		}
	}

	public interface TimerTaskListener
	{
		public void execAction();
	}

	public TimerTaskListener getTimerTaskListener()
	{
		return m_timerTaskListener;
	}

	public void setTimerTaskListener(TimerTaskListener timerTaskListener)
	{
		m_timerTaskListener = timerTaskListener;
	}

}
