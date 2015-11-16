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
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineUnit;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptAddEvent;
import com.xuezhi_client.work_flow.medicine_reminder_flow.config.MedicineReminderPageConfig;
import com.xuezhi_client.work_flow.medicine_reminder_flow.data.DMedicineReminderDisplay;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.msg_handler.MedicineReminderAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class MedicineReminderAddActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.reminder_state_cb)       CheckBox     m_reminderStateCB      = null;

	@Bind (R.id.medicine_name_tv)        TextView     m_medicineNameTV       = null;
	@Bind (R.id.num_per_day_et)          EditText     mNumPerDayET           = null;
	@Bind (R.id.rose_tv)                 EditText     m_roseTV               = null;
	@Bind (R.id.medicine_unit_tv)        TextView     m_medicineUnit         = null;
	@Bind (R.id.precautions_tv)          TextView     m_precautionsTV        = null;

	@Bind (R.id.reminder_time_1_region_ll) LinearLayout mReminderTime1RegionLL = null;
	@Bind (R.id.reminder_time_1_tv)        TextView     m_medicineTime1TV       = null;
	@Bind (R.id.reminder_time_2_region_ll) LinearLayout mReminderTime2RegionLL = null;
	@Bind (R.id.reminder_time_2_tv)        TextView     m_medicineTime2TV       = null;
	@Bind (R.id.reminder_time_3_region_ll) LinearLayout mReminderTime3RegionLL = null;
	@Bind (R.id.reminder_time_3_tv)        TextView     m_medicineTime3TV       = null;
	@Bind (R.id.reminder_time_4_region_ll) LinearLayout mReminderTime4RegionLL = null;
	@Bind (R.id.reminder_time_4_tv)        TextView     m_medicineTime4TV       = null;

	private BottomCommon m_bottomCommon = null;

	//logical
	private MedicineReminderAddMsgHandler m_medicineReminderAddMsgHandler = new MedicineReminderAddMsgHandler(this);

	private ClickSaveBtn    m_clickSaveBtn    = new ClickSaveBtn();
	private AddSuccessEvent m_addSuccessEvent = new AddSuccessEvent();
	private Add2BoxEvent    m_add2BoxEvent    = new Add2BoxEvent();

	//date
	private DMedicineReminderDisplay m_medicineReminderDisplay = new DMedicineReminderDisplay();

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
	@OnFocusChange(R.id.num_per_day_et)
	public void leaveNumPerDayET(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		setNumPerDay();
		return;
	}

	@OnFocusChange (R.id.rose_tv)
	public void leaveDoseEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		setDose();
		return;
	}

//	@OnClick (R.id.reminder_time_region_ll)
//	public void selectMedicineTime(View v)
//	{
//		m_medicineReminderAddMsgHandler.go2SelectMedicineTimeFragment();
//	}

	@OnClick (R.id.medicine_name_region_ll)
	public void selectMedicine(View v)
	{
		m_medicineReminderAddMsgHandler.go2SelectMedicineFragment();
	}

	@OnCheckedChanged (R.id.reminder_state_cb)
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		setStateFlag(isChecked);

		if (isChecked)
			m_reminderStateCB.setText(MedicineReminderPageConfig.OPEN);
		else
			m_reminderStateCB.setText(MedicineReminderPageConfig.CLOSE);

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

		mReminderTime1RegionLL.setVisibility(View.INVISIBLE);
		mReminderTime2RegionLL.setVisibility(View.INVISIBLE);
		mReminderTime3RegionLL.setVisibility(View.INVISIBLE);
		mReminderTime4RegionLL.setVisibility(View.INVISIBLE);

	}

	private void setNumPerDay()
	{
		String numPerDayDisplay = mNumPerDayET.getText().toString();

		int num = 1;
		if (!TextUtils.isEmpty(numPerDayDisplay))
		{
			try
			{
				num = Integer.valueOf(numPerDayDisplay);
				if (num <= 0)
				{
					num = 1;
				}
				else if (num >= 4)
				{
					num = 4;
				}

			}
			catch (NumberFormatException e)
			{
				num = 1;
			}
		}
		setNumPerDay(num);

		if (num == 1)
		{
			mReminderTime1RegionLL.setVisibility(View.VISIBLE);
			mReminderTime2RegionLL.setVisibility(View.INVISIBLE);
			mReminderTime3RegionLL.setVisibility(View.INVISIBLE);
			mReminderTime4RegionLL.setVisibility(View.INVISIBLE);
		}
		else if (num == 2)
		{
			mReminderTime1RegionLL.setVisibility(View.VISIBLE);
			mReminderTime2RegionLL.setVisibility(View.VISIBLE);
			mReminderTime3RegionLL.setVisibility(View.INVISIBLE);
			mReminderTime4RegionLL.setVisibility(View.INVISIBLE);
		}
		else if (num == 3)
		{
			mReminderTime1RegionLL.setVisibility(View.VISIBLE);
			mReminderTime2RegionLL.setVisibility(View.VISIBLE);
			mReminderTime3RegionLL.setVisibility(View.VISIBLE);
			mReminderTime4RegionLL.setVisibility(View.INVISIBLE);
		}
		else if (num == 4)
		{
			mReminderTime1RegionLL.setVisibility(View.VISIBLE);
			mReminderTime2RegionLL.setVisibility(View.VISIBLE);
			mReminderTime3RegionLL.setVisibility(View.VISIBLE);
			mReminderTime4RegionLL.setVisibility(View.VISIBLE);
		}
		else
		{
			mReminderTime1RegionLL.setVisibility(View.VISIBLE);
			mReminderTime2RegionLL.setVisibility(View.INVISIBLE);
			mReminderTime3RegionLL.setVisibility(View.INVISIBLE);
			mReminderTime4RegionLL.setVisibility(View.INVISIBLE);
		}

	}

	private void setDose()
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
//		if (TextUtils.isEmpty(m_medicineTimeTV.getText()))
//		{
//			TipsDialog.GetInstance().setMsg(MedicineReminderPageConfig.ERROR_INPUT_REMINDER_TIME, this);
//			TipsDialog.GetInstance().show();
//			return false;
//		}

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

		return true;
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

			if (!m_medicineReminderDisplay.isStateFlag())
				setStateFlag(m_reminderStateCB.isChecked());

			setDose();

			RequestMedicinePromptAddEvent event = new RequestMedicinePromptAddEvent();
			event.setUID(DAccount.GetInstance().getId());
			event.setMID(String.valueOf(m_medicineReminderDisplay.getMedicineID()));
			event.setValid(m_medicineReminderDisplay.isStateFlag());
			event.setPrecaution(m_medicineReminderDisplay.getPrecaution());
			event.setNum(m_medicineReminderDisplay.getNumPerDay());

			for (int index = 0; index < m_medicineReminderDisplay.getNumPerDay(); ++index)
			{
				event.setDose(m_medicineReminderDisplay.getDose(), index);
//				event.setTime()
			}

			m_medicineReminderAddMsgHandler.requestAddMedicalPromptAction(event);
		}
	}

	/**
	 * date:get/set
	 */
	public boolean isStateFlag()
	{
		return m_medicineReminderDisplay.isStateFlag();
	}

	public void setStateFlag(boolean stateFlag)
	{
		m_medicineReminderDisplay.setStateFlag(stateFlag);

		if (stateFlag)
			m_reminderStateCB.setText(MedicineReminderPageConfig.OPEN);
		else
			m_reminderStateCB.setText(MedicineReminderPageConfig.CLOSE);
	}

	public Calendar getReminderTime()
	{
		return m_medicineReminderDisplay.getReminderTime();
	}

	public void setReminderTime(Calendar remainderTime)
	{
		m_medicineReminderDisplay.setReminderTime(remainderTime);

		String reminderTimeDisplay = m_hmSDF.format(remainderTime.getTime());
//		m_medicineTimeTV.setText(reminderTimeDisplay);
	}

	public int getMedicineID()
	{
		return m_medicineReminderDisplay.getMedicineID();
	}

	public void setMedicineID(int medicineID)
	{
		m_medicineReminderDisplay.setMedicineID(medicineID);

		DMedicine     medicine     = DBusinessData.GetInstance().getMedicineList().getMedicineByID(medicineID);
		DMedicineUnit medicineUnit = null;
		if (medicine != null)
		{
			m_medicineNameTV.setText(medicine.getName());
			setPrecaution(medicine.getPrecautions());

			int medicineUnitID = medicine.getMUID();
			medicineUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(medicineUnitID);
		}

		if (medicineUnit != null)
		{
			m_medicineUnit.setText(medicineUnit.getUnitName());
		}

	}

	public double getDose()
	{
		return m_medicineReminderDisplay.getDose();
	}

	public void setNumPerDay(int numPerDay)
	{
		m_medicineReminderDisplay.setNumPerDay(numPerDay);
		mNumPerDayET.setText(String.valueOf(numPerDay));
	}

	public void setDose(double dose)
	{
		m_medicineReminderDisplay.setDose(dose);
		m_roseTV.setText(String.valueOf(dose));
	}

	public String getPrecaution()
	{
		return m_medicineReminderDisplay.getPrecaution();
	}

	public void setPrecaution(String precaution)
	{
		m_medicineReminderDisplay.setPrecaution(precaution);
		m_precautionsTV.setText(precaution);
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
