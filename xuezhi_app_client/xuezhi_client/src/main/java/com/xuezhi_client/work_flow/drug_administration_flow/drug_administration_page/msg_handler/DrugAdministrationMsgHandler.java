package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.msg_handler;

import android.content.Intent;
import android.widget.Toast;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerAddMedicalStockEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicalStockListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicalStockListEvent;
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

	public void delDrugAndGo2DrugAdministrationPage()
	{
		Toast.makeText(m_context, "正在删除", Toast.LENGTH_SHORT).show();
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

	//update
	public void onEventMainThread(AnswerAddMedicalStockEvent event)
	{
		//		DrugAdministrationActivity drugAdministrationActivity = (DrugAdministrationActivity)m_context;
		//		drugAdministrationActivity.updateContent();

		RequestMedicalStockListEvent event_new = new RequestMedicalStockListEvent();
		event_new.setUID("2");
		DBusinessMsgHandler.GetInstance().requestMedicalStockListAction(event_new);

		return;
	}

	public void onEventMainThread(AnswerMedicalStockListEvent event)
	{
		DrugAdministrationActivity drugAdministrationActivity = (DrugAdministrationActivity)m_context;
		drugAdministrationActivity.updateContent();
	}


}
