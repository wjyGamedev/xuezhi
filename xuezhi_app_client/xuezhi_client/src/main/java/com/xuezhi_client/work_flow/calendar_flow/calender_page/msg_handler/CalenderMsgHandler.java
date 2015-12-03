package com.xuezhi_client.work_flow.calendar_flow.calender_page.msg_handler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.RotationWaittingDialog;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerNoTakeMedicineGetHistoryListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerTakeMedicineGetHistoryListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestTakeMedicineGetHistoryListEvent;
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
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

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
		//		RequestNoTakeMedicineGetHistoryListEvent event = new RequestNoTakeMedicineGetHistoryListEvent();
		//		event.setUID(DAccount.GetInstance().getId());
		//		Calendar calendar = Calendar.getInstance();
		//		event.setCurrMonth(calendar);
		//		DBusinessMsgHandler.GetInstance().requestNoTakeMedicineGetHistoryListAction(event);
	}

	public void onEventMainThread(AnswerNoTakeMedicineGetHistoryListEvent event)
	{
		CalenderActivity                       activity            = (CalenderActivity)m_context;
		RequestTakeMedicineGetHistoryListEvent noTakeMedicineEvent = new RequestTakeMedicineGetHistoryListEvent();
		noTakeMedicineEvent.setUID(DAccount.GetInstance().getId());
		noTakeMedicineEvent.setCurrMonth(activity.getCurrMonth());
		DBusinessMsgHandler.GetInstance().requestTakeMedicineGetHistoryListAction(noTakeMedicineEvent);
	}

	public void onEventMainThread(AnswerTakeMedicineGetHistoryListEvent event)
	{
		CalenderActivity activity              = (CalenderActivity)m_context;
		ViewPager        monthCalendarRegionVP = activity.getCalendarView().getMonthCalendarRegionVP();
		if (monthCalendarRegionVP != null)
		{
//			int itemPosIndex = monthCalendarRegionVP.getCurrentItem();
//			if (activity.getClickArrowDirection().equals(CalendarFlowConfig.ARROW_DIRECTION_LEFT))
//			{
//				activity.getCalendarView().getMonthCalendarRegionVP().setCurrentItem(itemPosIndex - 1, true);
//				activity.setClickArrowDirection(null);
//				activity.getCalendarView().getMonthAdapter().invalidateDecorators();
//				RotationWaittingDialog.GetInstance().dialogClose();
//			}
//			else if (activity.getClickArrowDirection().equals(CalendarFlowConfig.ARROW_DIRECTION_RIGHT))
//			{
//				activity.getCalendarView().getMonthCalendarRegionVP().setCurrentItem(itemPosIndex + 1, true);
//				activity.setClickArrowDirection(null);
//				activity.getCalendarView().getMonthAdapter().invalidateDecorators();
//				RotationWaittingDialog.GetInstance().dialogClose();
//			}
			activity.getCalendarView().getMonthAdapter().invalidateDecorators();
			RotationWaittingDialog.GetInstance().dialogClose();
		}
	}
}
