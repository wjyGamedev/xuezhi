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

package com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.msg_handler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineUnit;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicineGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptAddEvent;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.ADHChartFragment;
import com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui.MedicineReminderAddActivity;
import com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui.SelectMedicineFragment;
import com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui.SelectMedicineTimeFragment;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MedicineReminderAddMsgHandler extends BaseUIMsgHandler
{
	private SimpleDateFormat m_hmSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE);

//	@Override
//	protected void init()
//	{
//		super.init();
//		m_eventBus.register(this);
//	}

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

	public void requestAddMedicalPromptAction()
	{
		RequestMedicinePromptAddEvent event = new RequestMedicinePromptAddEvent();
		event.setUID(DAccount.GetInstance().getId());

		DBusinessMsgHandler.GetInstance().requestMedicinePromptAddAction(event);
		return;
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

		String displayTime = m_hmSDF.format(reminderTime.getTime());

		activity.getMedicineTimeTV().setText(displayTime);
		activity.setRemainderTime(reminderTime);
	}

	public void setMedicalID(int medicalID)
	{
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)m_context;

		DMedicine medical = DBusinessData.GetInstance().getMedicalList().getMedicalByID(medicalID);
		if (medical == null)
		{
			TipsDialog.GetInstance().setMsg("Input medicalID is invalid![medicalID:=" + medicalID + "]", activity);
			TipsDialog.GetInstance().show();
			return;
		}

		//01. 用药名称，id
		activity.getMedicineNameTV().setText(medical.getName());
		activity.setMedicineID(medicalID);

		//02. 用药单位
		int unitID = medical.getMUID();
		DMedicineUnit medicalUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(unitID);
		if (medicalUnit == null)
		{
			TipsDialog.GetInstance().setMsg("medicalUnit == null!unitID is invalid![unitID:="+unitID+"]", activity);
			TipsDialog.GetInstance().show();
			//TODO:这里出错不返回。
		}
		else
		{
			activity.getMedicineUnit().setText(medicalUnit.getUnitName());
		}

		//03. 用药的注意事项，string
		activity.getPrecautionsTV().setText(medical.getPrecautions());
		return;
	}

	public void setRose(double rose)
	{
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)m_context;

		activity.getRoseTV().setText(String.valueOf(rose));
		activity.setRose(rose);

		return;
	}

}
