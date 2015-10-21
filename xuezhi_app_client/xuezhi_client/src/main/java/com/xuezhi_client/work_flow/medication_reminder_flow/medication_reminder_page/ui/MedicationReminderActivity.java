package com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_page.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_page.msg_handler.MedicationReminderMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicationReminderActivity extends BaseActivity
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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_reminder);
		ButterKnife.bind(this);

		init();
	}

	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
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

	}

	public void updateContent()
	{
		m_medicineReminderAdapter.notifyDataSetChanged();
		return;
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



}
