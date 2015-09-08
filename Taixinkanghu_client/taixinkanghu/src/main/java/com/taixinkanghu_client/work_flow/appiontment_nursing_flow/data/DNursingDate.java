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

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.data;

import com.taixinkanghu_client.config.DateConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.net.config.NurseBasicListConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DNursingDate
{
	private SimpleDateFormat m_simpleDateFormat = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	private Date                m_beingDate         = null;
	private Date                m_endDate           = null;
	private String              m_dateDescription   = null;
	private ArrayList<Calendar> m_allCalendarList   = new ArrayList<>();
	private ArrayList<Calendar> m_dayCalendarList   = new ArrayList<>();
	private ArrayList<Calendar> m_nightCalendarList = new ArrayList<>();
	private int                 m_allNum            = 0;
	private int                 m_dayNum            = 0;
	private int                 m_nightNum          = 0;

	//inner
	private ArrayList<ArrayList<Date>>    m_dateListAll = new ArrayList<>();
	private ArrayList<ArrayList<Integer>> m_typeListAll = new ArrayList<>();

	public DNursingDate(Date beingDate, Date endDate, ArrayList<ArrayList<Date>> dateListAll, ArrayList<ArrayList<Integer>> typeListAll,
						String dateDescription)
	{
		m_beingDate = beingDate;
		m_endDate = endDate;
		m_dateListAll = dateListAll;
		m_typeListAll = typeListAll;
		m_dateDescription = dateDescription;

		postHandle(dateListAll, typeListAll);

	}

	public DNursingDate(Date beingDate, Date endDate, ArrayList<Calendar> allCalendarList, ArrayList<Calendar> dayCalendarList,
						ArrayList<Calendar> nightCalendarList, String dateDescription)
	{
		m_beingDate = beingDate;
		m_endDate = endDate;
		m_dateDescription = dateDescription;
		m_allCalendarList = allCalendarList;
		m_dayCalendarList = dayCalendarList;
		m_nightCalendarList = nightCalendarList;

		postHandle();
	}

	private void postHandle()
	{
		if (m_allCalendarList == null)
			m_allNum = 0;

		m_allNum = m_allCalendarList.size();

		if (m_dayCalendarList == null)
			m_dayNum = 0;

		m_dayNum = m_dayCalendarList.size();

		if (m_nightCalendarList == null)
			m_nightNum = 0;

		m_nightNum = m_nightCalendarList.size();

	}

	private void postHandle(ArrayList<ArrayList<Date>> dateListAll, ArrayList<ArrayList<Integer>> typeListAll)
	{
		//01. m_allNum,m_dayNum,m_nightNum
		m_allNum = getDayNumByStatus(typeListAll, EnumConfig.NurseServiceDayStatus.ALL);
		m_dayNum = getDayNumByStatus(typeListAll, EnumConfig.NurseServiceDayStatus.DAY);
		m_nightNum = getDayNumByStatus(typeListAll, EnumConfig.NurseServiceDayStatus.NIGHT);
		//02. m_allCalendarList, m_dayCalendarList, m_nightCalendarList
		m_allCalendarList = getDateListByStatus(dateListAll, typeListAll, EnumConfig.NurseServiceDayStatus.ALL);
		m_dayCalendarList = getDateListByStatus(dateListAll, typeListAll, EnumConfig.NurseServiceDayStatus.DAY);
		m_nightCalendarList = getDateListByStatus(dateListAll, typeListAll, EnumConfig.NurseServiceDayStatus.NIGHT);
	}

	public String getSchedualAllDescription()
	{
		String schedualDate = null;
		String dateString   = null;

		for (Calendar calendar : m_allCalendarList)
		{
			Date date = calendar.getTime();
			dateString = m_simpleDateFormat.format(date);
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
			dateString = m_simpleDateFormat.format(date);
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
			dateString = m_simpleDateFormat.format(date);
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


	private int getDayNumByStatus(ArrayList<ArrayList<Integer>> typeListAll, EnumConfig.NurseServiceDayStatus dayType)
	{
		int dayNums = 0;
		for (ArrayList<Integer> integerList : typeListAll)
		{
			for (Integer integer : integerList)
			{
				if (integer == dayType.getId())
				{
					dayNums++;
					continue;
				}
			}
		}
		return dayNums;
	}

	private ArrayList<Calendar> getDateListByStatus(ArrayList<ArrayList<Date>> dateListAll, ArrayList<ArrayList<Integer>> typeListAll, EnumConfig.NurseServiceDayStatus dayType)
	{
		ArrayList<Calendar> calendars = new ArrayList<>();
		for (int iMonth = 0; iMonth < typeListAll.size(); iMonth++)
		{
			ArrayList<Integer> typeArrayList = typeListAll.get(iMonth);
			ArrayList<Date> dateArrayList = dateListAll.get(iMonth);

			if (typeListAll.size() != dateListAll.size())
				return null;

			for (int iDay = 0; iDay < typeArrayList.size(); ++iDay)
			{
				//DataConfig.SELECT_DAY_TYEP_ALL,SELECT_DAY_TYEP_DAY,SELECT_DAY_TYEP_NIGHT
				if (typeArrayList.get(iDay) != dayType.getId())
					continue;

				Date date = dateArrayList.get(iDay);
				Calendar tmpCalendar = Calendar.getInstance();
				tmpCalendar.setTime(date);
				calendars.add(tmpCalendar);
			}
		}
		return calendars;
	}


	public Date getBeingDate()
	{
		return m_beingDate;
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
