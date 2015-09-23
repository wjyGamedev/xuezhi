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
 * 2015/8/14		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.repeat_order_flow.select_date_page.ui;

import calendar.CalendarDay;
import calendar.day.DayViewDecorator;
import calendar.month.MonthView;

public abstract class BaseSelectorDecorator implements DayViewDecorator
{
	public interface OnShouldDecorateListener
	{
		boolean shouldDecorate(CalendarDay day, MonthView monthView);
	}
}
