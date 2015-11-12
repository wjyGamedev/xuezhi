package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_page.ui;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.AsyncWaitDialog;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptDeleteEvent;
import com.xuezhi_client.work_flow.medicine_reminder_flow.config.MedicineReminderPageConfig;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_page.msg_handler.MedicationReminderMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicineReminderActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.medication_reminder_display_lv) ListView m_medicationReminderDisplayLV = null;    //用药提醒列表

	private BottomCommon m_bottomCommon = null;

	//logical
	private int                     m_selectedMedicineReminderID = DataConfig.DEFAULT_VALUE;
	private MedicineReminderAdapter m_medicineReminderAdapter    = null;

	private MedicationReminderMsgHandler m_medicationReminderMsgHandler = new MedicationReminderMsgHandler(this);
	private ClickAddBottomBtn            m_clickAddBottomBtn            = new ClickAddBottomBtn();
	private HandleDeleteDialogEvent      m_handleDeleteDialogEvent      = new HandleDeleteDialogEvent();

	private AsyncWaitDialog        m_asyncWaitDialog        = new AsyncWaitDialog();
	private HandleAsyncDislogEvent m_handleAsyncDislogEvent = new HandleAsyncDislogEvent();

	private int m_waitDeletedMPID = DataConfig.DEFAULT_ID;

	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
	}

	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_medication_reminder);
		return this;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
	}

	@Override
	public void onDestoryAction()
	{

	}

	/**
	 * overrider func
	 */
	class ClickAddBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_medicationReminderMsgHandler.go2MedicineReminderAddPage();
		}
	}

	class HandleAsyncDislogEvent implements AsyncWaitDialog.HandleWaitDialogFinishedEvent
	{

		@Override
		public void OnWaitDialogFinished()
		{
			m_asyncWaitDialog.dismiss();
		}
	}

	class HandleDeleteDialogEvent implements DialogInterface.OnClickListener
	{

		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			//01. 删除
			if (which == DialogInterface.BUTTON_POSITIVE)
			{
				RequestMedicinePromptDeleteEvent event = new RequestMedicinePromptDeleteEvent();
				event.setUID(DAccount.GetInstance().getId());
				event.setMPID(String.valueOf(m_waitDeletedMPID));
				m_medicationReminderMsgHandler.requestMedicineReminderDeleteAction(event);
				m_asyncWaitDialog.show();
			}
			dialog.dismiss();
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.medication_reminder_page_title_text);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.setBtnNum(1);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.medication_reminder_add_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickAddBottomBtn);

		m_medicineReminderAdapter = new MedicineReminderAdapter(this);
		m_medicationReminderDisplayLV.setAdapter(m_medicineReminderAdapter);

		m_asyncWaitDialog.init(this, MedicineReminderPageConfig.WAIT_TIME_FOR_DELETE_MEDICINE_REMINDER_ITEM);
		m_asyncWaitDialog.setHandleWaitDialogFinishedEvent(m_handleAsyncDislogEvent);
	}

	public void updateContent()
	{
		m_medicineReminderAdapter.notifyDataSetChanged();
		return;
	}



	public int getWaitDeletedMPID()
	{
		return m_waitDeletedMPID;
	}

	/**
	 * get:func
	 */


	public int getSelectedMedicineReminderID()
	{
		return m_selectedMedicineReminderID;
	}

	public MedicationReminderMsgHandler getMedicationReminderMsgHandler()
	{
		return m_medicationReminderMsgHandler;
	}

	public void popDeleteDialog(int MPID)
	{
		m_waitDeletedMPID = MPID;
		DMedicinePrompt medicinePrompt = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPromptByID(m_waitDeletedMPID);
		if (medicinePrompt == null)
		{
			TipsDialog.GetInstance().setMsg("medicinePrompt == null!Input MPID is invalid!MPID:=[" + MPID + "]");
			TipsDialog.GetInstance().show();
			return;
		}
		TipsDialog.GetInstance().setMsg(getString(R.string.medication_reminder_temp_delete_item_tips),
										this,
										m_handleDeleteDialogEvent,
										m_handleDeleteDialogEvent);
		TipsDialog.GetInstance().show();
	}

	public void closeAsyncDialog()
	{
		m_asyncWaitDialog.dismiss();
	}
}
