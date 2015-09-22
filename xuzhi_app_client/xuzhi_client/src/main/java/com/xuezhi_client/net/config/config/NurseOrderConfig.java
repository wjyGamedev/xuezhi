/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/18		WangJY		1.0.0		create
 */

package com.xuezhi_client.net.config.config;

public class NurseOrderConfig
{
	//01. common
	public final static String HOSPITAL_ID        = "hospital_id";
	public final static String USER_ID            = "uid";                        //用户id
	public final static String NURSE_ID           = "cid";                        //护理员ID
	public final static String DEPARTMENT_ID      = "department_id";       //护理员科室ID
	public final static String ORDER_ID           = "oid";                        //订单ID
	public final static String USER_PHONE_NUM     = "order_mobile";            //用户输入的手机号
	public final static String PATIENT_NAME       = "patient_name";            //患者名称
	public final static String PATIENT_GENDER     = "patient_gender";            //患者性别
	public final static String PATIENT_AGE        = "patient_age";                //患者年龄
	public final static String PATIENT_WEIGHT     = "patient_weight";            //患者体重
	public final static String PATIENT_STATUS     = "patient_status";            //患者自理状态
	public final static String PATIENT_REMARK     = "patient_remark";            //患者备注
	public final static String ORDER_TOTAL_CHARGE = "order_total_charge";        //护理员获取的该订单的价格。
	public final static String ORDER_USER_PAY = "order_user_pay";        //订单的总价格。
	public final static String SCHEDULE_ALL       = NurseBasicListConfig.SCHEDULE_ALL;
	public final static String SCHEDULE_DAY       = NurseBasicListConfig.SCHEDULE_DAY;
	public final static String SCHEDULE_NIGHT     = NurseBasicListConfig.SCHEDULE_NIGHT;
	public final static String NURSE_INFO     = "carer";
	public final static String BEGIN_DATE_FOR_CHANGE_NURSE    = "date";


	//02. confirm
	//0201. recv(无)
	//0201. send
	//03. nurse order list
	//0301. send
	//0302. recv
	public final static String NURSE_ORDER_LIST   = "order_list";                    //用户订单列表
	public final static String ORDER_TIME         = "order_time";                //订单时间
	public final static String ORDER_STATUS       = "order_status";            //订单的状态。
	public final static String ORDER_SERIAL_NUM   = "show_id";    //订单的交易流水号。
	public final static String SCHEDULE_DATE_LIST = "schedule";
	public final static String DATE_ALL_LIST      = "all";
	public final static String DATE_DAY_LIST      = "day";
	public final static String DATE_NIGHT_LIST    = "night";

	//04. nurse order check
	public final static String CHECK_TYPE = "type";
	//05. nurse order alipay
	public final static String RESULT_STATUS = "result_status";

	//06. nurse order pay more
	//send
	public final static String ORDER_PAY_MORE_PRICE = "price";        //补差价的价格
	public final static String ORDER_PAY_MORE_REASON_OPTION = "reason_option";        //补差价的option
	public final static String ORDER_PAY_MORE_REASON_VALUE = "reason_value";        //补差价的具体原因
	//recv
	public final static String ORDER_PAY_MORE_OBJECT = "addon";        //补差价的返回对象

	//07. change nurse
	public final static String CHANGE_NURSE_BEGIN_DATE = "begin_date";
	public final static String CHANGE_NURSE_TODAY = "tody";
	public final static String CHANGE_NURSE_END_DATE = "end_date";
	public final static String CHANGE_NURSE_DATE_DESCRIPTION = "date_description";

	//08. comment nurse order
	//send
	public final static String COMMENT_LEVEL = "comment_level";
	public final static String COMMENT_CONTENT  = "comment_content";
	//recvv
	public final static String COMMENT_LIST = "comment_list";
	public final static String COMMENT_DATE = "comment_date";
	public final static String COMMENT_ID = "comment_id";

	//09. pay more
	public final static String PAY_MORE_ORDER_ID = "aid";

	//logical
	public final static int NURSE_IN_SERVICE = -2;



}
