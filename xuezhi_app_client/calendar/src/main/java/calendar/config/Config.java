/**
 * Copyright (c) 213Team
 *
 * @className : calendar.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/16		WangJY		1.0.0		create
 */

package calendar.config;

import java.util.Calendar;

public class Config
{
	public static final int DEFAULT_TILE_SIZE_DP          = 44;
	public static final int DEFAULT_DAYS_IN_WEEK          = 7;
	public static final int DEFAULT_MAX_WEEKS             = 6;
	public static final int DEFAULT_MONTH_TILE_HEIGHT     = DEFAULT_MAX_WEEKS + 1;
	public static final int DEFAULT_FIRST_DAY_OF_THE_WEEK = Calendar.SUNDAY;
}
