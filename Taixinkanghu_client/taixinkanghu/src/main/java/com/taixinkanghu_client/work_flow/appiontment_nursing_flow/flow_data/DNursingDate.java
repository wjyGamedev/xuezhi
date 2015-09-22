/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.data.page.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/21		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data;

import com.module.data.DGlobal;
import com.module.util.logcal.LogicalUtil;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DateConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.nurse_list.util.NurseUtil;
import com.taixinkanghu_client.net.config.NurseBasicListConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import calendar.CalendarDay;

public class DNursingDate
{
	private SimpleDateFormat m_yearMonthDaySDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);
	private SimpleDateFormat m_monthDaySDF     = new SimpleDateFormat(DateConfig.PATTERN_DATE_MONTH_DAY);

	private Date                m_beginDate         = null;
	private Date                m_endDate           = null;
	private String              m_dateDescription   = null;
	private ArrayList<Calendar> m_allCalendarList   = new ArrayList<>();
	private ArrayList<Calendar> m_dayCalendarList   = new ArrayList<>();
	private ArrayList<Calendar> m_nightCalendarList = new ArrayList<>();
	private int                 m_allNum            = 0;
	private int                 m_dayNum            = 0;
	private int                 m_nightNum          = 0;

	public DNursingDate(Date beginDate, Date endDate, HashMap<CalendarDay, Integer> selectedDateHashMap)
	{
		m_beginDate = beginDate;
		m_endDate = endDate;
		postHandler(selectedDateHashMap);
	}

	public DNursingDate(Date beginDate, Date endDate)
	{
		m_beginDate = beginDate;
		m_endDate = endDate;
		HashMap<CalendarDay, Integer> selectedDateHashMap = NurseUtil.GetCalendarDayHashMap(beginDate, endDate);
		postHandler(selectedDateHashMap);
	}

	private void postHandler(HashMap<CalendarDay, Integer> selectedDateHashMap)
	{
		if (m_beginDate == null || m_endDate == null)
			return;

		//01. m_dateDescription
		String beginContent = m_monthDaySDF.format(m_beginDate);
		String endContent   = m_monthDaySDF.format(m_endDate);
		int    days         = LogicalUtil.GetDayNums(m_beginDate, m_endDate);
		String total        = DGlobal.GetInstance().getAppContext().getResources().getString(R.string.char_total);
		String day          = DGlobal.GetInstance().getAppContext().getResources().getString(R.string.char_day);
		m_dateDescription = beginContent + " - " + endContent + total + days + day;

		//02. m_allCalendarList, m_dayCalendarList, m_nightCalendarList
		m_allCalendarList.clear();
		m_dayCalendarList.clear();
		m_nightCalendarList.clear();

		Iterator<Map.Entry<CalendarDay, Integer>> iterator = selectedDateHashMap.entrySet().iterator();
		while (iterator.hasNext())
		{
			Map.Entry<CalendarDay, Integer> entry = iterator.next();
			CalendarDay calendarDay = (CalendarDay)entry.getKey();
			int selectedDateType = (int)entry.getValue();
			if (selectedDateType == EnumConfig.NurseServiceDayStatus.ALL.getId())
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(calendarDay.getDate());
				m_allCalendarList.add(calendar);
			}
			else if (selectedDateType == EnumConfig.NurseServiceDayStatus.DAY.getId())
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(calendarDay.getDate());
				m_dayCalendarList.add(calendar);
			}
			else if (selectedDateType == EnumConfig.NurseServiceDayStatus.NIGHT.getId())
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(calendarDay.getDate());
				m_nightCalendarList.add(calendar);
			}
			else
			{
				TipsDialog.GetInstance().setMsg("selectedDateType is invalid![selectedDateType:=" + selectedDateType + "]");
				TipsDialog.GetInstance().show();
				return;
			}
		}

		//03. day num
		if (m_allCalendarList == null)
			m_allNum = 0;

		m_allNum = m_allCalendarList.size();

		if (m_dayCalendarList == null)
			m_dayNum = 0;

		m_dayNum = m_dayCalendarList.size();

		if (m_nightCalendarList == null)
			m_nightNum = 0;

		m_nightNum = m_nightCalendarList.size();

		return;

	}

	public String getSchedualAllDescription()
	{
		String schedualDate = null;
		String dateString   = null;

		for (Calendar calendar : m_allCalendarList)
		{
			Date date = calendar.getTime();
			dateString = m_yearMonthDaySDF.format(date);
			if (schedualDate == null)
			{
				schedualDate = (dateString + NurseBasicListConfig.SCHEDULE_SPLIT);
			}
			else
			{
				schedualDate += (dateString + NurseBasicListConfig.SCHEDULE_SPLIT);
			}
		}

		return schedualDate;
	}

	public String getSchedualDayDescription()
	{
		String schedualDate = null;
		String dateString   = null;

		for (Calendar calendar : m_dayCalendarList)
		{
			Date date = calendar.getTime();
			dateString = m_yearMonthDaySDF.format(date);
			if (schedualDate == null)
			{
				schedualDate = (dateString + NurseBasicListConfig.SCHEDULE_SPLIT);
			}
			else
			{
				schedualDate += (dateString + NurseBasicListConfig.SCHEDULE_SPLIT);
			}
		}

		return schedualDate;
	}

	public String getSchedualNightDescription()
	{
		String schedualDate = null;
		String dateString   = null;

		for (Calendar calendar : m_nightCalendarList)
		{
			Date date = calendar.getTime();
			dateString = m_yearMonthDaySDF.format(date);
			if (schedualDate == null)
			{
				schedualDate = (dateString + NurseBasicListConfig.SCHEDULE_SPLIT);
			}
			else
			{
				schedualDate += (dateString + NurseBasicListConfig.SCHEDULE_SPLIT);
			}
		}

		return schedualDate;
	}


	public Date getBeginDate()
	{
		return m_beginDate;
	}

	public Date getEndDate()
	{
		return m_endDate;
	}

	public String getDateDescription()
	{
		return m_dateDescription;
	}

	public ArrayList<Calendar> getAllCalendarList()
	{
		return m_allCalendarList;
	}

	public ArrayList<Calendar> getDayCalendarList()
	{
		return m_dayCalendarList;
	}

	public ArrayList<Calendar> getNightCalendarList()
	{
		return m_nightCalendarList;
	}

	public int getAllNum()
	{
		return m_allNum;
	}

	public int getDayNum()
	{
		return m_dayNum;
	}

	public int getNightNum()
	{
		return m_nightNum;
	}
}
