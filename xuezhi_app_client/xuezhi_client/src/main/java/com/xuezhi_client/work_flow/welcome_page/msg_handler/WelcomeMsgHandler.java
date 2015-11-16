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

package com.xuezhi_client.work_flow.welcome_page.msg_handler;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.work_flow.welcome_page.ui.WelcomeActivity;

public class WelcomeMsgHandler extends BaseUIMsgHandler
{
	public WelcomeMsgHandler(WelcomeActivity welcomeActivity)
	{
		super(welcomeActivity);
	}

	public void initAction()
	{
		//01. 药品单位
		DBusinessMsgHandler.GetInstance().requestMedicineUnitGetListAction();

		//02. 发送药品列表
		DBusinessMsgHandler.GetInstance().requestMedicineGetListAction();

		//03. 药品公司列表
		DBusinessMsgHandler.GetInstance().requestMedicineCompanyGetListAction();
	}
}
