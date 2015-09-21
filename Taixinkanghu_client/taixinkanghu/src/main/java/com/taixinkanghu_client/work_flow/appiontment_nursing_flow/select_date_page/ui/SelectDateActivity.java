/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.select_date.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/7		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.select_date_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data.DNursingDate;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.select_date_page.msg_handler.SelectDateMsgHandler;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import calendar.CalendarDay;
import calendar.calendar.MaterialCalendarView;
import calendar.calendar.OnDateChangedListener;
import calendar.month.MonthView;

public class SelectDateActivity extends Activity
{
	//widget
	private                     HeaderCommon         m_headerCommon = null;//title
	@Bind (R.id.calendar_view)  MaterialCalendarView m_calendarView = null;    //calendarview
	@Bind (R.id.select_date_id) TextView             m_selectDateTV = null;
	private                     BottomCommon         m_bottomCommon = null; //bottom

	//logical
	private SelectDateMsgHandler m_selectDateMsgHandler = new SelectDateMsgHandler(this);
	private SelectBtnClickEvent  m_selectBtnClickEvent  = new SelectBtnClickEvent();
	private ResetBtnClickEvent   m_resetBtnClickEvent   = new ResetBtnClickEvent();

	private DNursingDate m_nursingDate = null;

	private HandleDateChangedClickEvent m_handleDateChangedClickEvent = new HandleDateChangedClickEvent();
	private SelectorBothDecorator       m_selectorBothDecorator       = new SelectorBothDecorator();
	private SelectorDayDecorator        m_selectorDayDecorator        = new SelectorDayDecorator();
	private SelectorNightDecorator      m_selectorNightDecorator      = new SelectorNightDecorator();
	private HandleBothDecorateListener  m_handleBothDecorateListener  = new HandleBothDecorateListener();
	private HandleDayDecorateListener   m_handleDayDecorateListener   = new HandleDayDecorateListener();
	private HandleNightDecorateListener m_handleNightDecorateListener = new HandleNightDecorateListener();
	private Button                      m_bottomBtn                   = null;    //bottom

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_date);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * widget event
	 */


	/**
	 * override func
	 */
	class SelectBtnClickEvent implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			//01. 将数据同步
			m_selectDateMsgHandler.setNursingDate(m_nursingDate);
			return;
		}
	}

	class ResetBtnClickEvent implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			//01. 清楚日历数据
			m_calendarView.clearupSelectedDate();

			//02. 清楚本地UI
			m_selectDateTV.setText(R.string.tips_select_date);

			//03. 清楚本地数据
			m_nursingDate = null;
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
			if (m_calendarView.getBeginDate() == null)
			{
				//清楚本地UI
				m_selectDateTV.setText(R.string.tips_select_date);
				return;
			}

			if (m_calendarView.getEndDate() == null)
			{
				//清楚本地UI
				m_selectDateTV.setText(R.string.tips_select_date);
				return;
			}

			Date beginDate = m_calendarView.getBeginDate().getDate();
			Date endDate = m_calendarView.getEndDate().getDate();
			HashMap<CalendarDay, Integer> selectedDateHashMap = m_calendarView.getSelectedDateHashMap();

			//01. 将要更新的数据保存在本地
			m_nursingDate = new DNursingDate(beginDate, endDate, selectedDateHashMap);

			//02. update ui
			m_selectDateTV.setText(m_nursingDate.getDateDescription());
			return;
		}
	}

	class HandleBothDecorateListener implements BaseSelectorDecorator.OnShouldDecorateListener
	{
		@Override
		public boolean shouldDecorate(CalendarDay day, MonthView monthView)
		{
			HashMap<CalendarDay, Integer> calendarDayIntegerHashMap = m_calendarView.getSelectedDateHashMap();

			if (monthView.getMonth().getMonth() != day.getMonth())
				return false;

			if (!monthView.isInRegion(day))
				return false;

			if (calendarDayIntegerHashMap.containsKey(day))
			{
				return (calendarDayIntegerHashMap.get(day) == EnumConfig.NurseServiceDayStatus.ALL.getId());
			}
			return false;
		}
	}

	class HandleDayDecorateListener implements BaseSelectorDecorator.OnShouldDecorateListener
	{

		@Override
		public boolean shouldDecorate(CalendarDay day, MonthView monthView)
		{
			HashMap<CalendarDay, Integer> calendarDayIntegerHashMap = m_calendarView.getSelectedDateHashMap();

			if (monthView.getMonth().getMonth() != day.getMonth())
				return false;

			if (!monthView.isInRegion(day))
				return false;

			if (calendarDayIntegerHashMap.containsKey(day))
			{
				return (calendarDayIntegerHashMap.get(day) == EnumConfig.NurseServiceDayStatus.DAY.getId());
			}
			return false;
		}
	}

	class HandleNightDecorateListener implements BaseSelectorDecorator.OnShouldDecorateListener
	{

		@Override
		public boolean shouldDecorate(CalendarDay day, MonthView monthView)
		{
			HashMap<CalendarDay, Integer> calendarDayIntegerHashMap = m_calendarView.getSelectedDateHashMap();

			if (monthView.getMonth().getMonth() != day.getMonth())
				return false;

			if (!monthView.isInRegion(day))
				return false;

			if (calendarDayIntegerHashMap.containsKey(day))
			{
				return (calendarDayIntegerHashMap.get(day) == EnumConfig.NurseServiceDayStatus.NIGHT.getId());
			}
			return false;
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		//01. widget
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.select_date_title);

		m_selectDateTV.setText(R.string.tips_select_date);

		m_calendarView.setShowOtherDates(true);
		m_calendarView.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
		m_calendarView.setDateTextAppearance(R.style.TextAppearance_AppCompat_Medium);
		m_calendarView.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);

		Calendar minCalendar = Calendar.getInstance();
		m_calendarView.setMinimumDate(minCalendar);

		Calendar maxCalendar = Calendar.getInstance();
		int      monthIndex  = maxCalendar.get(Calendar.MONTH);
		maxCalendar.set(Calendar.MONTH, monthIndex + DataConfig.MAX_MONTH_APPOINTMENT_NURSING);
		int maxDay = maxCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		maxCalendar.set(Calendar.DAY_OF_MONTH, maxDay);
		m_calendarView.setMaximumDate(maxCalendar);
		m_calendarView.setDateChangedListener(m_handleDateChangedClickEvent);

		m_selectorBothDecorator.setOnShouldDecorateListener(m_handleBothDecorateListener);
		m_selectorDayDecorator.setOnShouldDecorateListener(m_handleDayDecorateListener);
		m_selectorNightDecorator.setOnShouldDecorateListener(m_handleNightDecorateListener);
		m_calendarView.addDecorators(m_selectorBothDecorator, m_selectorDayDecorator, m_selectorNightDecorator);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.setBtnNum(2);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.reset_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_resetBtnClickEvent);
		m_bottomCommon.getCommonBottomBtn2().setText(R.string.confirm_btn_text);
		m_bottomCommon.getCommonBottomBtn2().setOnClickListener(m_selectBtnClickEvent);

	}


	/**
	 * func:get/set
	 */
	public TextView getSelectDateTV()
	{
		return m_selectDateTV;
	}
}
