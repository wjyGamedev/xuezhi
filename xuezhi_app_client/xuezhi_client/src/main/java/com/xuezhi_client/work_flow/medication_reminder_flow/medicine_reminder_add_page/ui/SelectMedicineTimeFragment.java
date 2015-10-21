/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/20		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.msg_handler.MedicineReminderAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectMedicineTimeFragment extends Fragment
{
	//widget
	@Bind (R.id.header_bg_ll) LinearLayout m_headerBGLL = null;
	@Bind (R.id.timePicker)   TimePicker   m_timePicker = null;
	@Bind (R.id.bottom_bg_ll) LinearLayout m_bottomBGLL = null;

	private   BottomCommon   m_bottomCommon   = null;

	protected LayoutInflater m_layoutInflater = null;
	protected View           m_view           = null;

	//logical
	private MedicineReminderAddMsgHandler m_medicineReminderAddMsgHandler = null;
	private HandleTimeChanged             m_handleTimeChanged             = new HandleTimeChanged();

	private Calendar m_calendar = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_layoutInflater = inflater;
		m_view = m_layoutInflater.inflate(R.layout.fragment_select_medicine_remainder_time, container, false);
		ButterKnife.bind(this, m_view);

		init();
		return m_view;
	}

	/**
	 * widget:func
	 */
	@OnClick (R.id.header_bg_ll)
	public void clickHeaderBGLL()
	{
		cancelAction();
	}

	@OnClick (R.id.bottom_bg_ll)
	public void clickBottomBGLL()
	{
		cancelAction();
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
			m_calendar = Calendar.getInstance();
			m_calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			m_calendar.set(Calendar.MINUTE, minute);
		}
	}


	/**
	 * inner func
	 */
	private void init()
	{
		m_bottomCommon = (BottomCommon)getActivity().getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.medication_reminder_save_btn_text);
//		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickSaveBtn);

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

		m_timePicker.setOnTimeChangedListener(m_handleTimeChanged);


		return;
	}

	private void cancelAction()
	{
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		Fragment fragment            = fragmentManager.findFragmentByTag(SelectMedicineTimeFragment.class.getName());
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();
	}

}
