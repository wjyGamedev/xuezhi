package com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_setting_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_setting_page.msg_handler
		.MedicationReminderSettingMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicationReminderSettingActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;

	//logical
	private MedicationReminderSettingMsgHandler m_medicationReminderSettingMsgHandler = new MedicationReminderSettingMsgHandler(this);
	private ClickBottomBtn                      m_clickBottomBtn                      = new ClickBottomBtn();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_reminder_setting);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.medication_reminder_setting_page_title_text);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.medication_reminder_save_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);
	}

	/**
	 * overrider func
	 */
	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_medicationReminderSettingMsgHandler.saveMedicationReminderInfo();
		}
	}

}
