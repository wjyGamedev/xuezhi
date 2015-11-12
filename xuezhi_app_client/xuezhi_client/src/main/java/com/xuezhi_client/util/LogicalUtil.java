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

import java.util.Date;
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

		Calendar tmpCalendar = Calendar.getInstance();

		Date date_starTime =startTime.getTime();
		tmpCalendar.setTime(date_starTime);   //设置当前日期
		tmpCalendar.add(Calendar.DATE, remainDays); //日期加1天
		exhaustTime.setTime(tmpCalendar.getTime());
		return exhaustTime;
	}
}
