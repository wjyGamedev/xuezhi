package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.msg_handler;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicineGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicinePromptSetEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptSetEvent;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.ADHChartFragment;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_page.ui.MedicineReminderActivity;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.ui.MedicineReminderSettingActivity;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.ui.SelectMedicineFragment;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.ui.SelectMedicineTimeFragment;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicationReminderSettingMsgHandler extends BaseUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public MedicationReminderSettingMsgHandler(MedicineReminderSettingActivity activity)
	{
		super(activity);
	}

	public void go2MedicineReminderPage()
	{
		MedicineReminderSettingActivity activity = (MedicineReminderSettingActivity)m_context;

		Intent intent = new Intent(activity, MedicineReminderActivity.class);
		activity.startActivity(intent);
	}

	public void requestSettingMedicineReminderAction(RequestMedicinePromptSetEvent event)
	{
		DBusinessMsgHandler.GetInstance().requestMedicinePromptSetAction(event);
	}

	public void onEventMainThread(AnswerMedicinePromptSetEvent event)
	{
		MedicineReminderSettingActivity activity = (MedicineReminderSettingActivity)m_context;
		requestMedicineReminderListAction();
		activity.popSetSuccessAction();

	}

	private void requestMedicineReminderListAction()
	{
		RequestMedicinePromptGetListEvent event = new RequestMedicinePromptGetListEvent();
		event.setUID(DAccount.GetInstance().getId());
		DBusinessMsgHandler.GetInstance().requestMedicinePromptGetListAction(event);
		return;
	}

	public void go2SelectMedicineTimeFragment()
	{
		MedicineReminderSettingActivity activity = (MedicineReminderSettingActivity)m_context;

		SelectMedicineTimeFragment selectMedicineTimeFragment = new SelectMedicineTimeFragment();

		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.medicine_reminder_setting_page, selectMedicineTimeFragment, SelectMedicineTimeFragment.class.getName());
		transaction.commit();
	}

	public void go2SelectMedicineFragment()
	{
		MedicineReminderSettingActivity activity = (MedicineReminderSettingActivity)m_context;

		SelectMedicineFragment selectMedicineFragment = new SelectMedicineFragment();

		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.medicine_reminder_setting_page, selectMedicineFragment, SelectMedicineFragment.class.getName());
		transaction.commit();

	}

	public void setRemainderTime(Calendar reminderTime)
	{
		MedicineReminderSettingActivity activity = (MedicineReminderSettingActivity)m_context;

		activity.setReminderTime(reminderTime);
	}

	public void setMedicalID(int medicalID)
	{
		MedicineReminderSettingActivity activity = (MedicineReminderSettingActivity)m_context;

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
		MedicineReminderSettingActivity activity = (MedicineReminderSettingActivity)m_context;
		activity.setDose(rose);
	}

	public void requestMedicalListAction()
	{
		DBusinessMsgHandler.GetInstance().requestMedicineGetListAction();
	}

	public void onEventMainThread(AnswerMedicineGetListEvent event)
	{
		MedicineReminderSettingActivity activity = (MedicineReminderSettingActivity)m_context;

		Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(ADHChartFragment.class.getName());
		if (fragment == null)
			return;

		SelectMedicineFragment selectMedicineFragment = (SelectMedicineFragment)fragment;
		if (selectMedicineFragment == null)
			return;

		selectMedicineFragment.updateContent();
	}

}
