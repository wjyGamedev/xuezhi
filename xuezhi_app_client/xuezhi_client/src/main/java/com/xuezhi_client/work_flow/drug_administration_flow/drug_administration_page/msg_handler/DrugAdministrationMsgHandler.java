package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.ui.DrugAdministrationActivity;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page.DrugAdministrationSettingActivity;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.ui.DrugStockAddActivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class DrugAdministrationMsgHandler extends BaseUIMsgHandler
{
	public DrugAdministrationMsgHandler(DrugAdministrationActivity activity)
	{
		super(activity);
	}

	public void go2DrugStockAddPage()
	{
		//跳转到药品库存添加页面
		DrugAdministrationActivity drugAdministrationActivity = (DrugAdministrationActivity)m_context;
		if (drugAdministrationActivity == null)
		{
			TipsDialog.GetInstance().setMsg("drugAdministrationActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		Intent intent = new Intent(drugAdministrationActivity, DrugStockAddActivity.class);
		drugAdministrationActivity.startActivity(intent);

		return;

	}

	public void delMedication_reminder()
	{
		return;
	}

	public void go2DrugAdministrationSettingPage(int medicalStockID)
	{
		//01. 判断nurse id 有效性
		//		if (!DBusinessData.GetInstance().checkNurseID(medicalStockID))
		//		{
		//			TipsDialog.GetInstance().setMsg("medicalStockID is invalid![drugItemID:="+medicalStockID+"]");
		//			TipsDialog.GetInstance().show();
		//			return;
		//		}

		//02. 同步数据到DAppiontmentNursing
		//		m_dAppiontmentNursingFlow.setSelectedDrugItemID(medicalStockID);

		//03. 跳转到药品管理设置页面
		DrugAdministrationActivity drugAdministrationActivity = (DrugAdministrationActivity)m_context;
		if (drugAdministrationActivity == null)
		{
			TipsDialog.GetInstance().setMsg("drugAdministrationActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		Intent intent = new Intent(drugAdministrationActivity, DrugAdministrationSettingActivity.class);
		drugAdministrationActivity.startActivity(intent);

		return;
	}
}
