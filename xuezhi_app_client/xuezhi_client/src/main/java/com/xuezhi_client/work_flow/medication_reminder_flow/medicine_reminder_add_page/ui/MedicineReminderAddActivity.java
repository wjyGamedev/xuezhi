/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/20		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.msg_handler.MedicineReminderAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MedicineReminderAddActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.reminder_state_cb) CheckBox m_reminderStateCB = null;
	@Bind (R.id.reminder_time_tv)  TextView m_medicineTimeTV  = null;
	@Bind (R.id.medicine_name_tv)  TextView m_medicineNameTV  = null;
	@Bind (R.id.rose_tv)           TextView m_roseTV          = null;
	@Bind (R.id.medicine_unit_tv)  TextView m_medicineUnit    = null;

	@Bind (R.id.precautions_tv) TextView m_precautionsTV = null;

	private BottomCommon m_bottomCommon = null;

	//Literals
	private String OPEN  = null;
	private String CLOSE = null;

	//logical
	private MedicineReminderAddMsgHandler m_medicineReminderAddMsgHandler = new MedicineReminderAddMsgHandler(this);

	private ClickSaveBtn m_clickSaveBtn = new ClickSaveBtn();

	//date
	private boolean  m_stateFlag     = false;    //开启状态。
	private Calendar m_remainderTime = null;    //提醒时间
	private int      m_medicineID    = DataConfig.DEFAULT_VALUE;    //药品ID
	private double   m_rose          = 0f;    //用药剂量

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

	@OnClick (R.id.reminder_time_tv)
	public void selectMedicineTime(View v)
	{
		m_medicineReminderAddMsgHandler.go2SelectMedicineTimeFragment();
	}

	@OnClick (R.id.medicine_name_tv)
	public void selectMedicine(View v)
	{
		m_medicineReminderAddMsgHandler.go2SelectMedicineFragment();
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
			m_medicineReminderAddMsgHandler.requestAddMedicalPromptAction();
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

	public Calendar getRemainderTime()
	{
		return m_remainderTime;
	}

	public void setRemainderTime(Calendar remainderTime)
	{
		m_remainderTime = remainderTime;
	}

	public int getMedicineID()
	{
		return m_medicineID;
	}

	public void setMedicineID(int medicineID)
	{
		m_medicineID = medicineID;
	}

	public double getRose()
	{
		return m_rose;
	}

	public void setRose(double rose)
	{
		m_rose = rose;
	}

	public MedicineReminderAddMsgHandler getMedicineReminderAddMsgHandler()
	{
		return m_medicineReminderAddMsgHandler;
	}

	/**
	 * widget:get
	 */
	public TextView getMedicineTimeTV()
	{
		return m_medicineTimeTV;
	}

	public TextView getMedicineNameTV()
	{
		return m_medicineNameTV;
	}

	public TextView getRoseTV()
	{
		return m_roseTV;
	}

	public TextView getMedicineUnit()
	{
		return m_medicineUnit;
	}

	public TextView getPrecautionsTV()
	{
		return m_precautionsTV;
	}
}
