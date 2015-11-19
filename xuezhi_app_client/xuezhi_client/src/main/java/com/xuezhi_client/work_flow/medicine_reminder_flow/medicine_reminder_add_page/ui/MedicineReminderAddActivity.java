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

package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.ui;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineCompany;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineUnit;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptAddEvent;
import com.xuezhi_client.work_flow.medicine_reminder_flow.config.MedicineReminderPageConfig;
import com.xuezhi_client.work_flow.medicine_reminder_flow.data.DMultiReminder;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.msg_handler.MedicineReminderAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class MedicineReminderAddActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.reminder_state_cb) CheckBox m_reminderStateCB = null;

	@Bind (R.id.medicine_name_tv)      TextView     m_medicineNameTV   = null;
	@Bind (R.id.num_per_day_region_ll) LinearLayout mNumPerDayRegionLl = null;

	@Bind (R.id.num_per_day_tv)      TextView     mNumPerDayTv       = null;
	@Bind (R.id.rose_tv)             EditText     m_roseTV           = null;
	@Bind (R.id.medicine_unit_tv)    TextView     m_medicineUnit     = null;
	@Bind (R.id.precation_region_ll) LinearLayout mPrecationRegionLL = null;
	@Bind (R.id.precautions_tv)      TextView     m_precautionsTV    = null;

	@Bind (R.id.reminder_time_container_ll) LinearLayout mReminderTimeCOntainerLl = null;
	private                                 BottomCommon m_bottomCommon           = null;

	//logical
	private MedicineReminderAddMsgHandler m_medicineReminderAddMsgHandler = new MedicineReminderAddMsgHandler(this);

	private ClickSaveBtn    m_clickSaveBtn    = new ClickSaveBtn();
	private AddSuccessEvent m_addSuccessEvent = new AddSuccessEvent();
	private Add2BoxEvent    m_add2BoxEvent    = new Add2BoxEvent();

	private ArrayList<ReminderTime> mReminderTimes = new ArrayList<>();

	//date
	private DMultiReminder           mMultiReminder            = new DMultiReminder();

	private SimpleDateFormat m_hmSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE);

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

	@Override
	protected void onStart()
	{
		super.onStart();
		updateContent();
	}

	private void updateContent()
	{
		mPrecationRegionLL.setVisibility(View.GONE);
		setTakeMedicineNumPerDay();
	}

	class Add2BoxEvent implements DialogInterface.OnClickListener
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

	class AddSuccessEvent implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			finish();
		}
	}

	/**
	 * widget:func
	 */
	@OnClick (R.id.num_per_day_region_ll)
	public void clickNumPerDayRegionLl()
	{
		m_medicineReminderAddMsgHandler.go2SelectTakeMedicineNumPerDayFragment();
	}

	@OnFocusChange (R.id.rose_tv)
	public void leaveDoseEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		syncDose();
		return;
	}

	@OnClick (R.id.medicine_name_region_ll)
	public void selectMedicine(View v)
	{
		m_medicineReminderAddMsgHandler.go2SelectMedicineFragment();
	}

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
	}

	private void setTakeMedicineNumPerDay()
	{
		//TODO:这里的控件生成还不够智能，之后换成智能的。
		String numPerDayDisplay = mNumPerDayTv.getText().toString();

		int num = MedicineReminderPageConfig.TAKE_MEDICINE_NUM_MIN_VALUE;
		if (!TextUtils.isEmpty(numPerDayDisplay))
		{
			try
			{
				num = Integer.valueOf(numPerDayDisplay);
				if (num <= 0)
				{
					num = MedicineReminderPageConfig.TAKE_MEDICINE_NUM_MIN_VALUE;
				}
				else if (num >= MedicineReminderPageConfig.TAKE_MEDICINE_NUM_MAX_VALUE)
				{
					num = MedicineReminderPageConfig.TAKE_MEDICINE_NUM_MAX_VALUE;
				}

			}
			catch (NumberFormatException e)
			{
				num = MedicineReminderPageConfig.TAKE_MEDICINE_NUM_MIN_VALUE;
			}
		}
		setTakeMedicineNumPerDay(num);



	}

	private boolean check()
	{
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

		for (ReminderTime reminderTime : mReminderTimes)
		{
			if (TextUtils.isEmpty(reminderTime.getTimeTv().getText()) == true)
			{
				String error = String.format(getString(R.string.medicine_reminder_add_error_input_take_medicine_x_time), reminderTime.getIndexDisplay());
				TipsDialog.GetInstance().setMsg(error, this);
				TipsDialog.GetInstance().show();
				return false;
			}
		}

		return true;
	}

	private void syncDose()
	{
		if (TextUtils.isEmpty(m_roseTV.getText()) == false)
		{
			double dose = 0f;
			try
			{
				dose = Double.valueOf(m_roseTV.getText().toString());
			}
			catch (NumberFormatException e)
			{
				dose = 1f;
			}

			for (int index = 0; index < mMultiReminder.getNumPerDay(); ++index)
			{
				mMultiReminder.setDoseArrayList(index, dose);
			}
		}
	}

	private void syncData()
	{
		if (mMultiReminder.isStateFlag() == DMultiReminder.DEFAULT_STATE_FLAG)
		{
			setStateFlag(m_reminderStateCB.isChecked());
		}

		if (mMultiReminder.getDoseArrayList().isEmpty())
		{
			syncDose();
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

			syncData();

			RequestMedicinePromptAddEvent event = new RequestMedicinePromptAddEvent();
			event.setUID(DAccount.GetInstance().getId());
			event.setMID(String.valueOf(mMultiReminder.getMedicineID()));
			event.setValid(mMultiReminder.isStateFlag());
			event.setPrecaution(mMultiReminder.getPrecaution());
			event.setNum(mMultiReminder.getNumPerDay());
			event.setDoseArrayList(mMultiReminder.getDoseArrayList());
			event.setCalendarArrayList(mMultiReminder.getReminderTimeArrayList());
			m_medicineReminderAddMsgHandler.requestAddMedicalPromptAction(event);
		}
	}

	/**
	 * date:get/set
	 */
	public void setStateFlag(boolean stateFlag)
	{
		mMultiReminder.setStateFlag(stateFlag);

		if (stateFlag)
			m_reminderStateCB.setText(MedicineReminderPageConfig.OPEN);
		else
			m_reminderStateCB.setText(MedicineReminderPageConfig.CLOSE);
	}

	public void setReminderTime(int index, Calendar remainderTime)
	{
		mMultiReminder.setReminderTimeArrayList(index, remainderTime);

		if (index >= mReminderTimes.size())
			return;

		String reminderTimeDisplay = m_hmSDF.format(remainderTime.getTime());
		mReminderTimes.get(index).getTimeTv().setText(reminderTimeDisplay);

	}

	public void setMedicineID(int medicineID)
	{
		mMultiReminder.setMedicineID(medicineID);

		DMedicine     medicine     = DBusinessData.GetInstance().getMedicineList().getMedicineByID(medicineID);
		DMedicineUnit medicineUnit = null;
		if (medicine != null)
		{
			String name = medicine.getName();
			DMedicineCompany medicineCompany = DBusinessData.GetInstance().getMedicineCompanyList().getMedicineCompanyByID(medicine.getCID());
			if (medicineCompany != null)
			{
				name = name + "(" + medicineCompany.getName() + ")";
			}
			m_medicineNameTV.setText(name);

			int medicineUnitID = medicine.getMUID();
			medicineUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(medicineUnitID);
		}

		if (medicineUnit != null)
		{
			m_medicineUnit.setText(medicineUnit.getUnitName());
		}

	}

	private boolean findInReminderTimeContaner(int index)
	{
		return (index < mReminderTimes.size());
	}
	public void setTakeMedicineNumPerDay(int numPerDay)
	{
		mMultiReminder.setNumPerDay(numPerDay);
		mNumPerDayTv.setText(String.valueOf(numPerDay));

		//01. 如果没有则添加。可见
		for (int index = 0; index < numPerDay; index++)
		{
			ReminderTime tmpReminderTime = null;
			if (findInReminderTimeContaner(index) == true)
			{
				tmpReminderTime = mReminderTimes.get(index);

			}
			else
			{
				tmpReminderTime = new ReminderTime(this, mReminderTimeCOntainerLl, index, m_medicineReminderAddMsgHandler);
				mReminderTimes.add(tmpReminderTime);
			}
			tmpReminderTime.setVisibility(View.VISIBLE);
		}

		//将其余的隐藏
		for (int otherIndex = numPerDay; otherIndex < mReminderTimes.size(); otherIndex++)
		{
			ReminderTime tmpReminderTime = mReminderTimes.get(otherIndex);
			tmpReminderTime.setVisibility(View.GONE);
		}
	}

	public MedicineReminderAddMsgHandler getMedicineReminderAddMsgHandler()
	{
		return m_medicineReminderAddMsgHandler;
	}

	/**
	 * widget:get
	 */

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
		String tips   = getString(R.string.medicine_reminder_add_to_medicinebox_tips);
		String add    = getString(R.string.medicine_reminder_add_medicinebox_content);
		String cancel = getString(R.string.medicine_reminder_add_cancel_content);
		TipsDialog.GetInstance().setMsg(tips, this, add, m_add2BoxEvent, cancel, m_add2BoxEvent);
		TipsDialog.GetInstance().show();
		return;
	}
}
