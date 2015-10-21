package com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_page.ui.MedicationReminderActivity;
import com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui.MedicineReminderAddActivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicationReminderMsgHandler extends BaseUIMsgHandler
{
//	@Override
//	protected void init()
//	{
//		super.init();
//		m_eventBus.register(this);
//	}

	public MedicationReminderMsgHandler(MedicationReminderActivity activity)
	{
		super(activity);
	}

	public void go2MedicineReminderAddPage()
	{
		//01. 跳转到药品管理设置页面
		MedicationReminderActivity medicationReminderActivity = (MedicationReminderActivity)m_context;

		Intent intent = new Intent(medicationReminderActivity, MedicineReminderAddActivity.class);
		medicationReminderActivity.startActivity(intent);

		return;
	}
}
