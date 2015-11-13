/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/20		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.module.frame.BaseFragment;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.msg_handler.MedicineReminderAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectMedicineTimeFragment extends BaseFragment
{
	//widget
	@Bind (R.id.header_bg_ll)   LinearLayout m_headerBGLL   = null;
	@Bind (R.id.func_region_ll) LinearLayout m_funcRegionLL = null;
	@Bind (R.id.timePicker)     TimePicker   m_timePicker   = null;
	@Bind (R.id.confirm_btn)    Button       m_confirmBtn   = null;
	@Bind (R.id.bottom_bg_ll)   LinearLayout m_bottomBGLL   = null;

	protected LayoutInflater m_layoutInflater = null;
	protected View           m_view           = null;

	//logical
	private MedicineReminderAddMsgHandler m_medicineReminderAddMsgHandler = null;
	private HandleTimeChanged             m_handleTimeChanged             = new HandleTimeChanged();

	//date
	private Calendar m_calendar = null;

	@Override
	public View onCreateViewAction(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_layoutInflater = inflater;
		m_view = m_layoutInflater.inflate(R.layout.fragment_select_medicine_remainder_time, container, false);
		return m_view;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
	}

	@Override
	public void onDestoryViewAction()
	{

	}

	@Override
	public BaseFragment getOwner()
	{
		return this;
	}

	/**
	 * widget:func
	 */
	@OnClick (R.id.header_bg_ll)
	public void clickHeaderBGLL()
	{
		cancelAction();
	}

	@OnClick (R.id.confirm_btn)
	public void clickConfirm()
	{
		m_timePicker.clearFocus();

		if (m_timePicker.is24HourView())
		{
			m_calendar.set(Calendar.HOUR_OF_DAY, m_timePicker.getCurrentHour());
			m_calendar.set(Calendar.MINUTE, m_timePicker.getCurrentMinute());
		}
		else
		{
			m_timePicker.setIs24HourView(true);
			m_calendar.set(Calendar.HOUR_OF_DAY, m_timePicker.getCurrentHour());
			m_calendar.set(Calendar.MINUTE, m_timePicker.getCurrentMinute());
		}

		m_medicineReminderAddMsgHandler.setRemainderTime(m_calendar);
		cancelAction();
	}

	@OnClick (R.id.bottom_bg_ll)
	public void clickBottomBGLL()
	{
		cancelAction();
	}

	//防止点击穿透
	@OnClick (R.id.func_region_ll)
	public void preventPenetration(View v)
	{
		return;
	}

	/**
	 * override func
	 */
	//TODO:bufferKnife 暂不提供支持
	class HandleTimeChanged implements TimePicker.OnTimeChangedListener
	{

		@Override
		public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
		{
			if (m_timePicker.is24HourView())
			{
				m_calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				m_calendar.set(Calendar.MINUTE, minute);
			}
			else
			{
				m_timePicker.setIs24HourView(true);
				m_calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				m_calendar.set(Calendar.MINUTE, minute);
			}
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		//01. m_medicineReminderAddMsgHandler
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)getActivity();
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("MedicineReminderAddActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		m_medicineReminderAddMsgHandler = activity.getMedicineReminderAddMsgHandler();
		if (m_medicineReminderAddMsgHandler == null)
		{
			TipsDialog.GetInstance().setMsg("m_medicineReminderAddMsgHandler == null", activity);
			TipsDialog.GetInstance().show();
			return;
		}

		m_calendar = Calendar.getInstance();

		m_timePicker.setOnTimeChangedListener(m_handleTimeChanged);
		m_timePicker.setIs24HourView(true);
		m_timePicker.setCurrentHour(m_calendar.get(Calendar.HOUR_OF_DAY));
		m_timePicker.setCurrentMinute(m_calendar.get(Calendar.MINUTE));

		return;
	}

	private void cancelAction()
	{
		FragmentManager     fragmentManager     = getActivity().getSupportFragmentManager();
		Fragment            fragment            = fragmentManager.findFragmentByTag(SelectMedicineTimeFragment.class.getName());
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();
	}

}
