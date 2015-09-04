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

	//医院列表
	public final static int QUEST_HOSPITAL_LIST    = NETWORK_DEFAULT + 1;
	public final static int FINISHED_HOSPITAL_LIST = QUEST_HOSPITAL_LIST + 1;

	//科室列表
	public final static int QUEST_DEPARTMENT_LIST    = FINISHED_HOSPITAL_LIST + 1;
	public final static int FINISHED_DEPARTMENT_LIST = QUEST_DEPARTMENT_LIST + 1;

	//注册消息
	public final static int QUEST_SUPPORTED_COUNTRIES_LIST    = FINISHED_DEPARTMENT_LIST + 1;
	public final static int QUEST_REGISTER = QUEST_SUPPORTED_COUNTRIES_LIST + 1;
	public final static int RESPONSE_REGISTER = QUEST_REGISTER + 1;

	//护工基础信息列表
	public final static int QUEST_NURSE_BASIC_LIST    = FINISHED_DEPARTMENT_LIST + 1;
	public final static int FINISHED_NURSE_BASIC_LIST = QUEST_NURSE_BASIC_LIST + 1;

	//护工高阶信息列表
	public final static int QUEST_NURSE_SENIOR_LIST    = FINISHED_NURSE_BASIC_LIST + 1;
	public final static int FINISHED_NURSE_SENIOR_LIST = QUEST_NURSE_SENIOR_LIST + 1;



	//预约陪护信息
	public final static int QUEST_APPPINTMENT_NURSING = QUEST_REGISTER + 1;

	//护工订单确认
	public final static int QUEST_NURSE_ORDER_CONFIRM    = QUEST_APPPINTMENT_NURSING + 1;
	public final static int FINISHED_NURSE_ORDER_CONFIRM = QUEST_NURSE_ORDER_CONFIRM + 1;

	public final static int QUEST_NURSE_ORDER_CONFIRM_FOR_CHANGE_NURSE    = FINISHED_NURSE_ORDER_CONFIRM + 1;


	//护工订单check
	public final static int QUEST_NURSE_ORDER_CHECK    = QUEST_NURSE_ORDER_CONFIRM_FOR_CHANGE_NURSE + 1;
	public final static int FAILED_NURSE_ORDER_CHECK = QUEST_NURSE_ORDER_CHECK + 1;
	public final static int FINISHED_NURSE_ORDER_CHECK = FAILED_NURSE_ORDER_CHECK + 1;

	//护理订单alipay
	public final static int QUEST_NURSE_ORDER_ALIPAY    = FINISHED_NURSE_ORDER_CHECK + 1;
	public final static int FAILED_NURSE_ORDER_ALIPAY    = QUEST_NURSE_ORDER_ALIPAY + 1;
	public final static int FINISHED_NURSE_ORDER_ALIPAY    = FAILED_NURSE_ORDER_ALIPAY + 1;

	//订单列表
	public final static int QUEST_NURSE_ORDER_LIST    = FINISHED_NURSE_ORDER_ALIPAY + 1;
	public final static int FINISHED_NURSE_ORDER_LIST = QUEST_NURSE_ORDER_LIST + 1;

	//取消订单，在未支付状态。
	public final static int QUEST_NURSE_ORDER_CANCEL = FINISHED_NURSE_ORDER_LIST + 1;
	public final static int FINISHED_NURSE_ORDER_CANCEL = QUEST_NURSE_ORDER_CANCEL + 1;

	//取消订单，在服务状态下。
	public final static int QUEST_NURSE_ORDER_CANCEL_SERVICE = FINISHED_NURSE_ORDER_CANCEL + 1;

	//补差价
	public final static int QUEST_NURSE_ORDER_PAY_MORE = QUEST_NURSE_ORDER_CANCEL_SERVICE + 1;
	public final static int FINISHED_NURSE_ORDER_PAY_MORE = QUEST_NURSE_ORDER_PAY_MORE + 1;


	//康复用品
	public final static int QUEST_SHOPPING_BASIC_LIST = FINISHED_NURSE_ORDER_PAY_MORE + 1;

	//sms event
	public final static int SMS_EVENT_DEFAULT = 200;

	/**
	 * UI event
	 */
	public final static int UI_DEFAULT                      = 1000;
	public final static int UI_SWITCH_IMAGE                 = 1001;
	public final static int UI_SMS_DESERIALIZATION_FINISHED = 1002;
	public final static int UI_SELECT_DATE_SURE             = 1003;
	//	public final static int UI_LOGOUT= 1003;

	/**
	 * logical event
	 */
	public final static int LOGICAL_DEFAULT = 2000;
	public final static int LOGICAL_LOGOUT  = 2001;


}
