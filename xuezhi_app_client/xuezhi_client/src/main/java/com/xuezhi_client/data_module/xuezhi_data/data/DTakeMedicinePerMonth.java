/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DTakeMedicinePerMonth
{
	private Calendar                               m_selectedPerMonth          = Calendar.getInstance();
	private HashMap<Calendar, DTakeMedicinePerDay> m_takeMedicinePerDayHashMap = new HashMap<>();

	public DTakeMedicinePerMonth()
	{
	}

	public synchronized boolean serialization(JSONObject response, Calendar monthCalendar) throws JSONException, ParseException
	{
		//02. 当前月份
		m_selectedPerMonth.setTime(monthCalendar.getTime());
		int currYear  = m_selectedPerMonth.get(Calendar.YEAR);
		int currMonth = m_selectedPerMonth.get(Calendar.MONTH);
		int currDay   = m_selectedPerMonth.get(Calendar.DAY_OF_MONTH);


		DTakeMedicinePerDay takeMedicinePerDay = null;
		//更新
		Iterator<Map.Entry<Calendar, DTakeMedicinePerDay>> iterator = m_takeMedicinePerDayHashMap.entrySet().iterator();
		int                                                year     = 0;
		int                                                month    = 0;
		int                                                day      = 0;
		boolean                                            findFlag = false;
		while (iterator.hasNext())
		{
			Map.Entry<Calendar, DTakeMedicinePerDay> entry = iterator.next();
			Calendar tmpCalendar = entry.getKey();
			year = tmpCalendar.get(Calendar.YEAR);
			month = tmpCalendar.get(Calendar.MONTH);
			day = tmpCalendar.get(Calendar.DAY_OF_MONTH);

			if (year == currYear && month == currMonth && day == currDay)
			{
				findFlag = true;
				takeMedicinePerDay = entry.getValue();
				break;
			}
		}
		//add new
		if (!findFlag)
		{
			takeMedicinePerDay = new DTakeMedicinePerDay();
			m_takeMedicinePerDayHashMap.put(monthCalendar, takeMedicinePerDay);
		}

		takeMedicinePerDay.serialization(response, m_selectedPerMonth);


		return true;

	}

	public Calendar getSelectedPerMonth()
	{
		return m_selectedPerMonth;
	}

	public HashMap<Calendar, DTakeMedicinePerDay> getTakeMedicinePerDayHashMap()
	{
		return m_takeMedicinePerDayHashMap;
	}

	public synchronized DTakeMedicinePerDay getMedicalHistoryBySelectedDay(Calendar month)
	{
		Iterator<Map.Entry<Calendar, DTakeMedicinePerDay>> iter = m_takeMedicinePerDayHashMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry<Calendar, DTakeMedicinePerDay> entry = iter.next();

			Calendar tmpCalendar = (Calendar)entry.getKey();
			if (tmpCalendar == null)
				continue;

			DTakeMedicinePerDay tmpTakemedicinePerDay = (DTakeMedicinePerDay)entry.getValue();
			if (tmpTakemedicinePerDay == null)
				continue;

			if (tmpCalendar.get(Calendar.YEAR) != month.get(Calendar.YEAR))
				continue;

			if (tmpCalendar.get(Calendar.MONTH) != month.get(Calendar.MONTH))
				continue;

			if (tmpCalendar.get(Calendar.DAY_OF_MONTH) != month.get(Calendar.DAY_OF_MONTH))
				continue;

			return tmpTakemedicinePerDay;
		}
		return null;
	}
}
