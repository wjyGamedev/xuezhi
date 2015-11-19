package com.xuezhi_client.work_flow.calendar_flow.calender_page.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DNoTakeMedicinePerDay;
import com.xuezhi_client.data_module.xuezhi_data.data.DNoTakeMedicinePerMonth;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerDay;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerMonth;
import com.xuezhi_client.work_flow.calendar_flow.calender_page.msg_handler.CalenderMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import calendar.CalendarDay;
import calendar.calendar.MaterialCalendarView;
import calendar.calendar.OnDateChangedListener;
import calendar.calendar.OnMonthChangedListener;
import calendar.month.MonthView;

/**
 * Created by Administrator on 2015/9/22.
 */
public class CalenderActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.calendar_view)                          MaterialCalendarView m_calendarView = null;    //calendarview
	@Bind (R.id.selected_day_taked_medicine_history_tv) TextView             m_selectedTV   = null;
	@Bind (R.id.selected_day_taked_medicine_history_iv) ImageView            m_selectedIV   = null;

	private BottomCommon m_bottomCommon = null;

	//logical
	private CalenderMsgHandler m_calenderMsgHandler = new CalenderMsgHandler(this);
	private ClickBottomBtn     m_clickBottomBtn     = new ClickBottomBtn();

	private SelectorNotTakedDecorator m_selectorNotTakedDecorator = new SelectorNotTakedDecorator();
	private SelectorTakedDecorator    m_selectorTakedDecorator    = new SelectorTakedDecorator();

	private HandleTakedDecorateListener    m_handleTakedDecorateListener    = new HandleTakedDecorateListener();
	private HandleNotTakedDecorateListener m_handleNotTakedDecorateListener = new HandleNotTakedDecorateListener();
	private HandleDateChangedClickEvent    m_handleDateChangedClickEvent    = new HandleDateChangedClickEvent();
	private HandleMonthChangedClickEvent   mHandleMonthChangedClickEvent    = new HandleMonthChangedClickEvent();


	private SimpleDateFormat m_ymdSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	private Calendar m_selectedDay = Calendar.getInstance();
	private Calendar mCurrMonth    = Calendar.getInstance();

	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_calendar);
		return this;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
		m_calenderMsgHandler.initAction();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
	}

	@Override
	public void onDestoryAction()
	{

	}


	class HandleMonthChangedClickEvent implements OnMonthChangedListener
	{

		@Override
		public void onMonthChanged(MaterialCalendarView widget, CalendarDay date)
		{
			Calendar currCalendar = Calendar.getInstance();
			date.copyTo(currCalendar);
			mCurrMonth.set(currCalendar.get(Calendar.YEAR), currCalendar.get(Calendar.MONTH), currCalendar.get(Calendar.DAY_OF_MONTH));
		}
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
			Calendar tmpCalendar = Calendar.getInstance();
			date.copyTo(tmpCalendar);

			int tmpYear  = tmpCalendar.get(Calendar.YEAR);
			int tmpMonth = tmpCalendar.get(Calendar.MONTH);
			int tmpDay   = tmpCalendar.get(Calendar.DAY_OF_MONTH);

			//01. 大于today return
			Calendar today = Calendar.getInstance();
			if (tmpYear > today.get(Calendar.YEAR) ||
					(tmpYear == today.get(Calendar.YEAR) && tmpMonth > today.get(Calendar.MONTH)) ||
					(tmpYear == today.get(Calendar.YEAR) && tmpMonth == today.get(Calendar.MONTH) && tmpDay > today.get(Calendar
																																.DAY_OF_MONTH)))
			{
				return;
			}

			//02. 等于今天，set
			if (tmpYear == today.get(Calendar.YEAR) && tmpMonth == today.get(Calendar.MONTH) && tmpDay == today.get(Calendar.DAY_OF_MONTH) )
			{
				setSelectedTakeMedicineTime(tmpCalendar);
				return;
			}

			//03. 小于今天
			//既没有notakehistory，有没有takehistory
			DNoTakeMedicinePerMonth noTakeMedicinePerMonth = DBusinessData.GetInstance().getNoTakeMedicineList().getMedicalHistoryBySelectedMonth(tmpCalendar);
			DTakeMedicinePerMonth takeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList().getMedicalHistoryBySelectedMonth(tmpCalendar);
			if (noTakeMedicinePerMonth == null && takeMedicinePerMonth == null)
			{
				return;
			}

			DNoTakeMedicinePerDay noTakeMedicinePerDay = null;
			DTakeMedicinePerDay takeMedicinePerDay = null;
			if (noTakeMedicinePerMonth != null)
			{
				noTakeMedicinePerDay = noTakeMedicinePerMonth.getMedicalHistoryBySelectedDay(tmpCalendar);
			}
			if (takeMedicinePerMonth != null)
			{
				takeMedicinePerDay = takeMedicinePerMonth.getMedicalHistoryBySelectedDay(tmpCalendar);
			}

			if (noTakeMedicinePerDay == null && takeMedicinePerDay == null)
			{
				return;
			}

			boolean emptyNoTake = true;
			boolean emptyTake = true;
			if (noTakeMedicinePerDay != null)
			{
				emptyNoTake = noTakeMedicinePerDay.getNoTakeMedicines().isEmpty();
			}
			if (takeMedicinePerDay != null)
			{
				emptyTake = takeMedicinePerDay.getTakeMedicines().isEmpty();
			}
			if (emptyNoTake == false || emptyTake == false)
			{
				setSelectedTakeMedicineTime(tmpCalendar);
			}
			return;
		}
	}

	class HandleTakedDecorateListener implements BaseSelectorDecorator.OnShouldDecorateListener
	{
		@Override
		public boolean shouldDecorate(CalendarDay day, MonthView monthView)
		{
			Calendar currCalendar = Calendar.getInstance();
			day.copyTo(currCalendar);

			//01. 大于今天，return false
			Calendar today = Calendar.getInstance();
			if (currCalendar.get(Calendar.YEAR) > today.get(Calendar.YEAR))
			{
				return false;
			}
			else if (currCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && currCalendar.get(Calendar.MONTH) > today.get(Calendar
																																		 .MONTH))
			{
				return false;
			}
			else if (currCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
					currCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
					currCalendar.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))
			{
				return false;
			}

			//02. 既没有notakehistory，有没有takehistory
			DNoTakeMedicinePerMonth noTakeMedicinePerMonth = DBusinessData.GetInstance().getNoTakeMedicineList().getMedicalHistoryBySelectedMonth(currCalendar);
			DTakeMedicinePerMonth takeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList().getMedicalHistoryBySelectedMonth(currCalendar);
			if (noTakeMedicinePerMonth == null && takeMedicinePerMonth == null)
			{
				return false;
			}

			DNoTakeMedicinePerDay noTakeMedicinePerDay = null;
			DTakeMedicinePerDay takeMedicinePerDay = null;
			if (noTakeMedicinePerMonth != null)
			{
				noTakeMedicinePerDay = noTakeMedicinePerMonth.getMedicalHistoryBySelectedDay(currCalendar);
			}
			if (takeMedicinePerMonth != null)
			{
				takeMedicinePerDay = takeMedicinePerMonth.getMedicalHistoryBySelectedDay(currCalendar);
			}

			if (noTakeMedicinePerDay == null && takeMedicinePerDay == null)
			{
				return false;
			}

			boolean emptyNoTake = true;
			boolean emptyTake = true;
			if (noTakeMedicinePerDay != null)
			{
				emptyNoTake = noTakeMedicinePerDay.getNoTakeMedicines().isEmpty();
			}
			if (takeMedicinePerDay != null)
			{
				emptyTake = takeMedicinePerDay.getTakeMedicines().isEmpty();
			}
			if (emptyNoTake == true && emptyTake == false)
			{
				//如果是今天返回false，不是今天true
				if (currCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
						currCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
						currCalendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))
				{
					return false;
				}
				else
				{
					return true;

				}
			}
			return false;
		}
	}

	class HandleNotTakedDecorateListener implements BaseSelectorDecorator.OnShouldDecorateListener
	{
		@Override
		public boolean shouldDecorate(CalendarDay day, MonthView monthView)
		{
			Calendar currCalendar = Calendar.getInstance();
			day.copyTo(currCalendar);

			//01. 大于今天，return false
			Calendar today = Calendar.getInstance();
			if (currCalendar.get(Calendar.YEAR) > today.get(Calendar.YEAR))
			{
				return false;
			}
			else if (currCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && currCalendar.get(Calendar.MONTH) > today.get(Calendar
																																		 .MONTH))
			{
				return false;
			}
			else if (currCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
					currCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
					currCalendar.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))
			{
				return false;
			}


			DNoTakeMedicinePerMonth noTakeMedicinePerMonth = DBusinessData.GetInstance().getNoTakeMedicineList()
																		  .getMedicalHistoryBySelectedMonth(currCalendar
																										   );
			if (noTakeMedicinePerMonth == null)
				return false;

			DNoTakeMedicinePerDay noTakeMedicinePerDay = noTakeMedicinePerMonth.getMedicalHistoryBySelectedDay(currCalendar);
			if (noTakeMedicinePerDay == null)
			{
				//如果是今天返回true，不是今天false
				if (currCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
						currCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
						currCalendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))
				{
					return true;
				}
				else
				{
					return false;

				}

			}

			return (!noTakeMedicinePerDay.getNoTakeMedicines().isEmpty());
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
		m_calendarView.setMonthChangedListener(mHandleMonthChangedClickEvent);

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

		Map.Entry<Calendar, DTakeMedicinePerDay> entry              = iterator.next();
		DTakeMedicinePerDay                      takeMedicinePerDay = entry.getValue();
		if (takeMedicinePerDay == null)
			return;

		if (takeMedicinePerDay.getTakeMedicines().isEmpty())
			return;

		DTakeMedicine takeMedicine = takeMedicinePerDay.getTakeMedicines().get(0);
		Calendar      takeCalendar = takeMedicine.getTakeCalendar();
		m_selectedIV.setVisibility(View.VISIBLE);
		setSelectedTakeMedicineTime(takeCalendar);

		mCurrMonth.setTime(m_selectedDay.getTime());
	}

	private void setSelectedTakeMedicineTime(Calendar selectedDay)
	{
		if (selectedDay == null)
		{
			return;
		}

		m_selectedDay.set(selectedDay.get(Calendar.YEAR), selectedDay.get(Calendar.MONTH), selectedDay.get(Calendar.DAY_OF_MONTH));

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
			m_calenderMsgHandler.go2SelectedMonthChartPage(mCurrMonth);
		}
	}

}