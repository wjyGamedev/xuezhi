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

package com.xuezhi_client.work_flow.calendar_flow.calender_page.ui;

import android.graphics.drawable.Drawable;

import com.module.data.DGlobal;
import com.xuzhi_client.xuzhi_app_client.R;

import calendar.CalendarDay;
import calendar.day.DayViewFacade;
import calendar.month.MonthView;

public class SelectorNotTakedDecorator extends BaseSelectorDecorator
{
	private final Drawable m_drawable;
	private BaseSelectorDecorator.OnShouldDecorateListener m_onShouldDecorateListener = null;

	public SelectorNotTakedDecorator()
	{
		m_drawable = DGlobal.GetInstance().getAppContext().getResources().getDrawable(R.drawable.not_taken_img);
	}

	public void setOnShouldDecorateListener(OnShouldDecorateListener onShouldDecorateListener)
	{
		m_onShouldDecorateListener = onShouldDecorateListener;
	}

	@Override
	public boolean shouldDecorate(CalendarDay day, MonthView monthView)
	{
		return m_onShouldDecorateListener.shouldDecorate(day, monthView);
	}

	@Override
	public void decorate(DayViewFacade view)
	{
		view.setSelectionDrawable(m_drawable);
	}
}
