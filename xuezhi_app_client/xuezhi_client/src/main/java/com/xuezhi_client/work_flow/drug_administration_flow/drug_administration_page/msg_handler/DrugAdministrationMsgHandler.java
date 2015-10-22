package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicineBoxDeleteEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicineBoxGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicineBoxDeleteEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicineBoxGetListEvent;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.ui.DrugAdministrationActivity;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page.DrugAdministrationSettingActivity;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.ui.DrugStockAddActivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class DrugAdministrationMsgHandler extends BaseUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public DrugAdministrationMsgHandler(DrugAdministrationActivity activity)
	{
		super(activity);
	}

	//跳转到药品库存添加页面
	public void go2DrugStockAddPage()
	{
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

	//删除药品库存
	public void delDrugAndGo2DrugAdministrationPage(int drugStockID)
	{
		if(DAccount.GetInstance().isRegisterSuccess())
		{
			RequestMedicineBoxDeleteEvent requestDelMedicalStockAction = new RequestMedicineBoxDeleteEvent();
			requestDelMedicalStockAction.setUID(DAccount.GetInstance().getId());
			requestDelMedicalStockAction.setMBID(String.valueOf(drugStockID));
			DBusinessMsgHandler.GetInstance().requestMedicineBoxDeleteAction(requestDelMedicalStockAction);
		}
		return;
	}

	//删除成功，获取药品库存列表
	public void onEventMainThread(AnswerMedicineBoxDeleteEvent event)
	{
		if (DAccount.GetInstance().isRegisterSuccess())
		{
			RequestMedicineBoxGetListEvent event_new = new RequestMedicineBoxGetListEvent();
			event_new.setUID(DAccount.GetInstance().getId());
			DBusinessMsgHandler.GetInstance().requestMedicineBoxGetListAction(event_new);
		}
		return;
	}

	//删除成功，获取药品库存列表
	public void onEventMainThread(AnswerMedicineBoxGetListEvent event)
	{
		DrugStockAddActivity drugStockAddActivity = (DrugStockAddActivity)m_context;
		drugStockAddActivity.updateContent();
		return;
	}

	public void delMedication_reminder()
	{
		return;
	}

	public void go2DrugAdministrationSettingPage(int selectMedicalStockID)
	{
		//01. 判断selectMedicalStockID 有效性
		//		if (!DBusinessData.GetInstance().checkNurseID(medicalStockID))
		//		{
		//			TipsDialog.GetInstance().setMsg("medicalStockID is invalid![drugItemID:="+medicalStockID+"]");
		//			TipsDialog.GetInstance().show();
		//			return;
		//		}

		//02. 跳转到药品管理设置页面
		DrugAdministrationActivity drugAdministrationActivity = (DrugAdministrationActivity)m_context;
		if (drugAdministrationActivity == null)
		{
			TipsDialog.GetInstance().setMsg("drugAdministrationActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		Intent intent = new Intent(drugAdministrationActivity, DrugAdministrationSettingActivity.class);
		intent.putExtra("selectMedicalStockID", Integer.toString(selectMedicalStockID));
		drugAdministrationActivity.startActivity(intent);

		return;
	}

}
