package com.xuezhi_client.work_flow.calendar_flow.calender_page.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerDay;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerMonth;
import com.xuezhi_client.work_flow.calendar_flow.calender_page.msg_handler.CalenderMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import calendar.CalendarDay;
import calendar.calendar.MaterialCalendarView;
import calendar.calendar.OnDateChangedListener;
import calendar.month.MonthView;

/**
 * Created by Administrator on 2015/9/22.
 */
public class CalenderActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.calendar_view)                          MaterialCalendarView m_calendarView        = null;    //calendarview
	@Bind (R.id.selected_day_taked_medicine_history_tv) TextView             m_selectedTV          = null;
	@Bind (R.id.selected_day_taked_medicine_history_iv) ImageView            m_selectedIV          = null;
	@Bind (R.id.selected_day_region_ll)                 LinearLayout         m_selectedDayRegionLL = null;

	private BottomCommon m_bottomCommon = null;

	//logical
	private CalenderMsgHandler m_calenderMsgHandler = new CalenderMsgHandler(this);
	private ClickBottomBtn     m_clickBottomBtn     = new ClickBottomBtn();

	private SelectorNotTakedDecorator m_selectorNotTakedDecorator = new SelectorNotTakedDecorator();
	private SelectorTakedDecorator    m_selectorTakedDecorator    = new SelectorTakedDecorator();

	private HandleTakedDecorateListener    m_handleTakedDecorateListener    = new HandleTakedDecorateListener();
	private HandleNotTakedDecorateListener m_handleNotTakedDecorateListener = new HandleNotTakedDecorateListener();
	private HandleDateChangedClickEvent    m_handleDateChangedClickEvent    = new HandleDateChangedClickEvent();

	private SimpleDateFormat m_ymdSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	private Calendar m_selectedDay = Calendar.getInstance();

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		ButterKnife.bind(this);

		init();
	}

	class HandleDateChangedClickEvent implements OnDateChangedListener
	{
		@Override
		public void onDateChanged(
				@NonNull
				MaterialCalendarView widget,
				@Nullable
				CalendarDay date)
		{
			date.copyTo(m_selectedDay);

			DTakeMedicinePerMonth takeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList()
																	  .getMedicalHistoryBySelectedMonth(m_selectedDay
																									   );
			if (takeMedicinePerMonth == null)
				return;

			DTakeMedicinePerDay takeMedicinePerDay = takeMedicinePerMonth.getMedicalHistoryBySelectedDay(m_selectedDay);
			if (takeMedicinePerDay == null)
				return;

			if (!takeMedicinePerDay.getTakeMedicines().isEmpty())
				setSelectedTakeMedicineTime(m_selectedDay);
		}
	}

	class HandleTakedDecorateListener implements BaseSelectorDecorator.OnShouldDecorateListener
	{
		@Override
		public boolean shouldDecorate(CalendarDay day, MonthView monthView)
		{
			Calendar currCalendar = Calendar.getInstance();
			day.copyTo(currCalendar);

			//01. 如果当前时间小于提醒时间，则return false。
			ArrayList<DMedicinePrompt> medicinePrompts = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPrompts();
			if (medicinePrompts.isEmpty())
				return false;

			Calendar minCalendar = Calendar.getInstance();
			for (DMedicinePrompt medicinePrompt : medicinePrompts)
			{
				Calendar addCalendar = medicinePrompt.getAddCalendar();
				if (minCalendar.get(Calendar.YEAR) > addCalendar.get(Calendar.YEAR))
				{
					minCalendar.setTime(addCalendar.getTime());
					break;
				}
				else if (minCalendar.get(Calendar.YEAR) == addCalendar.get(Calendar.YEAR)	&&
						minCalendar.get(Calendar.MONTH) > addCalendar.get(Calendar.MONTH))
				{
					minCalendar.setTime(addCalendar.getTime());
					break;
				}
				else if	(minCalendar.get(Calendar.YEAR) == addCalendar.get(Calendar.YEAR)	&&
						minCalendar.get(Calendar.MONTH) == addCalendar.get(Calendar.MONTH)	&&
						minCalendar.get(Calendar.DAY_OF_MONTH) > addCalendar.get(Calendar.DAY_OF_MONTH))
				{
					minCalendar.setTime(addCalendar.getTime());
					break;
				}
			}

			if (currCalendar.get(Calendar.YEAR) < minCalendar.get(Calendar.YEAR))
			{
				return false;
			}
			else if (currCalendar.get(Calendar.YEAR) == minCalendar.get(Calendar.YEAR)	&&
					currCalendar.get(Calendar.MONTH) < minCalendar.get(Calendar.MONTH))
			{
				return false;
			}
			else if	(currCalendar.get(Calendar.YEAR) == minCalendar.get(Calendar.YEAR)	&&
					currCalendar.get(Calendar.MONTH) == minCalendar.get(Calendar.MONTH)	&&
					currCalendar.get(Calendar.DAY_OF_MONTH) < minCalendar.get(Calendar.DAY_OF_MONTH))
			{
				return false;
			}

			//02. 大于今天，return false
			Calendar today = Calendar.getInstance();
			if (currCalendar.get(Calendar.YEAR) > today.get(Calendar.YEAR))
			{
				return false;
			}
			else if (currCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)	&&
					currCalendar.get(Calendar.MONTH) > today.get(Calendar.MONTH))
			{
				return false;
			}
			else if	(currCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)	&&
					currCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)	&&
					currCalendar.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))
			{
				return false;
			}

			//03. 当日提醒的数量如果小于历史信息的数量，return false。
			//由于走到这里，提醒数量不为0.所以历史信息要大于0
			DTakeMedicinePerMonth takeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList()
																	  .getMedicalHistoryBySelectedMonth(currCalendar
																									   );
			if (takeMedicinePerMonth == null)
				return false;

			DTakeMedicinePerDay takeMedicinePerDay = takeMedicinePerMonth.getMedicalHistoryBySelectedDay(currCalendar);
			if (takeMedicinePerDay == null)
				return false;

			//获取当日提醒数量
			int count = 0;
			for (DMedicinePrompt medicinePrompt : medicinePrompts)
			{
				Calendar addCalendar = medicinePrompt.getAddCalendar();
				if (currCalendar.get(Calendar.YEAR) > addCalendar.get(Calendar.YEAR))
				{
					count++;
					continue;
				}
				else if (currCalendar.get(Calendar.YEAR) == addCalendar.get(Calendar.YEAR)	&&
						currCalendar.get(Calendar.MONTH) > addCalendar.get(Calendar.MONTH))
				{
					count++;
					continue;
				}
				else if	(currCalendar.get(Calendar.YEAR) == addCalendar.get(Calendar.YEAR)	&&
						currCalendar.get(Calendar.MONTH) == addCalendar.get(Calendar.MONTH)	&&
						currCalendar.get(Calendar.DAY_OF_MONTH) >= addCalendar.get(Calendar.DAY_OF_MONTH))
				{
					count++;
					continue;
				}
			}

			return (count == takeMedicinePerDay.getTakeMedicines().size());

		}
	}

	class HandleNotTakedDecorateListener implements BaseSelectorDecorator.OnShouldDecorateListener
	{
		@Override
		public boolean shouldDecorate(CalendarDay day, MonthView monthView)
		{
			Calendar currCalendar = Calendar.getInstance();
			day.copyTo(currCalendar);

			//01. 如果当前时间小于提醒时间，则return false。
			ArrayList<DMedicinePrompt> medicinePrompts = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPrompts();
			if (medicinePrompts.isEmpty())
				return false;

			Calendar minCalendar = Calendar.getInstance();
			for (DMedicinePrompt medicinePrompt : medicinePrompts)
			{
				Calendar addCalendar = medicinePrompt.getAddCalendar();
				if (minCalendar.get(Calendar.YEAR) > addCalendar.get(Calendar.YEAR))
				{
					minCalendar.setTime(addCalendar.getTime());
					break;
				}
				else if (minCalendar.get(Calendar.YEAR) == addCalendar.get(Calendar.YEAR)	&&
						minCalendar.get(Calendar.MONTH) > addCalendar.get(Calendar.MONTH))
				{
					minCalendar.setTime(addCalendar.getTime());
					break;
				}
				else if	(minCalendar.get(Calendar.YEAR) == addCalendar.get(Calendar.YEAR)	&&
						minCalendar.get(Calendar.MONTH) == addCalendar.get(Calendar.MONTH)	&&
						minCalendar.get(Calendar.DAY_OF_MONTH) > addCalendar.get(Calendar.DAY_OF_MONTH))
				{
					minCalendar.setTime(addCalendar.getTime());
					break;
				}
			}

			if (currCalendar.get(Calendar.YEAR) < minCalendar.get(Calendar.YEAR))
			{
				return false;
			}
			else if (currCalendar.get(Calendar.YEAR) == minCalendar.get(Calendar.YEAR)	&&
					currCalendar.get(Calendar.MONTH) < minCalendar.get(Calendar.MONTH))
			{
				return false;
			}
			else if	(currCalendar.get(Calendar.YEAR) == minCalendar.get(Calendar.YEAR)	&&
					currCalendar.get(Calendar.MONTH) == minCalendar.get(Calendar.MONTH)	&&
					currCalendar.get(Calendar.DAY_OF_MONTH) < minCalendar.get(Calendar.DAY_OF_MONTH))
			{
				return false;
			}

			//02. 大于今天，return false
			Calendar today = Calendar.getInstance();
			if (currCalendar.get(Calendar.YEAR) > today.get(Calendar.YEAR))
			{
				return false;
			}
			else if (currCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)	&&
					currCalendar.get(Calendar.MONTH) > today.get(Calendar.MONTH))
			{
				return false;
			}
			else if	(currCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)	&&
					currCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)	&&
					currCalendar.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))
			{
				return false;
			}

			//03. 当日提醒的数量如果小于历史信息的数量，return false。
			//由于走到这里，提醒数量不为0.所以历史信息要大于0
			DTakeMedicinePerMonth takeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList()
																	  .getMedicalHistoryBySelectedMonth(currCalendar
																									   );
			if (takeMedicinePerMonth == null)
				return false;

			DTakeMedicinePerDay takeMedicinePerDay = takeMedicinePerMonth.getMedicalHistoryBySelectedDay(currCalendar);
			if (takeMedicinePerDay == null)
				return false;

			//获取当日提醒数量
			int count = 0;
			for (DMedicinePrompt medicinePrompt : medicinePrompts)
			{
				Calendar addCalendar = medicinePrompt.getAddCalendar();
				if (currCalendar.get(Calendar.YEAR) > addCalendar.get(Calendar.YEAR))
				{
					count++;
					continue;
				}
				else if (currCalendar.get(Calendar.YEAR) == addCalendar.get(Calendar.YEAR)	&&
						currCalendar.get(Calendar.MONTH) > addCalendar.get(Calendar.MONTH))
				{
					count++;
					continue;
				}
				else if	(currCalendar.get(Calendar.YEAR) == addCalendar.get(Calendar.YEAR)	&&
						currCalendar.get(Calendar.MONTH) == addCalendar.get(Calendar.MONTH)	&&
						currCalendar.get(Calendar.DAY_OF_MONTH) >= addCalendar.get(Calendar.DAY_OF_MONTH))
				{
					count++;
					continue;
				}
			}

			return (count != takeMedicinePerDay.getTakeMedicines().size());
		}
	}


	@OnClick (R.id.selected_day_region_ll)
	public void clickSelectedDayRegion()
	{
		m_calenderMsgHandler.go2SelectedTakenMedicineHistoryPage(m_selectedDay);
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.calendar_page_title_text);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.calendar_bottom_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);

		m_calendarView.setShowOtherDates(true);
		m_calendarView.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
		m_calendarView.setDateTextAppearance(R.style.TextAppearance_AppCompat_Medium);
		m_calendarView.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);
		m_calendarView.setDateChangedListener(m_handleDateChangedClickEvent);

		m_selectorNotTakedDecorator.setOnShouldDecorateListener(m_handleNotTakedDecorateListener);
		m_selectorTakedDecorator.setOnShouldDecorateListener(m_handleTakedDecorateListener);
		m_calendarView.addDecorators(m_selectorTakedDecorator, m_selectorNotTakedDecorator);


		Calendar today = Calendar.getInstance();
		DTakeMedicinePerMonth takeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList()
																  .getMedicalHistoryBySelectedMonth(today
																								   );


		m_selectedTV.setText(R.string.calendar_no_taked_medicine_history);
		m_selectedIV.setVisibility(View.INVISIBLE);
		if (takeMedicinePerMonth == null)
			return;

		HashMap<Calendar, DTakeMedicinePerDay> takeMedicinePerDayHashMap = takeMedicinePerMonth.getTakeMedicinePerDayHashMap();
		if (takeMedicinePerDayHashMap.isEmpty())
			return;

		Iterator<Map.Entry<Calendar, DTakeMedicinePerDay>> iterator = takeMedicinePerDayHashMap.entrySet().iterator();
		if (!iterator.hasNext())
			return;

		Map.Entry<Calendar, DTakeMedicinePerDay> entry = iterator.next();
		DTakeMedicinePerDay takeMedicinePerDay = entry.getValue();
		if (takeMedicinePerDay == null)
			return;

		if (takeMedicinePerDay.getTakeMedicines().isEmpty())
			return;

		DTakeMedicine takeMedicine = takeMedicinePerDay.getTakeMedicines().get(0);
		Calendar takeCalendar = takeMedicine.getTakeCalendar();
		String display = m_ymdSDF.format(takeCalendar.getTime());
		m_selectedTV.setText(display);
		m_selectedIV.setVisibility(View.VISIBLE);
		m_selectedDay = takeCalendar;

	}

	private void setSelectedTakeMedicineTime(Calendar selectedDay)
	{
		if (selectedDay == null)
		{
			return;
		}
		String display = m_ymdSDF.format(selectedDay.getTime());
		m_selectedTV.setText(display);
	}

	/**
	 * overrider func
	 */
	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_calenderMsgHandler.go2MainPage();
		}
	}

}