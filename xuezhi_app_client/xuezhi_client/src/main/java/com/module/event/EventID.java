/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/9		WangJY		1.0.0		create
 */

package com.module.event;

public class EventID
{
	/**
	 * network 100~999
	 */
	public final static int NETWORK_DEFAULT = 100;

	//注册消息
	public final static int QUEST_SUPPORTED_COUNTRIES_LIST    = NETWORK_DEFAULT + 1;
	public final static int QUEST_REGISTER = QUEST_SUPPORTED_COUNTRIES_LIST + 1;
	public final static int RESPONSE_REGISTER = QUEST_REGISTER + 1;

	//药品列表
	public final static int QUEST_MEDICAL_LIST    = RESPONSE_REGISTER + 1;
	public final static int ANSWER_MEDICAL_LIST = QUEST_MEDICAL_LIST + 1;

	//库存列表
	public final static int QUEST_MEDICAL_STOCK_LIST    = ANSWER_MEDICAL_LIST + 1;
	public final static int ANSWER_MEDICAL_STOCK_LIST = QUEST_MEDICAL_STOCK_LIST + 1;

	//用药记录列表
	public final static int QUEST_MEDICAL_HISTORY_LIST    = ANSWER_MEDICAL_LIST + 1;
	public final static int ANSWER_MEDICAL_HISTORY_LIST = QUEST_MEDICAL_HISTORY_LIST + 1;

	//用药提醒列表(按月)
	public final static int QUEST_MEDICAL_PROMPT_LIST    = ANSWER_MEDICAL_HISTORY_LIST + 1;
	public final static int ANSWER_MEDICAL_PROMPT_LIST = QUEST_MEDICAL_PROMPT_LIST + 1;

	//添加化验检查信息
	public final static int QUEST_ADD_ASSAY_DETECTION_INFO    = ANSWER_MEDICAL_PROMPT_LIST + 1;
	public final static int ANSWER_ADD_ASSAY_DETECTION_INFO = QUEST_ADD_ASSAY_DETECTION_INFO + 1;

	//化验检查信息列表
	public final static int QUEST_ASSAY_DETECTION_LIST    = ANSWER_ADD_ASSAY_DETECTION_INFO + 1;
	public final static int ANSWER_ASSAY_DETECTION_LIST = QUEST_ASSAY_DETECTION_LIST + 1;



	//sms event
	public final static int SMS_EVENT_DEFAULT = 200;

	/**
	 * UI event
	 */
	public final static int UI_DEFAULT                      = 1000;
	public final static int UI_SELECT_APPOINTMENT_NURSING_DATE_FINISHED             = 1003;

	public final static int UI_SWITCH_IMAGE                 = 1001;
	public final static int UI_SMS_DESERIALIZATION_FINISHED = 1002;

	//	public final static int UI_LOGOUT= 1003;

	/**
	 * logical event
	 */
	public final static int LOGICAL_DEFAULT = 2000;
	public final static int LC_WAIT_DIALOG_FINISHED  = LOGICAL_DEFAULT+1;


}
