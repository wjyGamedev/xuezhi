/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.util.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/27		WangJY		1.0.0		create
 */

package com.xuezhi_client.util;

import java.util.Calendar;

public class LogicalUtil
{
	public static Calendar getExhaustTime(double amountPerTime, double reminderValue)
	{
		Calendar today = Calendar.getInstance();
		return getExhaustTime(amountPerTime, reminderValue, today);
	}

	public static Calendar getExhaustTime(double amountPerTime, double reminderValue, Calendar startTime)
	{
		if (amountPerTime <= 0)
		{
			amountPerTime = 1;
			//TODO:报告错误
		}

		Calendar exhaustTime = Calendar.getInstance();

		if (reminderValue <= 0)
		{
			exhaustTime.setTime(startTime.getTime());
			return exhaustTime;
		}

		int remainDays = (int)Math.floor(reminderValue / amountPerTime);
		int todayYear  = startTime.get(Calendar.YEAR);
		int todayMonth = startTime.get(Calendar.MONTH);
		int todayDay   = startTime.get(Calendar.DAY_OF_MONTH);
		int maxMonths  = startTime.getActualMaximum(Calendar.MONTH);
		int maxDays    = 0;

		Calendar tmpCalendar = Calendar.getInstance();
		int      beginYear   = todayYear;
		int      beginMonth  = todayMonth;
		int      beginDay    = todayDay;
		for (int index = 0; index < remainDays; ++index)
		{
			//今天
			if (index == 0)
			{
				tmpCalendar.set(todayYear, todayMonth, todayDay);
				continue;
			}

			maxDays = tmpCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			if (beginDay >= maxDays)
			{
				beginDay = 1;
				if (beginMonth >= maxMonths)
				{
					beginMonth = 1;
					beginYear++;
				}
				else
				{
					beginMonth++;
				}
			}
			else
			{
				beginDay++;
			}

			tmpCalendar.set(beginYear, beginMonth, beginDay);
		}

		exhaustTime.setTime(tmpCalendar.getTime());
		return exhaustTime;
	}


}
