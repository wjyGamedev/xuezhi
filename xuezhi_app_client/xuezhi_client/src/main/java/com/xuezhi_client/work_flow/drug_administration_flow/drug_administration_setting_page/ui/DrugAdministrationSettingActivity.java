package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineBox;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePromptList;
import com.xuezhi_client.util.LogicalUtil;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_config.DrugAdministrationConfig;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page.msg_handler
		.DrugAdministrationSettingMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by Administrator on 2015/9/23.
 */
public class DrugAdministrationSettingActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;
	private BottomCommon m_addDelBtn    = null;

	private String m_selectMedicalStockID = null;

	private int      m_drugID               = DataConfig.DEFAULT_ID;
	private double   m_oldDrugStockNum      = 0.0f;
	private double   m_oldDrugAlertNum      = 0.0f;
	private boolean  m_oldDrugReminderState = false;
	private Calendar m_addCalendar          = null;
	private Calendar m_alertCalendar        = null;
	private int      m_medicalUnitID        = DataConfig.DEFAULT_ID;

	@Bind (R.id.drug_setting_reminder_state_cb)  CheckBox m_drugReminderStateCB = null;
	@Bind (R.id.drug_stock_setting_drug_name_tv) TextView m_drugNameTV          = null;
	@Bind (R.id.drug_setting_drug_stock_num_et)  EditText m_drugStockNumET      = null;
	@Bind (R.id.drug_setting_drug_alert_num_et)  EditText m_drugAlertNumET      = null;
	@Bind (R.id.drug_setting_add_date_tv)        TextView m_drugAddDateNumTV    = null;
	@Bind (R.id.drug_setting_run_out_date_tv)    TextView m_drugRunOutDateNumTV = null;
	@Bind (R.id.drug_setting_drug_stock_unit_tv) TextView m_drugStockUnitTV     = null;
	@Bind (R.id.drug_setting_drug_alert_unit_tv) TextView m_drugAlertUnitTV     = null;

	private boolean drugSettingReminderState = false;

	//logical
	private DrugAdministrationSettingMsgHandler m_drugAdministrationSettingMsgHandler = new DrugAdministrationSettingMsgHandler(this);
	private ClickSaveBtn                        m_clickSaveBtn                        = new ClickSaveBtn();
	private ClickDrugReminderStateCB            m_clickDrugReminderStateCB            = new ClickDrugReminderStateCB();

	/**
	 * inner func
	 */
	private void init()
	{
		m_selectMedicalStockID = getIntent().getStringExtra(DrugAdministrationConfig.SELECT_MEDICAL_STOCK_ID);
		int          selectMedicalStockID = Integer.valueOf(m_selectMedicalStockID);
		DMedicineBox selectMedicalStock   = DBusinessData.GetInstance().getMedicineBoxList().getMedicineBoxByID(selectMedicalStockID);
		if (selectMedicalStock == null)
		{
			TipsDialog.GetInstance().setMsg("selectMedicalStock == null!selectMedicalStock is null![m_selectMedicalStockID:=" +
													m_selectMedicalStockID + "]", this
										   );
			TipsDialog.GetInstance().show();
			return;
		}


		m_drugReminderStateCB.setOnCheckedChangeListener(m_clickDrugReminderStateCB);

		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.drug_administration_setting_page_title_text);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.drug_administration_setting_save_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickSaveBtn);

	}

	@OnTextChanged (R.id.drug_setting_drug_stock_num_et)
	public void drugStockNumETonTextChanged(CharSequence s, int start, int before, int count)
	{
		if (inspection_data())
		{
			m_drugRunOutDateNumTV.setText(calculateRunOutData());
		}

		return;
	}

	@OnTextChanged (R.id.drug_setting_drug_alert_num_et)
	public void drugAlertNumETonTextChanged(CharSequence s, int start, int before, int count)
	{
		if (inspection_data())
		{
			m_drugRunOutDateNumTV.setText(calculateRunOutData());
		}

		return;
	}


	@Override
	protected void onStart()
	{
		m_drugAdministrationSettingMsgHandler.DrugSettingPageFillingContent();
		super.onStart();
	}

	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_drug_administration_setting);
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
	 * overrider func
	 */
	private class ClickSaveBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_drugAdministrationSettingMsgHandler.saveDrugStockSettingInfo();
		}
	}

	private class ClickDrugReminderStateCB implements CompoundButton.OnCheckedChangeListener
	{
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		{
			if (isChecked)
				m_drugReminderStateCB.setText(getResources().getString(R.string.drug_administration_setting_alert_state_open_text));
			else
				m_drugReminderStateCB.setText(getResources().getString(R.string.drug_administration_setting_alert_state_close_text));

			setDrugSettingReminderState(isChecked);
		}
	}

	public boolean inspection_data()
	{
		String drugStockNum  = String.valueOf(m_drugStockNumET.getText());
		String drugwaringNum = String.valueOf(m_drugAlertNumET.getText());

		if (drugStockNum == null || drugStockNum.equals(""))
			return false;
		if (drugwaringNum == null || drugwaringNum.equals(""))
			return false;
		if (m_drugID == DataConfig.DEFAULT_ID)
			return false;
		return true;
	}

	public String calculateRunOutData()
	{
		DMedicine medical          = DBusinessData.GetInstance().getMedicineList().getMedicineByID(m_drugID);

		double    amountPerTime = 0f; //每次用量
		if (medical == null)
		{
			throw new JsonSerializationException("medical == null!m_MID is invalid![m_MID:=" + medical.getID() + "]");
		}

		DMedicinePromptList medicinePromptList = DBusinessData.GetInstance().getMedicinePromptList();
		if (medicinePromptList != null)
		{
			ArrayList<DMedicinePrompt> medicinePrompt = medicinePromptList.getMedicalPrompts();
			if (medicinePrompt != null)
			{
				for (DMedicinePrompt tmpMedicinePrompt : medicinePrompt)
				{
					if (tmpMedicinePrompt.getMID() == medical.getID())
					{
						amountPerTime += tmpMedicinePrompt.getDose();
					}
				}
			}
		}

		if (amountPerTime == 0)
		{
			amountPerTime = medical.getDose();
			if (amountPerTime == 0)
			{
				throw new JsonSerializationException("amountPerTime == 0![m_MID:=" + medical.getID() + "]");
			}
		}

		String drugStockNum  = String.valueOf(m_drugStockNumET.getText());
		String drugwaringNum = String.valueOf(m_drugAlertNumET.getText());

		double remianNum = Double.valueOf(drugStockNum);
		double waringNum = Double.valueOf(drugwaringNum);

		double reminderValue = remianNum - waringNum;

		Calendar warningCalendar = LogicalUtil.getExhaustTime(amountPerTime, reminderValue);

		SimpleDateFormat sdf         = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);
		String           warningDate = sdf.format(warningCalendar.getTime());
		return warningDate;
	}


	public TextView getDrugNameTV()
	{
		return m_drugNameTV;
	}

	public void setDrugNameTV(TextView drugNameTV)
	{
		m_drugNameTV = drugNameTV;
	}

	public EditText getDrugStockNumET()
	{
		return m_drugStockNumET;
	}

	public void setDrugStockNumET(EditText drugStockNumET)
	{
		m_drugStockNumET = drugStockNumET;
	}

	public EditText getDrugAlertNumET()
	{
		return m_drugAlertNumET;
	}

	public void setDrugAlertNumET(EditText drugAlertNumET)
	{
		m_drugAlertNumET = drugAlertNumET;
	}

	public TextView getDrugAddDateNumTV()
	{
		return m_drugAddDateNumTV;
	}

	public void setDrugAddDateNumTV(TextView drugAddDateNumTV)
	{
		m_drugAddDateNumTV = drugAddDateNumTV;
	}

	public boolean isDrugSettingReminderState()
	{
		return drugSettingReminderState;
	}

	public void setDrugSettingReminderState(boolean drugSettingReminderState)
	{
		this.drugSettingReminderState = drugSettingReminderState;
	}

	public TextView getDrugRunOutDateNumTV()
	{
		return m_drugRunOutDateNumTV;
	}

	public void setDrugRunOutDateNumTV(TextView drugRunOutDateNumTV)
	{
		m_drugRunOutDateNumTV = drugRunOutDateNumTV;
	}

	public String getSelectMedicalStockID()
	{
		return m_selectMedicalStockID;
	}

	public void setSelectMedicalStockID(String selectMedicalStockID)
	{
		m_selectMedicalStockID = selectMedicalStockID;
	}

	public int getDrugID()
	{
		return m_drugID;
	}

	public void setDrugID(int drugID)
	{
		m_drugID = drugID;
	}

	public double getOldDrugStockNum()
	{
		return m_oldDrugStockNum;
	}

	public void setOldDrugStockNum(double oldDrugStockNum)
	{
		m_oldDrugStockNum = oldDrugStockNum;
	}

	public String getNewDrugStockNum()
	{
		return m_drugStockNumET.getText().toString();
	}

	public double getOldDrugAlertNum()
	{
		return m_oldDrugAlertNum;
	}

	public void setOldDrugAlertNum(double oldDrugAlertNum)
	{
		m_oldDrugAlertNum = oldDrugAlertNum;
	}

	public String getNewDrugAlertNum()
	{
		return m_drugAlertNumET.getText().toString();
	}

	public boolean isOldDrugReminderState()
	{
		return m_oldDrugReminderState;
	}

	public void setOldDrugReminderState(boolean oldDrugReminderState)
	{
		m_oldDrugReminderState = oldDrugReminderState;
	}

	public boolean isNewDrugReminderState()
	{
		return m_drugReminderStateCB.isChecked();
	}

	public Calendar getAddCalendar()
	{
		return m_addCalendar;
	}

	public void setAddCalendar(Calendar addCalendar)
	{
		m_addCalendar = addCalendar;
	}

	public Calendar getAlertCalendar()
	{
		return m_alertCalendar;
	}

	public void setAlertCalendar(Calendar alertCalendar)
	{
		m_alertCalendar = alertCalendar;
	}

	public int getMedicalUnitID()
	{
		return m_medicalUnitID;
	}

	public void setMedicalUnitID(int medicalUnitID)
	{
		m_medicalUnitID = medicalUnitID;
	}

	public CheckBox getDrugReminderStateCB()
	{
		return m_drugReminderStateCB;
	}

	public void setDrugReminderStateCB(CheckBox drugReminderStateCB)
	{
		m_drugReminderStateCB = drugReminderStateCB;
	}

	public TextView getDrugStockUnitTV()
	{
		return m_drugStockUnitTV;
	}

	public void setDrugStockUnitTV(TextView drugStockUnitTV)
	{
		m_drugStockUnitTV = drugStockUnitTV;
	}

	public TextView getDrugAlertUnitTV()
	{
		return m_drugAlertUnitTV;
	}

	public void setDrugAlertUnitTV(TextView drugAlertUnitTV)
	{
		m_drugAlertUnitTV = drugAlertUnitTV;
	}


}
