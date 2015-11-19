/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.calendar_flow.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/11/19		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.calendar_flow.config;

import android.graphics.Color;

public class CalendarFlowConfig
{
	public final static String SELECTED_MONTH                     = "selected_month";
	public final static int    SELECTED_MONTH_PIE_CHART_MAX_VALUE = 4;

	public final static int COLOR_NO_SETTING_REMINDER     = Color.rgb(2, 141, 242);		//1:未设置用药提醒的
	public final static int COLOR_TAKE_MEDICINE_FAILED    = Color.rgb(199, 0, 0);		//2:未完全服药的颜色
	public final static int COLOR_TAKE_MEDICINE_SUCCESSED = Color.rgb(234, 171, 2);		//3:待服药的颜色
	public final static int COLOR_TAKE_MEDICINE_WAIT      = Color.rgb(0, 167, 55);		//4:完全服药的颜色

}



