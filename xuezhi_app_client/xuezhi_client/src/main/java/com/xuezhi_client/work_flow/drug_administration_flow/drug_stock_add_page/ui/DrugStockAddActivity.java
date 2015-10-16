package com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.msg_handler.DrugStockAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/9/30.
 */
public class DrugStockAddActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;

	private                                   int          m_drugID              = 0;
	private                                   double       m_drugStockNum        = 0.0f;
	private                                   double       m_drugAlertNum        = 0.0f;
	private                                   boolean      m_drugReminderState   = true;
	private                                   Calendar     m_addCalendar         = null;
	private                                   Calendar     m_alertCalendar       = null;
	//	private                                   int     m_medicalUnitID       = null;
	@Bind (R.id.drug_reminder_state_cb)       CheckBox     m_drugReminderStateCB = null;
	@Bind (R.id.drug_add_drug_name_region_ll) LinearLayout m_drugNameRegionLL    = null;
	@Bind (R.id.drug_stock_add_drug_name)     TextView     m_drugNameTV          = null;
	@Bind (R.id.drug_add_drug_stock_num_et)   EditText     m_drugStockNumET      = null;
	@Bind (R.id.drug_add_drug_alert_num_et)   EditText     m_drugAlertNumET      = null;
	@Bind (R.id.drug_add_date_tv)             TextView     m_drugAddDateTV       = null;
	@Bind (R.id.drug_add_run_out_tv)          TextView     m_drugRunOutTV        = null;

	private ClickSaveBottomBtn       m_clickSaveBottomBtn       = new ClickSaveBottomBtn();
	private ClickDrugReminderStateCB m_clickDrugReminderStateCB = new ClickDrugReminderStateCB();

	private DrugStockAddMsgHandler m_drugStockAddMsgHandler = new DrugStockAddMsgHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drug_stock_add);
		ButterKnife.bind(this);

		init();
		//		initWaitAction();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.drug_stock_add_page_title_text);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.drug_stock_add_save_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickSaveBottomBtn);

		m_drugReminderStateCB.setOnCheckedChangeListener(m_clickDrugReminderStateCB);

		//获取当前日期
		SimpleDateFormat sdf       = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);
		String           toDayDate = sdf.format(new java.util.Date());
		m_drugAddDateTV.setText(toDayDate);
		m_drugRunOutTV.setText(toDayDate);

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
				m_drugReminderStateCB.setText("开启");
			else
				m_drugReminderStateCB.setText("关闭");

			setDrugReminderState(isChecked);
		}
	}

	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
	}

	public void updateContent()
	{

	}

	//网上复制下来的代码，作用：点击EditText文本框之外任何地方隐藏键盘
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		if (ev.getAction() == MotionEvent.ACTION_DOWN)
		{
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev))
			{

				InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
				if (imm != null)
				{
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev))
		{
			return true;
		}
		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event)
	{
		if (v != null && (v instanceof EditText))
		{
			int[] leftTop = {0, 0};
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom)
			{
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			}
			else
			{
				return true;
			}
		}
		return false;
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
}
