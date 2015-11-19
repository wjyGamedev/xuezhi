package com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.ui.SelectedTakenMedicineHistoryActivity;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicationDetailsMsgHandler extends BaseUIMsgHandler
{
	public MedicationDetailsMsgHandler(SelectedTakenMedicineHistoryActivity activity)
	{
		super(activity);
	}

	public void go2MainPage()
	{
		SelectedTakenMedicineHistoryActivity activity = (SelectedTakenMedicineHistoryActivity)m_context;
		activity.finish();
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}

	public void go2TakenFragment()
	{

	}

	public void go2NoTakenFragment()
	{

	}
}
