/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.select_date.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/7		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.select_date_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.select_date_page.msg_handler.SelectDateMsgHandler;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import calendar.calendar.MaterialCalendarView;

public class SelectDateActivity extends Activity
{
	//widget
	private                     HeaderCommon         m_headerCommon = null;//title
	@Bind (R.id.calendar_view)  MaterialCalendarView m_calendarView = null;    //calendarview
	@Bind (R.id.select_date_id) TextView             m_selectDateTV = null;
	private                     BottomCommon         m_bottomCommon = null; //bottom

	//logical
	private SelectDateMsgHandler   m_selectDateMsgHandler   = new SelectDateMsgHandler(this);
	private BottomCommonClickEvent m_bottomCommonClickEvent = new BottomCommonClickEvent();

	private Button m_bottomBtn = null;    //bottom

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
	class BottomCommonClickEvent implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			//01.
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

		m_calendarView.setShowOtherDates(true);
		m_calendarView.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
		m_calendarView.setDateTextAppearance(R.style.TextAppearance_AppCompat_Medium);
		m_calendarView.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);

		Calendar minCalendar = Calendar.getInstance();
		m_calendarView.setMinimumDate(minCalendar);

		Calendar maxCalendar = Calendar.getInstance();
		int monthIndex = maxCalendar.get(Calendar.MONTH);
		maxCalendar.set(Calendar.MONTH, monthIndex + DataConfig.MAX_MONTH_APPOINTMENT_NURSING);
		int maxDay = maxCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		maxCalendar.set(Calendar.DAY_OF_MONTH, maxDay);
		m_calendarView.setMaximumDate(maxCalendar);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.confirm_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_bottomCommonClickEvent);

	}

}
