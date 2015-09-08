/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.welcome.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/7		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.welcome.msg_handler;

import com.module.frame.BaseUIMsgHandler;
import com.taixinkanghu_client.data_module.department_list.msg_handler.DepartmentMsgHandler;
import com.taixinkanghu_client.data_module.hospital_list.msg_handler.HospitalMsgHandler;
import com.taixinkanghu_client.work_flow.welcome.ui.WelcomeActivity;

public class WelcomeMsgHandler extends BaseUIMsgHandler
{
	public WelcomeMsgHandler(WelcomeActivity welcomeActivity)
	{
		super(welcomeActivity);
	}

	//01. 请求医院列表
	public void requestHospitalList()
	{
		HospitalMsgHandler.GetInstance().requestHospitalListAction();
	}

	//02. 请求科室列表
	public void requestDepartmentList()
	{
		DepartmentMsgHandler.GetInstance().requestDepartmentListAction();
	}

}
