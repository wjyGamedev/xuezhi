package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_page.ui.MedicineReminderActivity;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.ui.MedicineReminderSettingActivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicationReminderSettingMsgHandler extends BaseUIMsgHandler
{
	public MedicationReminderSettingMsgHandler(MedicineReminderSettingActivity activity)
	{
		super(activity);
	}

	public void go2MedicineReminderPage()
	{
		MedicineReminderSettingActivity activity = (MedicineReminderSettingActivity)m_context;

		Intent intent = new Intent(activity, MedicineReminderActivity.class);
		activity.startActivity(intent);
	}
}
