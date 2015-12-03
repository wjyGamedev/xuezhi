/**
 * Copyright (c) 213Team
 *
 * @className : com.module.widget.dialog.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.module.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.module.data.DGlobal;
import com.module.event.BaseLogicalEvent;
import com.module.event.EventID;
import com.module.frame.BaseActivity;
import com.module.util.timer.TimerTaskWrapper;
import com.module.widget.fragment.waitting_wheel_mask.WaittingWheelMask;
import com.xuezhi_client.config.DataConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import event.EventBus;

public class RotationWaittingDialog
{
	private static RotationWaittingDialog s_rotationWaittingDialog = new RotationWaittingDialog();


	private final String INFO_WAIT_TIPS             = DGlobal.GetInstance().getAppContext().getResources().getString(R.string.wait_tips);
	private final String INFO_ERROR_TIPS_INIT_FIRST = DGlobal.GetInstance().getAppContext().getResources().getString(R.string
																															 .error_tips_init_first);


	private final static long MAX_DURATION = 20000;

	private EventBus                      m_eventBus                      = EventBus.getDefault();
	private boolean                       m_initFlag                      = false;
	private ProgressDialog                m_waitProgressDialog            = null;
	private HandleWaitDialogFinishedEvent m_handleWaitDialogFinishedEvent = null;
	private long                          m_waitTimeByMilliSeconds        = MAX_DURATION;
	private String                        m_waitTips                      = INFO_WAIT_TIPS;

	private BaseActivity m_activity = null;
	private int          m_pageID   = DataConfig.DEFAULT_ID;


	private TimerTaskWrapper m_waitTimerTask    = new TimerTaskWrapper();
	private TimerTaskHandler m_timerTaskHandler = new TimerTaskHandler();

	public static RotationWaittingDialog GetInstance()
	{
		return s_rotationWaittingDialog;
	}

	private RotationWaittingDialog() {}


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

	public RotationWaittingDialog(Context context, int pageID)
	{
		m_activity = (BaseActivity)context;
		m_pageID = pageID;
		m_eventBus.register(this);
		m_waitTimerTask.setTimerTaskListener(m_timerTaskHandler);
	}

	public void dialogOpen()
	{
		WaittingWheelMask waittingWheelMask = new WaittingWheelMask();
		if (m_activity == null)
			return;
		if (m_pageID == DataConfig.DEFAULT_ID)
			return;

		FragmentTransaction transaction = m_activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(m_pageID, waittingWheelMask, waittingWheelMask.getClass().getName());
		transaction.commit();
	}

	public void dialogClose()
	{
		FragmentManager     fgManager           = m_activity.getSupportFragmentManager();
		Fragment            fragment            = fgManager.findFragmentByTag(WaittingWheelMask.class.getName());
		FragmentTransaction fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();

		m_activity = null;
		m_pageID = DataConfig.DEFAULT_ID;
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
		//		m_waitProgressDialog = new ProgressDialog(context);
		//		m_waitProgressDialog.setMessage(m_waitTips);
		//		m_initFlag = true;
		//		m_waitTimeByMilliSeconds = waitTimeByMilliSecond;
		//		m_waitTimerTask.schedule(m_waitTimeByMilliSeconds);
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

	public void setM_pageID(int m_pageID)
	{
		this.m_pageID = m_pageID;
	}

	public void setM_activity(BaseActivity m_activity)
	{
		this.m_activity = m_activity;
	}
}
