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

package com.xuezhi_client.net.config;

public class NetConfig
{
	private final static String IP_ADDREDD_INNER   = "54.223.220.229:8888";        //内部测试服务器
	private final static String IP_ADDREDD_OFFICAL = "54.223.209.101:8888";    //泰心康护正式服务器
	private final static String IP_ADDREDD_TEST    = "54.223.217.123:8888";        //协同开发服务器

	private final static String IP_ADDRESS_XUEZHI_TEST = "54.223.203.15:8888";    //血脂的协同开发服务器。

	//测试地址
	//医院URL
	public final static String S_NORMAL_HOSPITALLIST_ADDRESS   = "http://" + IP_ADDREDD_TEST + "/hospital/gethospitallist/";
	//科室URL
	public final static String S_NORMAL_DEPARTMENTLIST_ADDRESS = "http://" + IP_ADDREDD_TEST + "/department/getDepartmentList/";

	//注册
	public final static String s_registerAddress = "http://" + IP_ADDRESS_XUEZHI_TEST + "/user/loginAction/";

	//01. 血脂相关
	//药品单位
	public final static String S_NORMAL_MEDICAL_UNIT_LIST_ADDRESS = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getUnitList/";

	//获取支持药品的下拉列表
	public final static String S_NORMAL_MEDICAL_LIST_ADDRESS = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getMedicalList/";

	//用药库存
	public final static String S_NORMAL_MEDICAL_REMAIN_ADDRESS    = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getPrompt/";
	//添加药品（库存）
	public final static String S_NORMAL_ADD_MEDICAL_STOCK_ADDRESS = "http://" + IP_ADDRESS_XUEZHI_TEST +
			"/medical/addRemainMedicalPrompt/";


	//用药记录
	public final static String S_NORMAL_MEDICAL_HISTORY_ADDRESS = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getTakeHistory/";
	//吃药
	public final static String S_NORMAL_TAKE_MEDICAL_ADDRESS    = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/takeTakeMedical/";

	//药提醒用
	public final static String S_NORMAL_MEDICAL_PROMPT_LIST_ADDRESS = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getPrompt/";
	//添加用药提醒
	public final static String S_NORMAL_MEDICAL_ADD_PROMPT_ADDRESS  = "http://" + IP_ADDRESS_XUEZHI_TEST +
			"/medical/addTakeMedicalPrompt/";


	//添加用药检查信息
	public final static String S_NORMAL_ADD_ASSAY_DETECTION_INFO_ADDRESS = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/addDetection/";

	//用药检查信息列表
	public final static String S_NORMAL_ASSAY_DETECTION_LIST_ADDRESS = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getDetection/";

}
