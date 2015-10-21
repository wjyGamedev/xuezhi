package com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_setting_page.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_setting_page.msg_handler.MedicationReminderSettingMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicationReminderSettingActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.reminder_state_cb) CheckBox m_reminderStateCB = null;
	@Bind (R.id.medicine_name_tv)  TextView m_medicineNameTV  = null;
	@Bind (R.id.rose_tv)           TextView m_RoseTV          = null;
	@Bind (R.id.precautions_tv)    TextView m_precautionsTV   = null;

	private BottomCommon m_bottomCommon = null;

	//Literals
	private String OPEN  = null;
	private String CLOSE = null;

	//logical
	private MedicationReminderSettingMsgHandler m_medicationReminderSettingMsgHandler = new MedicationReminderSettingMsgHandler(this);

	private ClickSaveBtn m_clickSaveBtn = new ClickSaveBtn();

	//date
	private boolean m_stateFlag  = false;    //开启状态。
	private int     m_medicineID = DataConfig.DEFAULT_VALUE;    //药品ID
	private double  m_rose       = 0f;    //用药剂量
	private int     m_remarkID   = DataConfig.DEFAULT_VALUE;    //备注

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_reminder_add);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * widget:func
	 */
	@OnCheckedChanged (R.id.reminder_state_cb)
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if (isChecked)
			m_reminderStateCB.setText(OPEN);
		else
			m_reminderStateCB.setText(CLOSE);

		setStateFlag(isChecked);
	}


	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.medicine_reminder_add_content);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.medication_reminder_save_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickSaveBtn);

		OPEN = getString(R.string.medicine_reminder_add_open_content);
		CLOSE = getString(R.string.medicine_reminder_add_close_content);

	}

	/**
	 * overrider func
	 */
	class ClickSaveBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_medicationReminderSettingMsgHandler.requestSettingMedicalPromptAction();
		}
	}

	/**
	 * date:get/set
	 */
	public boolean isStateFlag()
	{
		return m_stateFlag;
	}

	public void setStateFlag(boolean stateFlag)
	{
		m_stateFlag = stateFlag;
	}

	public int getRemarkID()
	{
		return m_remarkID;
	}

	public void setRemarkID(int remarkID)
	{
		m_remarkID = remarkID;
	}

	public double getRose()
	{
		return m_rose;
	}

	public void setRose(double rose)
	{
		m_rose = rose;
	}

	public int getMedicineID()
	{
		return m_medicineID;
	}

	public void setMedicineID(int medicineID)
	{
		m_medicineID = medicineID;
	}

}
