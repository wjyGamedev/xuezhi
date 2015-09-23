package com.xuezhi_client.work_flow.calendar_flow.medication_details_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.work_flow.calendar_flow.medication_details_page.ui.MedicationDetailsActivity;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicationDetailsMsgHandler extends BaseUIMsgHandler
{
	public MedicationDetailsMsgHandler(MedicationDetailsActivity activity)
	{
		super(activity);
	}

	public void go2MainPage()
	{
		MedicationDetailsActivity activity = (MedicationDetailsActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}
}
