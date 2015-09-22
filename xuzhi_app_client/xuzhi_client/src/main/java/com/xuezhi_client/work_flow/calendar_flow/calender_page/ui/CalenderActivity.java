package com.xuezhi_client.work_flow.calendar_flow.calender_page.ui;

import android.app.Activity;
import android.os.Bundle;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import calendar.calendar.MaterialCalendarView;

/**
 * Created by Administrator on 2015/9/22.
 */
public class CalenderActivity extends Activity
{
	//widget
	private                    HeaderCommon         m_headerCommon = null;//title
	@Bind (R.id.calendar_view) MaterialCalendarView m_calendarView = null;    //calendarview
	private                    BottomCommon         m_bottomCommon = null; //bottom


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		ButterKnife.bind(this);

//		init();
	}


}
