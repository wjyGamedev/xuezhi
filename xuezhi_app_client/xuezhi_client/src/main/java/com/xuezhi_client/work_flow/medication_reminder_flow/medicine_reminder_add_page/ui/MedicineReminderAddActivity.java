/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/20		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptAddEvent;
import com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.msg_handler.MedicineReminderAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class MedicineReminderAddActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.reminder_state_cb)       CheckBox     m_reminderStateCB      = null;
	@Bind (R.id.reminder_time_region_ll) LinearLayout m_medicineTimeRegionLL = null;
	@Bind (R.id.reminder_time_tv)        TextView     m_medicineTimeTV       = null;
	@Bind (R.id.medicine_name_tv)        TextView     m_medicineNameTV       = null;
	@Bind (R.id.rose_tv)                 TextView     m_roseTV               = null;
	@Bind (R.id.medicine_unit_tv)        TextView     m_medicineUnit         = null;

	@Bind (R.id.precautions_tv) TextView m_precautionsTV = null;

	private BottomCommon m_bottomCommon = null;

	//Literals
	private String OPEN                      = null;
	private String CLOSE                     = null;
	private String ERROR_INPUT_REMINDER_TIME = null;
	private String ERROR_INPUT_MEDICINE_NAME = null;
	private String ERROR_INPUT_ROSE          = null;

	//logical
	private MedicineReminderAddMsgHandler m_medicineReminderAddMsgHandler = new MedicineReminderAddMsgHandler(this);

	private ClickSaveBtn    m_clickSaveBtn    = new ClickSaveBtn();
	private AddSuccessEvent m_addSuccessEvent = new AddSuccessEvent();

	//date
	private boolean         m_stateFlag       = false;    //开启状态。
	private Calendar        m_remainderTime   = null;    //提醒时间
	private int             m_medicineID      = DataConfig.DEFAULT_VALUE;    //药品ID
	private double          m_dose            = 0f;    //用药剂量

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_reminder_add);
		ButterKnife.bind(this);

		init();
	}


	class AddSuccessEvent implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			if (which == DialogInterface.BUTTON_POSITIVE)
			{
				m_medicineReminderAddMsgHandler.go2AddMedicine2BoxPage();
			}
			else
			{
				finish();
			}
		}
	}

	/**
	 * widget:func
	 */
	@OnFocusChange (R.id.rose_tv)
	public void leaveDoseEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		initDose();
		return;
	}

	@OnClick (R.id.reminder_time_region_ll)
	public void selectMedicineTime(View v)
	{
		m_medicineReminderAddMsgHandler.go2SelectMedicineTimeFragment();
	}

	@OnClick (R.id.medicine_name_region_ll)
	public void selectMedicine(View v)
	{
		m_medicineReminderAddMsgHandler.go2SelectMedicineFragment();
	}

	@OnCheckedChanged (R.id.reminder_state_cb)
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		m_stateFlag = isChecked;

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
		ERROR_INPUT_REMINDER_TIME = getString(R.string.medicine_reminder_add_error_input_reminder_time);
		ERROR_INPUT_MEDICINE_NAME = getString(R.string.medicine_reminder_add_error_input_medicine_name_time);
		ERROR_INPUT_ROSE = getString(R.string.medicine_reminder_add_error_input_rose_time);
	}

	private void initDose()
	{
		String content = m_roseTV.getText().toString();
		if (!TextUtils.isEmpty(content))
		{
			try
			{
				m_dose = Double.valueOf(content);
			}
			catch (NumberFormatException e)
			{
				m_dose = 0;
			}
		}
	}
	/**
	 * overrider func
	 */
	class ClickSaveBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if (!check())
				return;

			if (!m_stateFlag)
				m_stateFlag = m_reminderStateCB.isChecked();

			initDose();

			RequestMedicinePromptAddEvent event = new RequestMedicinePromptAddEvent();
			event.setUID(DAccount.GetInstance().getId());
			event.setMID(String.valueOf(m_medicineID));
			event.setTime(m_remainderTime);
			event.setValid(m_stateFlag);
			event.setDose(m_dose);
			event.setPrecaution(getPrecautionsTV().getText().toString());
			m_medicineReminderAddMsgHandler.requestAddMedicalPromptAction(event);
		}
	}

	/**
	 * inner:func
	 */
	private boolean check()
	{
		if (TextUtils.isEmpty(m_medicineTimeTV.getText()))
		{
			TipsDialog.GetInstance().setMsg(ERROR_INPUT_REMINDER_TIME,this);
			TipsDialog.GetInstance().show();
			return false;
		}

		if (TextUtils.isEmpty(m_medicineNameTV.getText()))
		{
			TipsDialog.GetInstance().setMsg(ERROR_INPUT_MEDICINE_NAME,this);
			TipsDialog.GetInstance().show();
			return false;
		}

		if (TextUtils.isEmpty(m_roseTV.getText()))
		{
			TipsDialog.GetInstance().setMsg(ERROR_INPUT_ROSE,this);
			TipsDialog.GetInstance().show();
			return false;
		}

		return true;
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

	public double getDose()
	{
		return m_dose;
	}

	public void setDose(double dose)
	{
		m_dose = dose;
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

	/**
	 * logical:func
	 */
	public void findInMedicineBoxAction()
	{
		String tips = getString(R.string.medicine_reminder_add_success_tips);
		TipsDialog.GetInstance().setMsg(tips, this, m_addSuccessEvent);
		TipsDialog.GetInstance().show();
		return;
	}

	public void nothingInMedicineBoxAction()
	{
		String tips = getString(R.string.medicine_reminder_add_to_medicinebox_tips);
		String add = getString(R.string.medicine_reminder_add_medicinebox_content);
		String cancel = getString(R.string.medicine_reminder_add_cancel_content);
		TipsDialog.GetInstance().setMsg(tips, this, add, m_addSuccessEvent, cancel, m_addSuccessEvent);
		TipsDialog.GetInstance().show();
		return;
	}
}
