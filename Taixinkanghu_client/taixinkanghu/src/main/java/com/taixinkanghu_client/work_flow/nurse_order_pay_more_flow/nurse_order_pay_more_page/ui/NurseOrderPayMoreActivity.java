/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.nurse_order_pay_more.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/25		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.nurse_order_pay_more_flow.nurse_order_pay_more_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestNurseOrderPayMoreEvent;
import com.taixinkanghu_client.data_module.register_account.data.DAccount;
import com.taixinkanghu_client.work_flow.nurse_order_pay_more_flow.nurse_order_pay_more_page.msg_handler.NurseOrderPayMoreMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NurseOrderPayMoreActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.reason_option_rbtn)           RadioButton m_reasonOptionRbtn = null;
	@Bind (R.id.reason_value_region_rgrp)     RadioGroup  m_reasonValueRGrp  = null;
	@Bind (R.id.reason_value_care_rbtn)       RadioButton m_careRBtn         = null;
	@Bind (R.id.reason_value_half_care_rbtn)  RadioButton m_halfCareRBtn     = null;
	@Bind (R.id.reason_value_cannt_care_rbtn) RadioButton m_canntCareRBtn    = null;
	@Bind (R.id.price_tv)                     EditText    m_priceET          = null;

	Button m_confirmBtn = null;
	private BottomCommon m_bottomCommon = null;

	//logical
	private NurseOrderPayMoreMsgHandler m_nurseOrderPayMoreMsgHandler = new NurseOrderPayMoreMsgHandler(this);
	private CommonBottomClickEvent      m_commonBottomClickEvent      = new CommonBottomClickEvent();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_order_pay_more);
		ButterKnife.bind(this);

		init();
	}

	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
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

	/**
	 * override func
	 */
	class CommonBottomClickEvent implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			//01. 判断差价金额
			String payMoreprice = m_priceET.getText().toString();
			if (TextUtils.isEmpty(payMoreprice))
			{
				TipsDialog.GetInstance().setMsg(getString(R.string.pay_more_tips_price), NurseOrderPayMoreActivity.this);
				TipsDialog.GetInstance().show();
				return;
			}
			int payMorePrice = Integer.valueOf(payMoreprice);

			int selectValueID = m_reasonValueRGrp.getCheckedRadioButtonId();
			if (selectValueID == -1)
			{
				TipsDialog.GetInstance().setMsg(getString(R.string.pay_more_tips_reason), NurseOrderPayMoreActivity.this);
				TipsDialog.GetInstance().show();
				return;
			}

			//02. 发送event
			String reasonOption = getString(R.string.pay_more_reason_option);
			String reasonValue  = null;
			if (selectValueID == R.id.reason_value_care_rbtn)
			{
				reasonValue = EnumConfig.PatientState.PATIENT_STATE_CARE_MYSELF.getName();
			}
			else if (selectValueID == R.id.reason_value_half_care_rbtn)
			{
				reasonValue = EnumConfig.PatientState.PATIENT_STATE_HALF_CARE_MYSELF.getName();
			}
			else if (selectValueID == R.id.reason_value_cannt_care_rbtn)
			{
				reasonValue = EnumConfig.PatientState.PATIENT_STATE_CANNT_CARE_MYSELF.getName();
			}
			else
			{
				TipsDialog.GetInstance().setMsg("selectValueID is invalid!", NurseOrderPayMoreActivity.this);
				TipsDialog.GetInstance().show();
				return;
			}

			//02. 发送Event
			RequestNurseOrderPayMoreEvent event = new RequestNurseOrderPayMoreEvent();
			event.setUserID(DAccount.GetInstance().getId());
			event.setOrderID(String.valueOf(m_nurseOrderPayMoreMsgHandler.getSelectedNurseOrderID()));
			event.setTotalPrice(payMorePrice);
			event.setReasonOption(reasonOption);
			event.setReasonValue(reasonValue);

			m_nurseOrderPayMoreMsgHandler.requestNurseOrderPayMoreAction(event);
			return;


		}
	}


	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.pay_more_title);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(getString(R.string.content_confirm_btn));
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_commonBottomClickEvent);
	}

	private void updateContent()
	{
		m_reasonOptionRbtn.setChecked(true);

		int selectValueID = m_reasonValueRGrp.getCheckedRadioButtonId();
		if (selectValueID != DataConfig.DEFAULT_VALUE)
		{
			if (selectValueID == R.id.reason_value_care_rbtn)
			{
				m_careRBtn.setChecked(true);
				m_halfCareRBtn.setChecked(false);
				m_canntCareRBtn.setChecked(false);
			}
			else if (selectValueID == R.id.reason_value_half_care_rbtn)
			{
				m_careRBtn.setChecked(false);
				m_halfCareRBtn.setChecked(true);
				m_canntCareRBtn.setChecked(false);
			}
			else if (selectValueID == R.id.reason_value_cannt_care_rbtn)
			{
				m_careRBtn.setChecked(false);
				m_halfCareRBtn.setChecked(false);
				m_canntCareRBtn.setChecked(true);
			}
		}

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

}
