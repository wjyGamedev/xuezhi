/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/11		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.config;

public class DataConfig
{
	//康复用品
	public final static String GOODS_BASICS_LIST = "goods_basics_list";
	public final static String GOODS_ID          = "id";
	public final static String GOODS_NAME        = "name";
	public final static String GOODS_UNIT_PRICE  = "price";
	public final static String PRAISE_RATE       = "praise_rate";
	public final static String EVALUATION_TIMES  = "evaluation_times";

	public final static int MAX_SELECT_DAY_TYEP = 3;
	public final static int SELECT_DAY_TYEP_ALL = 0;
	public final static int SELECT_DAY_TYEP_DAY = 1;
	public final static int SELECT_DAY_TYEP_NIGHT = 2;

	public static final int DEFAULT_VALUE = 0;
	public final static String SCHEDULE_SPLIT = ",";

	//注册
	public final static int REGISTER_WAITTING_TIME = 60*1000;
	public final static int REGISTER_DELTA_TIME = 1000;

}

