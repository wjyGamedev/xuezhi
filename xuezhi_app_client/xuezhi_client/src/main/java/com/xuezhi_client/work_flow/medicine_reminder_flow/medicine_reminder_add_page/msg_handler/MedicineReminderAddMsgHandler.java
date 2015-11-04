/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/20		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.msg_handler;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineBox;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicineGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicinePromptAddEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptAddEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptGetListEvent;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.ADHChartFragment;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.ui.DrugStockAddActivity;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.ui.MedicineReminderAddActivity;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.ui.SelectMedicineFragment;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.ui.SelectMedicineTimeFragment;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.Calendar;

public class MedicineReminderAddMsgHandler extends BaseUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public MedicineReminderAddMsgHandler(MedicineReminderAddActivity context)
	{
		super(context);
	}

	public void go2SelectMedicineTimeFragment()
	{
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)m_context;

		SelectMedicineTimeFragment selectMedicineTimeFragment = new SelectMedicineTimeFragment();

		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.medicine_reminder_add_page, selectMedicineTimeFragment, SelectMedicineTimeFragment.class.getName());
		transaction.commit();

		return;
	}

	public void go2SelectMedicineFragment()
	{
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)m_context;

		SelectMedicineFragment selectMedicineFragment = new SelectMedicineFragment();

		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.medicine_reminder_add_page, selectMedicineFragment, SelectMedicineFragment.class.getName());
		transaction.commit();

		return;
	}

	private void requestMedicineReminderListAction()
	{
		RequestMedicinePromptGetListEvent event = new RequestMedicinePromptGetListEvent();
		event.setUID(DAccount.GetInstance().getId());
		DBusinessMsgHandler.GetInstance().requestMedicinePromptGetListAction(event);
		return;
	}

	public void requestAddMedicalPromptAction(RequestMedicinePromptAddEvent event)
	{
		DBusinessMsgHandler.GetInstance().requestMedicinePromptAddAction(event);
		return;
	}

	public void onEventMainThread(AnswerMedicinePromptAddEvent event)
	{
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)m_context;

		//01. 添加成功，update list
		requestMedicineReminderListAction();

		int MID = event.getMID();
		//01. 在药箱中，找到。
		String tips = "";
		DMedicineBox medicineBox = DBusinessData.GetInstance().getMedicineBoxList().getMedicineBoxByMID(MID);
		if (medicineBox != null)
		{
			activity.findInMedicineBoxAction();
			return;
		}
		//02. 在药箱中，没找到。
		else
		{
			activity.nothingInMedicineBoxAction();
		}

	}

	//发送药品列表
	public void requestMedicalListAction()
	{
		DBusinessMsgHandler.GetInstance().requestMedicineGetListAction();
		return;
	}

	public void onEventMainThread(AnswerMedicineGetListEvent event)
	{
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)m_context;

		Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(ADHChartFragment.class.getName());
		if (fragment == null)
			return;

		SelectMedicineFragment selectMedicineFragment = (SelectMedicineFragment)fragment;
		if (selectMedicineFragment == null)
			return;

		selectMedicineFragment.updateContent();
	}

	/**
	 * set
	 */
	public void setRemainderTime(Calendar reminderTime)
	{
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)m_context;

		activity.setReminderTime(reminderTime);
	}

	public void setMedicalID(int medicalID)
	{
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)m_context;

		DMedicine medical = DBusinessData.GetInstance().getMedicineList().getMedicineByID(medicalID);
		if (medical == null)
		{
			TipsDialog.GetInstance().setMsg("Input medicalID is invalid![medicalID:=" + medicalID + "]", activity);
			TipsDialog.GetInstance().show();
			return;
		}

		activity.setMedicineID(medicalID);
		return;
	}

	public void setRose(double rose)
	{
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)m_context;
		activity.setDose(rose);

		return;
	}

	public void go2AddMedicine2BoxPage()
	{
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)m_context;
		activity.startActivity(new Intent(activity, DrugStockAddActivity.class));
		activity.finish();
		return;
	}
}
