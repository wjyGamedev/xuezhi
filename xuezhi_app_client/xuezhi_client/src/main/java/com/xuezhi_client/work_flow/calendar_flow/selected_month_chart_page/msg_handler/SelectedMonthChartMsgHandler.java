/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.calendar_flow.selected_month_chart_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/11/19		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.calendar_flow.selected_month_chart_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.work_flow.calendar_flow.selected_month_chart_page.ui.SelectedMonthChartActivity;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;

public class SelectedMonthChartMsgHandler extends BaseUIMsgHandler
{
	public SelectedMonthChartMsgHandler(SelectedMonthChartActivity activity)
	{
		super(activity);
	}

	public void go2MainPage()
	{
		SelectedMonthChartActivity activity = (SelectedMonthChartActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}
}
