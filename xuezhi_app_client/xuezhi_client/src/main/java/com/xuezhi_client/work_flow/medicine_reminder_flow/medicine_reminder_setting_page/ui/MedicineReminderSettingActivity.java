package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineUnit;
import com.xuezhi_client.work_flow.medicine_reminder_flow.config.MedicineReminderPageConfig;
import com.xuezhi_client.work_flow.medicine_reminder_flow.data.DMedicineReminderDisplay;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.msg_handler.MedicationReminderSettingMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicineReminderSettingActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.reminder_state_cb) CheckBox m_reminderStateCB = null;
	@Bind (R.id.reminder_time_tv)  TextView m_medicineTimeTV  = null;
	@Bind (R.id.medicine_name_tv)  TextView m_medicineNameTV  = null;
	@Bind (R.id.rose_tv)           TextView m_roseTV          = null;
	@Bind (R.id.medicine_unit_tv)  TextView m_medicineUnit    = null;
	@Bind (R.id.precautions_tv)    TextView m_precautionsTV   = null;

	private BottomCommon m_bottomCommon = null;

	//logical
	private MedicationReminderSettingMsgHandler m_medicationReminderSettingMsgHandler = new MedicationReminderSettingMsgHandler(this);

	private ClickSaveBtn m_clickSaveBtn = new ClickSaveBtn();

	private SimpleDateFormat m_hmSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE);

	private NoChangeEvent            m_noChangeEvent              = new NoChangeEvent();
	//date
	private int                      m_MPID                       = DataConfig.DEFAULT_ID;
	private DMedicineReminderDisplay m_oldMedicineReminderDisplay = new DMedicineReminderDisplay();
	private DMedicineReminderDisplay m_newMedicineReminderDisplay = new DMedicineReminderDisplay();


	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_reminder_add);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * override func
	 */
	class NoChangeEvent implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			if (which == DialogInterface.BUTTON_NEGATIVE)
			{
				m_medicationReminderSettingMsgHandler.go2MedicineReminderPage();
			}
			dialog.dismiss();
		}
	}

	/**
	 * widget:func
	 */
	@OnCheckedChanged (R.id.reminder_state_cb)
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
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

		Intent intent = getIntent();
		m_MPID = intent.getIntExtra(MedicineReminderPageConfig.SELECTED_MEDICINE_REMINDER_ID, DataConfig.DEFAULT_VALUE);
		if (m_MPID == DataConfig.DEFAULT_VALUE)
		{
			TipsDialog.GetInstance().setMsg("m_MPID is invalid!", this);
			TipsDialog.GetInstance().show();
			return;
		}
		DMedicinePrompt medicinePrompt = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPromptByID(m_MPID);
		if (medicinePrompt == null)
		{
			TipsDialog.GetInstance().setMsg("m_MPID is invalid!", this);
			TipsDialog.GetInstance().show();
			return;
		}

		if (!m_oldMedicineReminderDisplay.init(m_MPID))
		{
			TipsDialog.GetInstance().setMsg("m_MPID is invalid!", this);
			TipsDialog.GetInstance().show();
			return;
		}

		if (!m_newMedicineReminderDisplay.init(m_MPID))
		{
			TipsDialog.GetInstance().setMsg("m_MPID is invalid!", this);
			TipsDialog.GetInstance().show();
			return;
		}

		setStateFlag(m_oldMedicineReminderDisplay.isStateFlag());
		setReminderTime(m_oldMedicineReminderDisplay.getRemainderTime());
		setMedicineID(m_oldMedicineReminderDisplay.getMedicineID());
		setDose(m_oldMedicineReminderDisplay.getDose());
	}

	private void initDose()
	{
		String content = m_roseTV.getText().toString();
		if (!TextUtils.isEmpty(content))
		{
			try
			{
				setDose(Double.valueOf(content));
			}
			catch (NumberFormatException e)
			{
				setDose(0);
			}
		}
	}

	private boolean check()
	{
		//01. 输入不能为空。
		if (TextUtils.isEmpty(m_medicineTimeTV.getText()))
		{
			TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.ERROR_INPUT_REMINDER_TIME,this);
			TipsDialog.GetInstance().show();
			return false;
		}

		if (TextUtils.isEmpty(m_medicineNameTV.getText()))
		{
			TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.ERROR_INPUT_MEDICINE_NAME,this);
			TipsDialog.GetInstance().show();
			return false;
		}

		if (TextUtils.isEmpty(m_roseTV.getText()))
		{
			TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.ERROR_INPUT_ROSE,this);
			TipsDialog.GetInstance().show();
			return false;
		}

		//02. 数据没有变化则不发送setting
		if (m_oldMedicineReminderDisplay.isStateFlag() != m_newMedicineReminderDisplay.isStateFlag())
			return true;

		if (m_oldMedicineReminderDisplay.getMedicineID() != m_newMedicineReminderDisplay.getMedicineID())
			return true;

		if (m_oldMedicineReminderDisplay.getRemainderTime() != m_newMedicineReminderDisplay.getRemainderTime())
			return true;

		if (m_oldMedicineReminderDisplay.getDose() != m_newMedicineReminderDisplay.getDose())
			return true;

		TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.ERROR_NO_CHANGE,this, MedicineReminderPageConfig.INFO_GO_BACK, m_noChangeEvent, MedicineReminderPageConfig.INFO_CONTINUE, m_noChangeEvent);
		TipsDialog.GetInstance().show();
		return false;
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

			if (!isStateFlag())
				setStateFlag(m_reminderStateCB.isChecked());

			initDose();

			m_medicationReminderSettingMsgHandler.requestSettingMedicalPromptAction();
		}
	}

	/**
	 * date:get/set
	 */
	public boolean isStateFlag()
	{
		return m_newMedicineReminderDisplay.isStateFlag();
	}

	public void setStateFlag(boolean stateFlag)
	{
		m_newMedicineReminderDisplay.setStateFlag(stateFlag);

		if (stateFlag)
			m_reminderStateCB.setText(MedicineReminderPageConfig.OPEN);
		else
			m_reminderStateCB.setText(MedicineReminderPageConfig.CLOSE);

	}

	public Calendar getReminderTime()
	{
		return m_newMedicineReminderDisplay.getRemainderTime();
	}

	public void setReminderTime(Calendar remainderTime)
	{
		m_newMedicineReminderDisplay.setRemainderTime(remainderTime);

		String reminderTimeDisplay =  m_hmSDF.format(remainderTime.getTime());
		m_medicineTimeTV.setText(reminderTimeDisplay);

	}

	public int getMedicineID()
	{
		return m_newMedicineReminderDisplay.getMedicineID();
	}

	public void setMedicineID(int medicineID)
	{
		m_newMedicineReminderDisplay.setMedicineID(medicineID);

		DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(medicineID);
		if (medicine != null)
		{
			m_medicineNameTV.setText(medicine.getName());
			m_precautionsTV.setText(medicine.getPrecautions());
		}

		DMedicineUnit medicineUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(medicineID);
		if (medicineUnit != null)
		{
			m_medicineUnit.setText(medicineUnit.getUnitName());
		}

	}

	public double getDose()
	{
		return m_newMedicineReminderDisplay.getDose();
	}

	public void setDose(double dose)
	{
		m_newMedicineReminderDisplay.setDose(dose);
		m_roseTV.setText(String.valueOf(dose));
	}


}
