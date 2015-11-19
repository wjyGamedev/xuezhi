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
 * 2015/7/9		WangJY		1.0.0		create
 */

package com.xuezhi_client.net.config;

public class NetConfig
{
	private final static String IP_ADDREDD_INNER   = "54.223.220.229:8888";        //内部测试服务器
	private final static String IP_ADDREDD_OFFICAL = "54.223.209.101:8888";    //泰心康护正式服务器
	private final static String IP_ADDREDD_TEST    = "54.223.217.123:8888";        //协同开发服务器

	private final static String IP_ADDRESS_XUEZHI_TEST = "54.223.203.15:8888";    //血脂的协同开发服务器。


	//01. 不需要用户ID和密码
	//0101. 注册
	public final static String S_REGISTER_ADDRESS = "http://" + IP_ADDRESS_XUEZHI_TEST + "/user/loginAction/";

	//0102. 获取支持药品的下拉列表
	public final static String S_NORMAL_MEDICINE_GET_LIST     = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getMedicalList/";
	//0103. 药品单位列表
	public final static String S_NORMAL_MEDICAL_UNIT_GET_LIST = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getUnitList/";
	//0104. 药品公司列表
	public final static String S_NORMAL_MEDICAL_COMPANY_GET_LIST = "http://" + IP_ADDRESS_XUEZHI_TEST + "/company/getCompanyList/";
	//02. 需要用户名和密码的
	//TODO:都要发送用户名和密码

	//0201. 我的药箱相关
	//获取我的药箱列表
	public final static String S_NORMAL_MEDICINE_BOX_GET_LIST = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getBox/";

	//添加药品到药箱中，如果添加ID相同，就累加remain的数量
	public final static String S_NORMAL_MEDICINE_BOX_ADD = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/addBox/";

	//从药箱中删除药品
	public final static String S_NORMAL_MEDICINE_BOX_DELETE = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/delBox/";

	//设置药箱中的药品
	public final static String S_NORMAL_MEDICINE_BOX_SET = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/setBox/";

	//0202. 用药提醒相关
	//获取用药提醒列表
	public final static String S_NORMAL_MEDICINE_PROMPT_GET_LIST = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getPrompt/";

	//添加用药提醒
	public final static String S_NORMAL_MEDICINE_PROMPT_ADD = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/addPrompt/";

	//删除用药提醒
	public final static String S_NORMAL_MEDICINE_PROMPT_DELETE = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/delPrompt/";

	//设置用药提醒
	public final static String S_NORMAL_MEDICINE_PROMPT_SET = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/setPrompt/";

	//03. 化验检查相关(血脂/生化)
	//化验检查信息列表
	public final static String S_NORMAL_ASSAY_DETECTION_GET_LIST = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getDetection/";

	//添加用药检查列表
	public final static String S_NORMAL_ASSAY_DETECTION_ADD = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/addDetection/";

	//设置用药检查列表
	public final static String S_NORMAL_ASSAY_DETECTION_SET = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/setDetection/";

	//04. 用药相关
	//用药记录
	public final static String S_NORMAL_TAKE_MEDICINE_GET_HISTORY_LIST = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getTakeHistory/";
	//吃药
	public final static String S_NORMAL_TAKE_MEDICINE_ADD              = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/addTakeHistory/";

	//忘记用药的记录
	public final static String S_NORMAL_NO_TAKE_MEDICINE_GET_HISTORY_LIST              = "http://" + IP_ADDRESS_XUEZHI_TEST + "/medical/getNoTakeHistory/";

}
