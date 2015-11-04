package com.xuezhi_client.work_flow.calendar_flow.calender_page.msg_handler;

import android.content.Intent;
import android.os.Bundle;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicine;
import com.xuezhi_client.net.config.TakeMedicineConfig;
import com.xuezhi_client.work_flow.calendar_flow.calender_page.ui.CalenderActivity;
import com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.ui.SelectedTakenMedicineHistoryActivity;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class CalenderMsgHandler extends BaseUIMsgHandler
{
	public CalenderMsgHandler(CalenderActivity activity)
	{
		super(activity);
	}

	public void go2MainPage()
	{
		CalenderActivity activity = (CalenderActivity)m_context;
		activity.finish();

		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}

	public void go2SelectedTakenMedicineHistoryPage(DTakeMedicine selectedTakeMedicine)
	{
		if (selectedTakeMedicine == null)
			return;

		CalenderActivity activity = (CalenderActivity)m_context;

		Intent intent = new Intent(activity, SelectedTakenMedicineHistoryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(TakeMedicineConfig.DTAKEMEDICINE, selectedTakeMedicine);
		intent.putExtras(bundle);
		activity.startActivity(intent);

	}
}
