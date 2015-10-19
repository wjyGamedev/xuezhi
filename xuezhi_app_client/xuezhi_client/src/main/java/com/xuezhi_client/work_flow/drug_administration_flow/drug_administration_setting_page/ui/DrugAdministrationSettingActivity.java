package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page.msg_handler
		.DrugAdministrationSettingMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/23.
 */
public class DrugAdministrationSettingActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;
	private BottomCommon m_addDelBtn    = null;

	private String m_selectMedicalStockID = null;

	private int      m_drugID            = -1;
	private double   m_drugStockNum      = 0.0f;
	private double   m_drugAlertNum      = 0.0f;
	private boolean  m_drugReminderState = false;
	private Calendar m_addCalendar       = null;
	private Calendar m_alertCalendar     = null;
	private int      m_medicalUnitID     = -1;

	@Bind (R.id.drug_setting_reminder_state_cb)  CheckBox m_drugReminderStateCB = null;
	@Bind (R.id.drug_stock_setting_drug_name_tv) TextView m_drugNameTV          = null;
	@Bind (R.id.drug_setting_drug_stock_num_et)  EditText m_drugStockNumET      = null;
	@Bind (R.id.drug_setting_drug_alert_num_et)  EditText m_drugAlertNumET      = null;
	@Bind (R.id.drug_setting_add_date_tv)        TextView m_drugAddDateNumTV    = null;
	@Bind (R.id.drug_setting_run_out_date_tv)    TextView m_drugRunOutDateNumTV = null;

	private boolean drugSettingReminderState = false;

	//logical
	private DrugAdministrationSettingMsgHandler m_drugAdministrationSettingMsgHandler = new DrugAdministrationSettingMsgHandler(this);
	private ClickSaveBtn                        m_clickSaveBtn                        = new ClickSaveBtn();
	private ClickDrugReminderStateCB            m_clickDrugReminderStateCB            = new ClickDrugReminderStateCB();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drug_administration_setting);
		ButterKnife.bind(this);
		init();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_selectMedicalStockID = getIntent().getStringExtra("selectMedicalStockID");

		m_drugReminderStateCB.setOnCheckedChangeListener(m_clickDrugReminderStateCB);

		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.drug_administration_setting_page_title_text);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.drug_administration_setting_save_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickSaveBtn);
	}

	@Override
	protected void onStart()
	{
		m_drugAdministrationSettingMsgHandler.DrugSettingPagefillingContent();
		super.onStart();
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
				m_drugReminderStateCB.setText("开启");
			else
				m_drugReminderStateCB.setText("关闭");

			setDrugSettingReminderState(isChecked);
		}
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

	public double getDrugStockNum()
	{
		return m_drugStockNum;
	}

	public void setDrugStockNum(double drugStockNum)
	{
		m_drugStockNum = drugStockNum;
	}

	public double getDrugAlertNum()
	{
		return m_drugAlertNum;
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
}
