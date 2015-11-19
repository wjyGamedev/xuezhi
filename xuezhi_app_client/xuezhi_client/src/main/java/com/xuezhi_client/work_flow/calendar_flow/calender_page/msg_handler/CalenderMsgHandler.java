package com.xuezhi_client.work_flow.calendar_flow.calender_page.msg_handler;

import android.content.Intent;
import android.os.Bundle;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestNoTakeMedicineGetHistoryListEvent;
import com.xuezhi_client.net.config.TakeMedicineConfig;
import com.xuezhi_client.work_flow.calendar_flow.calender_page.ui.CalenderActivity;
import com.xuezhi_client.work_flow.calendar_flow.config.CalendarFlowConfig;
import com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.ui.SelectedTakenMedicineHistoryActivity;
import com.xuezhi_client.work_flow.calendar_flow.selected_month_chart_page.ui.SelectedMonthChartActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/9/23.
 */
public class CalenderMsgHandler extends BaseUIMsgHandler
{
	private SimpleDateFormat mYmdSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	public CalenderMsgHandler(CalenderActivity activity)
	{
		super(activity);
	}

	public void go2SelectedMonthChartPage(Calendar selectedMonth)
	{
		CalenderActivity activity = (CalenderActivity)m_context;

		String dateDisplay = mYmdSDF.format(selectedMonth.getTime());

		Intent intent = new Intent(activity, SelectedMonthChartActivity.class);
		intent.putExtra(CalendarFlowConfig.SELECTED_MONTH, dateDisplay);
		activity.startActivity(intent);
		return;
	}

	public void go2SelectedTakenMedicineHistoryPage(Calendar selectedDay)
	{
		if (selectedDay == null)
			return;

		CalenderActivity activity = (CalenderActivity)m_context;

		Intent intent = new Intent(activity, SelectedTakenMedicineHistoryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(TakeMedicineConfig.DATE, selectedDay);
		intent.putExtras(bundle);
		activity.startActivity(intent);

	}

	public void initAction()
	{
		RequestNoTakeMedicineGetHistoryListEvent event = new RequestNoTakeMedicineGetHistoryListEvent();
		event.setUID(DAccount.GetInstance().getId());
		Calendar calendar = Calendar.getInstance();
		event.setCurrMonth(calendar);
		DBusinessMsgHandler.GetInstance().requestNoTakeMedicineGetHistoryListAction(event);
	}
}
