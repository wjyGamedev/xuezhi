package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicinePromptDeleteEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicinePromptGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptDeleteEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptGetListEvent;
import com.xuezhi_client.work_flow.data.DWaitForRemainder;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.ui.MedicineReminderAddActivity;
import com.xuezhi_client.work_flow.medicine_reminder_flow.config.MedicineReminderPageConfig;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_page.ui.MedicineReminderActivity;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.ui.MedicineReminderSettingActivity;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicationReminderMsgHandler extends BaseUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public MedicationReminderMsgHandler(MedicineReminderActivity activity)
	{
		super(activity);
	}

	public void go2MedicineReminderAddPage()
	{
		//01. 跳转到药品管理设置页面
		MedicineReminderActivity activity = (MedicineReminderActivity)m_context;

		Intent intent = new Intent(activity, MedicineReminderAddActivity.class);
		activity.startActivity(intent);

		return;
	}

	public void go2MedicineReminderSettingPage(int MPID)
	{
		MedicineReminderActivity activity = (MedicineReminderActivity)m_context;

		Intent intent = new Intent(activity, MedicineReminderSettingActivity.class);
		intent.putExtra(MedicineReminderPageConfig.SELECTED_MEDICINE_REMINDER_ID, MPID);
		activity.startActivity(intent);

	}

	public void requestMedicineReminderDeleteAction(RequestMedicinePromptDeleteEvent event)
	{
		DBusinessMsgHandler.GetInstance().requestMedicinePromptDeleteAction(event);
	}

	public void onEventMainThread(AnswerMedicinePromptDeleteEvent event)
	{
		RequestMedicinePromptGetListEvent getListEvent = new RequestMedicinePromptGetListEvent();
		getListEvent.setUID(DAccount.GetInstance().getId());
		requestMedicinePromptGetListAction(getListEvent);
	}

	private void requestMedicinePromptGetListAction(RequestMedicinePromptGetListEvent event)
	{
		DBusinessMsgHandler.GetInstance().requestMedicinePromptGetListAction(event);
	}

	public void onEventMainThread(AnswerMedicinePromptGetListEvent event)
	{
		MedicineReminderActivity activity = (MedicineReminderActivity)m_context;
		activity.updateContent();
		activity.closeAsyncDialog();
		DWaitForRemainder.GetInstance().updateTakeMedicineReminderContent();
	}

}
