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

package com.module.net;

public class NetConfig
{
	private final static String IP_ADDREDD_INNER   = "54.223.220.229:8888";        //内部测试服务器
	private final static String IP_ADDREDD_OFFICAL = "54.223.209.101:8888";    //泰心康护正式服务器
	private final static String IP_ADDREDD_TEST    = "54.223.217.123:8888";        //协同开发服务器

	//测试地址
	public final static String s_hospitalListAddress    = "http://" + IP_ADDREDD_INNER + "/downloads/hospital_list/";
	public final static String s_nurseBasicsListAddress = "http://" + IP_ADDREDD_INNER + "/downloads/nurse_basics_list/";

	public final static String s_ShoppingBasicsListAddress = "http://" + IP_ADDREDD_INNER + "/downloads/goods_basics_list/";


	//医院URL
	public final static String S_NORMAL_HOSPITALLIST_ADDRESS   = "http://" + IP_ADDREDD_TEST + "/hospital/gethospitallist/";
	//科室URL
	public final static String S_NORMAL_DEPARTMENTLIST_ADDRESS = "http://" + IP_ADDREDD_TEST + "/department/getDepartmentList/";

	//注册
	public final static String s_registerAddress = "http://" + IP_ADDREDD_TEST + "/user/loginAction/";

	//预约陪护地址
	public final static String s_appointmentNursingAddress = "http://" + IP_ADDREDD_TEST + "/carer/getCarerList/";

	//nurse senior list address
	public final static String s_nurseSeniorListAddress = "http://" + IP_ADDREDD_TEST + "/carer/getCarerSeniorList/";

	//nurse order confirm
	public final static String s_nurseOrderConfirmAddress = "http://" + IP_ADDREDD_TEST + "/order/preAddOrder/";

	//nurse order check
	public final static String s_nurseOrderCheckAddress = "http://" + IP_ADDREDD_TEST + "/order/checkOrder/";

	//服务器异步通知页面路径
	public static final String s_nurserOrderAlipayServerNoticePageURL = "http://" + IP_ADDREDD_TEST + "/order/payCallBack/";

	//nurse order list address
	public final static String s_nurseOrderListAddress = "http://" + IP_ADDREDD_TEST + "/order/getOder/";

	//取消订单，在未付款的情况下。
	public final static String s_nurseOrderCancel = "http://" + IP_ADDREDD_TEST + "/order/cancelOrder/";

	//取消订单，在服务西黄太下
	public final static String s_nurseOrderCancelService = "http://" + IP_ADDREDD_TEST + "/order/orderEarlyCancel/";

	//更换护工的地址
	public final static String s_changeNurseAddress = "http://" + IP_ADDREDD_TEST + "/order/changeCarer/";

	//补差价
	public final static String s_nurseOrderPayMoreAddress = "http://" + IP_ADDREDD_TEST + "/order/orderAddOnPay/";

}
