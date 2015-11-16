package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.ui;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineUnit;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptSetEvent;
import com.xuezhi_client.work_flow.medicine_reminder_flow.config.MedicineReminderPageConfig;
import com.xuezhi_client.work_flow.medicine_reminder_flow.data.DMedicineReminderDisplay;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.msg_handler.MedicationReminderSettingMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;

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

	private NoChangeEvent   m_noChangeEvent   = new NoChangeEvent();
	private SetSuccessEvent m_setSuccessEvent = new SetSuccessEvent();

	//date
	private int                      m_MPID                       = DataConfig.DEFAULT_ID;
	private DMedicineReminderDisplay m_oldMedicineReminderDisplay = new DMedicineReminderDisplay();
	private DMedicineReminderDisplay m_newMedicineReminderDisplay = new DMedicineReminderDisplay();

	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_medication_reminder_add);
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
	 * widget:func
	 */
	@OnCheckedChanged (R.id.reminder_state_cb)
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		setStateFlag(isChecked);
	}

//	@OnClick (R.id.reminder_time_region_ll)
//	public void selectMedicineTime(View v)
//	{
//		m_medicationReminderSettingMsgHandler.go2SelectMedicineTimeFragment();
//	}

	@OnClick (R.id.medicine_name_region_ll)
	public void selectMedicine(View v)
	{
		m_medicationReminderSettingMsgHandler.go2SelectMedicineFragment();
	}

	@OnFocusChange (R.id.rose_tv)
	public void leaveDoseEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		String content = m_roseTV.getText().toString();
		if (!TextUtils.isEmpty(content))
		{
			try
			{
				m_newMedicineReminderDisplay.setDose(Double.valueOf(content));
			}
			catch (NumberFormatException e)
			{
				m_newMedicineReminderDisplay.setDose(0);
			}
		}
		return;
	}

	/**
	 * override func
	 */
	class NoChangeEvent implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			if (which == DialogInterface.BUTTON_POSITIVE)
			{
				m_medicationReminderSettingMsgHandler.go2MedicineReminderPage();
			}
			dialog.dismiss();
		}
	}

	class SetSuccessEvent implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			dialog.dismiss();
			finish();
		}
	}

	class ClickSaveBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			String content = m_roseTV.getText().toString();
			if (!TextUtils.isEmpty(content))
			{
				try
				{
					m_newMedicineReminderDisplay.setDose(Double.valueOf(content));
				}
				catch (NumberFormatException e)
				{
					m_newMedicineReminderDisplay.setDose(0);
				}
			}

			if (!check())
				return;

			RequestMedicinePromptSetEvent event = new RequestMedicinePromptSetEvent();
			event.setUID(DAccount.GetInstance().getId());
			event.setMPID(String.valueOf(m_MPID));

			DMedicinePrompt medicinePrompt = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPromptByID(m_MPID);
			if (medicinePrompt != null)
			{
				event.setMID(String.valueOf(medicinePrompt.getMID()));
			}
			event.setMID(String.valueOf(m_newMedicineReminderDisplay.getMedicineID()));
			event.setTime(m_newMedicineReminderDisplay.getReminderTime());
			event.setValid(m_newMedicineReminderDisplay.isStateFlag());
			event.setDose(m_newMedicineReminderDisplay.getDose());
			event.setPrecaution(m_newMedicineReminderDisplay.getPrecaution());
			m_medicationReminderSettingMsgHandler.requestSettingMedicineReminderAction(event);
		}
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
		setReminderTime(m_oldMedicineReminderDisplay.getReminderTime());
		setMedicineID(m_oldMedicineReminderDisplay.getMedicineID());
		setDose(m_oldMedicineReminderDisplay.getDose());
		setPrecaution(m_oldMedicineReminderDisplay.getPrecaution());
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

		if (m_newMedicineReminderDisplay.getDose() == 0)
		{
			TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.ERROR_INPUT_ROSE_ZERO,this);
			TipsDialog.GetInstance().show();
			return false;
		}


		//02. 数据没有变化则不发送setting
		if (m_oldMedicineReminderDisplay.isStateFlag() != m_newMedicineReminderDisplay.isStateFlag())
			return true;

		if (m_oldMedicineReminderDisplay.getMedicineID() != m_newMedicineReminderDisplay.getMedicineID())
			return true;

		if (m_oldMedicineReminderDisplay.getReminderTime() != m_newMedicineReminderDisplay.getReminderTime())
			return true;

		if (m_oldMedicineReminderDisplay.getDose() != m_newMedicineReminderDisplay.getDose())
			return true;

		//TODO：需要测试
		if ( m_oldMedicineReminderDisplay.getPrecaution().equals(m_newMedicineReminderDisplay.getPrecaution()) == false )
			return true;

		TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.ERROR_NO_CHANGE,
										this,
										MedicineReminderPageConfig.INFO_GO_BACK,
										m_noChangeEvent,
										MedicineReminderPageConfig.INFO_CONTINUE,
										m_noChangeEvent
									   );
		TipsDialog.GetInstance().show();
		return false;
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
		return m_newMedicineReminderDisplay.getReminderTime();
	}

	public void setReminderTime(Calendar remainderTime)
	{
		m_newMedicineReminderDisplay.setReminderTime(remainderTime);

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
			setPrecaution(medicine.getPrecautions());
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

	public String getPrecaution()
	{
		return m_newMedicineReminderDisplay.getPrecaution();
	}

	public void setPrecaution(String precaution)
	{
		m_newMedicineReminderDisplay.setPrecaution(precaution);
		m_precautionsTV.setText(precaution);
	}

	public MedicationReminderSettingMsgHandler getMedicationReminderSettingMsgHandler()
	{
		return m_medicationReminderSettingMsgHandler;
	}

	public void popSetSuccessAction()
	{
		TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.INFO_SET_SUCCESS, m_setSuccessEvent);
		TipsDialog.GetInstance().show();
	}
}
