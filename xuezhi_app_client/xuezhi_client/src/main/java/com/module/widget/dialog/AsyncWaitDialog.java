/**
 * Copyright (c) 213Team
 *
 * @className : com.module.widget.dialog.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.module.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.module.data.DGlobal;
import com.module.event.BaseLogicalEvent;
import com.module.event.EventID;
import com.module.util.timer.TimerTaskWrapper;
import com.xuzhi_client.xuzhi_app_client.R;

import event.EventBus;

public class AsyncWaitDialog
{
	private final String INFO_WAIT_TIPS = DGlobal.GetInstance().getAppContext().getResources().getString(R.string.wait_tips);
	private final String INFO_ERROR_TIPS_INIT_FIRST = DGlobal.GetInstance().getAppContext().getResources().getString(R.string.error_tips_init_first);


	private final static long DELAY_TIME_MILLISENCENDS = 10000;

	private EventBus m_eventBus = EventBus.getDefault();
	private boolean        m_initFlag           = false;
	private ProgressDialog m_waitProgressDialog = null;
	private HandleWaitDialogFinishedEvent m_handleWaitDialogFinishedEvent = null;
	private long m_waitTimeByMilliSeconds = DELAY_TIME_MILLISENCENDS;
	private String m_waitTips = INFO_WAIT_TIPS;


	private TimerTaskWrapper m_waitTimerTask    = new TimerTaskWrapper();
	private TimerTaskHandler m_timerTaskHandler = new TimerTaskHandler();


	public interface HandleWaitDialogFinishedEvent
	{
		public void OnWaitDialogFinished();
	}

	class WaitFinishedEvent extends BaseLogicalEvent
	{
		public WaitFinishedEvent()
		{
			super(EventID.LC_WAIT_DIALOG_FINISHED);
		}
	}

	class TimerTaskHandler implements TimerTaskWrapper.TimerTaskListener
	{
		@Override
		public void execAction()
		{
			WaitFinishedEvent event = new WaitFinishedEvent();
			m_eventBus.post(event);
			return;
		}
	}


	public AsyncWaitDialog()
	{
		m_eventBus.register(this);
		m_waitTimerTask.setTimerTaskListener(m_timerTaskHandler);
	}

	public void initDefault(Context context)
	{
		m_waitProgressDialog = new ProgressDialog(context);
		m_waitProgressDialog.setMessage(m_waitTips);
		m_initFlag = true;
		m_waitTimerTask.schedule(m_waitTimeByMilliSeconds);
	}

	public void init(Context context, long waitTimeByMilliSecond)
	{
		m_waitProgressDialog = new ProgressDialog(context);
		m_waitProgressDialog.setMessage(m_waitTips);
		m_initFlag = true;
		m_waitTimeByMilliSeconds = waitTimeByMilliSecond;
		m_waitTimerTask.schedule(m_waitTimeByMilliSeconds);
	}
	public void setWaitTips(String waitTips)
	{
		if (!check())
			return;

		m_waitTips = waitTips;
	}

	public void setHandleWaitDialogFinishedEvent(HandleWaitDialogFinishedEvent handleWaitDialogFinishedEvent)
	{
		if (!check())
			return;

		m_handleWaitDialogFinishedEvent = handleWaitDialogFinishedEvent;
	}

	public void setWaitTimeByMilliSeconds(long waitTimeByMilliSeconds)
	{
		if (!check())
			return;

		m_waitTimeByMilliSeconds = waitTimeByMilliSeconds;
	}

	public void onEventMainThread(WaitFinishedEvent event)
	{
		if (m_handleWaitDialogFinishedEvent != null)
		{
			m_handleWaitDialogFinishedEvent.OnWaitDialogFinished();
		}
		return;
	}

	public void show()
	{
		if (!check())
			return;

		m_waitProgressDialog.show();
	}

	public void dismiss()
	{
		if (!check())
			return;

		m_waitProgressDialog.dismiss();
		return;
	}

	private boolean check()
	{
		if (!m_initFlag)
		{
			TipsDialog.GetInstance().setMsg(INFO_ERROR_TIPS_INIT_FIRST);
			TipsDialog.GetInstance().show();
			return false;
		}
		return true;
	}

	public boolean isInitFlag()
	{
		return m_initFlag;
	}
}
