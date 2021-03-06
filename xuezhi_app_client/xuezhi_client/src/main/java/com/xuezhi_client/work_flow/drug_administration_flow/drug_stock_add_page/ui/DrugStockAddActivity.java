package com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.ui;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePromptList;
import com.xuezhi_client.util.LogicalUtil;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.msg_handler.DrugStockAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Administrator on 2015/9/30.
 */
public class DrugStockAddActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;

	private                                   int          m_drugID              = DataConfig.DEFAULT_ID;
	private                                   double       m_drugStockNum        = 0.0f;
	private                                   double       m_drugAlertNum        = 0.0f;
	private                                   boolean      m_drugReminderState   = true;
	private                                   Calendar     m_addCalendar         = null;
	private                                   Calendar     m_alertCalendar       = null;
	private                                   int          m_medicalUnitID       = DataConfig.DEFAULT_ID;
	@Bind (R.id.drug_reminder_state_cb)       CheckBox     m_drugReminderStateCB = null;
	@Bind (R.id.drug_add_drug_name_region_ll) LinearLayout m_drugNameRegionLL    = null;
	@Bind (R.id.drug_stock_add_drug_name)     TextView     m_drugNameTV          = null;
	@Bind (R.id.drug_add_drug_stock_num_et)   EditText     m_drugStockNumET      = null;
	@Bind (R.id.drug_add_drug_alert_num_et)   EditText     m_drugAlertNumET      = null;
	@Bind (R.id.drug_add_date_tv)             TextView     m_drugAddDateTV       = null;
	@Bind (R.id.drug_add_run_out_tv)          TextView     m_drugRunOutTV        = null;
	@Bind (R.id.drug_add_drug_stock_unit_tv)  TextView     m_drugStockUnitTV     = null;
	@Bind (R.id.drug_add_drug_alert_unit_tv)  TextView     m_drugAlertUnitTV     = null;

	private ClickSaveBottomBtn       m_clickSaveBottomBtn       = new ClickSaveBottomBtn();
	private ClickDrugReminderStateCB m_clickDrugReminderStateCB = new ClickDrugReminderStateCB();

	private DrugStockAddMsgHandler m_drugStockAddMsgHandler = new DrugStockAddMsgHandler(this);


	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.drug_stock_add_page_title_text);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.drug_stock_add_save_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickSaveBottomBtn);

		m_drugReminderStateCB.setOnCheckedChangeListener(m_clickDrugReminderStateCB);

		//获取当前日期
		SimpleDateFormat sdf       = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);
		String           toDayDate = sdf.format(new java.util.Date());
		m_drugAddDateTV.setText(toDayDate);
		m_drugRunOutTV.setText(toDayDate);

	}

	@OnTextChanged (R.id.drug_add_drug_stock_num_et)
	public void drugStockNumETonTextChanged(CharSequence s, int start, int before, int count)
	{
		if (inspection_data())
		{
			m_drugRunOutTV.setText(calculateRunOutData());
		}

		return;
	}

	@OnTextChanged (R.id.drug_add_drug_alert_num_et)
	public void drugAlertNumETonTextChanged(CharSequence s, int start, int before, int count)
	{
		if (inspection_data())
		{
			m_drugRunOutTV.setText(calculateRunOutData());
		}

		return;
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
		DMedicine drug          = DBusinessData.GetInstance().getMedicineList().getMedicineByID(m_drugID);
		double    amountPerTime = 0f;

		DMedicinePromptList medicinePromptList = DBusinessData.GetInstance().getMedicinePromptList();
		if (medicinePromptList != null)
		{
			ArrayList<DMedicinePrompt> medicinePrompt = medicinePromptList.getMedicalPrompts();
			if (medicinePrompt != null)
			{
				for (DMedicinePrompt tmpMedicinePrompt : medicinePrompt)
				{
					if (tmpMedicinePrompt.getMID() == drug.getID())
					{
						amountPerTime += tmpMedicinePrompt.getDose();
					}
				}
			}
		}

		if (amountPerTime == 0)
		{
			amountPerTime = drug.getDose();
			if (amountPerTime == 0)
			{
				throw new JsonSerializationException("amountPerTime == 0![m_MID:=" + drug.getID() + "]");
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


	@OnClick (R.id.drug_add_drug_name_region_ll)
	public void clickNameRegion()
	{
		m_drugStockAddMsgHandler.go2SelectDrugFragment();
		return;
	}

	/**
	 * overrider func
	 */
	class ClickSaveBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_drugStockAddMsgHandler.saveDrugInfo();
		}
	}

	private class ClickDrugReminderStateCB implements CompoundButton.OnCheckedChangeListener
	{
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		{
			if (isChecked)
				m_drugReminderStateCB.setText(getString(R.string.drug_stock_add_alert_state_open_text));
			else
				m_drugReminderStateCB.setText(getString(R.string.drug_stock_add_alert_state_close_text));

			setDrugReminderState(isChecked);
		}
	}

	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
	}

	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_drug_stock_add);
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

	public void updateContent()
	{
	}


	public DrugStockAddMsgHandler getDrugStockAddMsgHandler()
	{
		return m_drugStockAddMsgHandler;
	}

	public TextView getDrugNameTV()
	{
		return m_drugNameTV;
	}

	public int getDrugID()
	{
		return m_drugID;
	}

	public void setDrugID(int m_drugID)
	{
		this.m_drugID = m_drugID;
	}

	public String getDrugStockNum()
	{
		return m_drugStockNumET.getText().toString();
	}

	public void setDrugStockNum(double drugStockNum)
	{
		m_drugStockNum = drugStockNum;
	}

	public String getDrugAlertNum()
	{
		return m_drugAlertNumET.getText().toString();
	}

	public void setDrugAlertNum(double drugAlertNum)
	{
		m_drugAlertNum = drugAlertNum;
	}

	public boolean isDrugReminderState()
	{
		return m_drugReminderState;
	}

	public void setDrugReminderState(boolean drugReminderState)
	{
		m_drugReminderState = drugReminderState;
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

	public CheckBox getDrugReminderStateCB()
	{
		return m_drugReminderStateCB;
	}

	public int getMedicalUnitID()
	{
		return m_medicalUnitID;
	}

	public void setMedicalUnitID(int medicalUnitID)
	{
		m_medicalUnitID = medicalUnitID;
	}

	public TextView getDrugStockUnitTV()
	{
		return m_drugStockUnitTV;
	}

	public TextView getDrugAlertUnitTV()
	{
		return m_drugAlertUnitTV;
	}

	public TextView getDrugRunOutTV()
	{
		return m_drugRunOutTV;
	}
}
