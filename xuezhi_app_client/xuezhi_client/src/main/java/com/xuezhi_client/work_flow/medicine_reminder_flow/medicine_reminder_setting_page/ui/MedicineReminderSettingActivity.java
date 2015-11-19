package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_setting_page.ui;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineCompany;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineUnit;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptSetEvent;
import com.xuezhi_client.work_flow.medicine_reminder_flow.config.MedicineReminderPageConfig;
import com.xuezhi_client.work_flow.medicine_reminder_flow.data.DSingleReminder;
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

	@Bind (R.id.reminder_state_cb)   CheckBox     m_reminderStateCB  = null;
	@Bind (R.id.reminder_time_tv)    TextView     m_medicineTimeTV   = null;
	@Bind (R.id.medicine_name_tv)    TextView     m_medicineNameTV   = null;
	@Bind (R.id.rose_tv)             TextView     m_roseTV           = null;
	@Bind (R.id.medicine_unit_tv)    TextView     m_medicineUnit     = null;
	@Bind (R.id.precation_region_ll) LinearLayout mPrecationRegionLL = null;


	private BottomCommon m_bottomCommon = null;

	//logical
	private MedicationReminderSettingMsgHandler m_medicationReminderSettingMsgHandler = new MedicationReminderSettingMsgHandler(this);

	private ClickSaveBtn m_clickSaveBtn = new ClickSaveBtn();

	private SimpleDateFormat m_hmSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE);

	private NoChangeEvent   m_noChangeEvent   = new NoChangeEvent();
	private SetSuccessEvent m_setSuccessEvent = new SetSuccessEvent();

	//date
	private int                      m_MPID                       = DataConfig.DEFAULT_ID;

	private DSingleReminder mOldSingleReminder = new DSingleReminder();
	private DSingleReminder mNewSingleReminder = new DSingleReminder();

	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_medication_reminder_setting);
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

	@OnClick(R.id.reminder_time_region_ll)
	public void selectReminderTime()
	{
		m_medicationReminderSettingMsgHandler.go2SelectMedicineTimeFragment();
	}

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
				mNewSingleReminder.setDose(Double.valueOf(content));
			}
			catch (NumberFormatException e)
			{
				mNewSingleReminder.setDose(0);
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
					mNewSingleReminder.setDose(Double.valueOf(content));
				}
				catch (NumberFormatException e)
				{
					mNewSingleReminder.setDose(0);
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
			event.setMID(String.valueOf(mNewSingleReminder.getMedicineID()));
			event.setTime(mNewSingleReminder.getReminderTime());
			event.setValid(mNewSingleReminder.isStateFlag());
			event.setDose(mNewSingleReminder.getDose());
			event.setPrecaution(mNewSingleReminder.getPrecaution());
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

		mPrecationRegionLL.setVisibility(View.INVISIBLE);

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

		if (!mOldSingleReminder.init(m_MPID))
		{
			TipsDialog.GetInstance().setMsg("m_MPID is invalid!", this);
			TipsDialog.GetInstance().show();
			return;
		}

		if (!mNewSingleReminder.init(m_MPID))
		{
			TipsDialog.GetInstance().setMsg("m_MPID is invalid!", this);
			TipsDialog.GetInstance().show();
			return;
		}

		setStateFlag(mOldSingleReminder.isStateFlag());
		setReminderTime(mOldSingleReminder.getReminderTime());
		setMedicineID(mOldSingleReminder.getMedicineID());
		setDose(mOldSingleReminder.getDose());

		mPrecationRegionLL.setVisibility(View.GONE);
	}

	private boolean check()
	{
		//01. 输入不能为空。
		if (TextUtils.isEmpty(m_medicineTimeTV.getText()))
		{
			TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.ERROR_INPUT_REMINDER_TIME, this);
			TipsDialog.GetInstance().show();
			return false;
		}

		if (TextUtils.isEmpty(m_medicineNameTV.getText()))
		{
			TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.ERROR_INPUT_MEDICINE_NAME, this);
			TipsDialog.GetInstance().show();
			return false;
		}

		if (TextUtils.isEmpty(m_roseTV.getText()))
		{
			TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.ERROR_INPUT_ROSE, this);
			TipsDialog.GetInstance().show();
			return false;
		}

		if (mNewSingleReminder.getDose() == 0)
		{
			TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.ERROR_INPUT_ROSE_ZERO, this);
			TipsDialog.GetInstance().show();
			return false;
		}


		//02. 数据没有变化则不发送setting
		if (mOldSingleReminder.isStateFlag() != mNewSingleReminder.isStateFlag())
			return true;

		if (mOldSingleReminder.getMedicineID() != mNewSingleReminder.getMedicineID())
			return true;

		if (mOldSingleReminder.getReminderTime() != mNewSingleReminder.getReminderTime())
			return true;

		if (mOldSingleReminder.getDose() != mNewSingleReminder.getDose())
			return true;

		//TODO：需要测试
		if (mOldSingleReminder.getPrecaution().equals(mNewSingleReminder.getPrecaution()) == false)
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
	public void setStateFlag(boolean stateFlag)
	{
		mNewSingleReminder.setStateFlag(stateFlag);

		if (stateFlag)
			m_reminderStateCB.setText(MedicineReminderPageConfig.OPEN);
		else
			m_reminderStateCB.setText(MedicineReminderPageConfig.CLOSE);

	}

	public Calendar getReminderTime()
	{
		return mNewSingleReminder.getReminderTime();
	}

	public void setReminderTime(Calendar remainderTime)
	{
		mNewSingleReminder.setReminderTime(remainderTime);

		String reminderTimeDisplay = m_hmSDF.format(remainderTime.getTime());
		m_medicineTimeTV.setText(reminderTimeDisplay);

	}

	public int getMedicineID()
	{
		return mNewSingleReminder.getMedicineID();
	}

	public void setMedicineID(int medicineID)
	{
		mNewSingleReminder.setMedicineID(medicineID);

		DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(medicineID);
		if (medicine != null)
		{
			String name = medicine.getName();
			DMedicineCompany medicineCompany = DBusinessData.GetInstance().getMedicineCompanyList().getMedicineCompanyByID(medicine
																																   .getCID());
			if (medicineCompany != null)
			{
				name = name + "(" +medicineCompany.getName() + ")";
			}
			m_medicineNameTV.setText(name);
		}

		DMedicineUnit medicineUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(medicineID);
		if (medicineUnit != null)
		{
			m_medicineUnit.setText(medicineUnit.getUnitName());
		}

	}

	public double getDose()
	{
		return mNewSingleReminder.getDose();
	}

	public void setDose(double dose)
	{
		mNewSingleReminder.setDose(dose);
		m_roseTV.setText(String.valueOf(dose));
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
