/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.order_confirm_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/9		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.repeat_order_flow.confirm_order_page.msg_handler;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;

import com.module.data.DGlobal;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.AnswerNurseOrderConfirmInNormalEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.NurseOrderListHandler;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestNurseOrderConfirmInNormalEvent;
import com.taixinkanghu_client.work_flow.repeat_order_flow.BaseRepeatFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.repeat_order_flow.confirm_order_page.ui.OrderConfirmActivity;
import com.taixinkanghu_client.work_flow.repeat_order_flow.confirm_order_page.ui.SelectPatientStateFragment;
import com.taixinkanghu_client.work_flow.repeat_order_flow.patient_info_page.ui.PatientActivity;
import com.taixinkanghu_client.work_flow.repeat_order_flow.nurse_order_pay_page.ui.NurseOrderPayInRepeatOrderActivity;
import com.taixinkanghu_client.work_flow.repeat_order_flow.user_protocal_page.ui.UserProtocalActivity;

public class OrderConfirmMsgHandler extends BaseRepeatFlowUIMsgHandler
{
	private final String UNIT_DAY  = DGlobal.GetInstance().getAppContext().getString(R.string.content_day);
	private final String UNIT_YUAN = DGlobal.GetInstance().getAppContext().getString(R.string.content_yuan);

	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public OrderConfirmMsgHandler(OrderConfirmActivity activity)
	{
		super(activity);
	}

	//01. 跳转到被护理人信息界面
	public void go2PatientInfoPage()
	{
		OrderConfirmActivity orderConfirmActivity = (OrderConfirmActivity)m_context;
		Intent               intent               = new Intent(orderConfirmActivity, PatientActivity.class);
		m_context.startActivity(intent);
		return;
	}

	//02. 弹出被护理人状态fragment
	public void go2PatientStateFragment()
	{
		OrderConfirmActivity orderConfirmActivity = (OrderConfirmActivity)m_context;

		int patientStateTitleHight = orderConfirmActivity.getSelectPatientStateTitleHight();

		FragmentTransaction        transaction                = orderConfirmActivity.getFragmentManager().beginTransaction();
		SelectPatientStateFragment selectPatientStateFragment = new SelectPatientStateFragment();
		selectPatientStateFragment.setPatientStateTitleHight(patientStateTitleHight);
		transaction.replace(R.id.order_confirm_page, selectPatientStateFragment, selectPatientStateFragment.getClass().getName());
		transaction.commit();

		return;
	}

	//03. 跳转到用户协议界面
	public void go2UserProtocalPage()
	{
		OrderConfirmActivity orderConfirmActivity = (OrderConfirmActivity)m_context;
		Intent               intent               = new Intent(orderConfirmActivity, UserProtocalActivity.class);
		m_context.startActivity(intent);
		return;
	}

	//04. 发送nurse order confirm event
	public void requestNurseOrderConfirmAction()
	{
		RequestNurseOrderConfirmInNormalEvent event = m_dRepeatOrderFlow.constructRequestNurseOrderConfirmInNormalEvent();
		NurseOrderListHandler.GetInstance().requestNurseOrderConfirmInNormalAction(event);
		return;
	}

	//05. nurse order confirm 返回消息处理：下单成功，跳转到支付页面
	public void onEventMainThread(AnswerNurseOrderConfirmInNormalEvent event)
	{
		OrderConfirmActivity orderConfirmActivity = (OrderConfirmActivity)m_context;

		//01. 将返回的关键数据同步到DAppiontmentNursing
		m_dRepeatOrderFlow.setOrderID(event.getOrderID());
		m_dRepeatOrderFlow.setOrderSerialNum(event.getOrderSerialNum());
		m_dRepeatOrderFlow.setTotalPrice(event.getTotalPrice());

		//02. 跳转到支付页面
		Intent intent = new Intent(orderConfirmActivity, NurseOrderPayInRepeatOrderActivity.class);
		orderConfirmActivity.startActivity(intent);

		return;
	}

	//06. 同步数据到UI
	public void updateContent()
	{
		OrderConfirmActivity orderConfirmActivity = (OrderConfirmActivity)m_context;

		int headerImgResID = m_dRepeatOrderFlow.getNurseHeaderImgResID();
		orderConfirmActivity.getNurseHeadImgIV().setImageResource(headerImgResID);

		String name = m_dRepeatOrderFlow.getNurseName();
		orderConfirmActivity.getNameTV().setText(name);

		String jobNum = m_dRepeatOrderFlow.getNurseJobNum();
		orderConfirmActivity.getJobNumTV().setText(jobNum);

		String nursingLevel = m_dRepeatOrderFlow.getNursingLevel();
		orderConfirmActivity.getNuringLevelTV().setText(nursingLevel);

		String serviceDate = m_dRepeatOrderFlow.getServiceDate();
		orderConfirmActivity.getServiceDateTV().setText(serviceDate);

		String serviceAddress = m_dRepeatOrderFlow.getServiceAddress();
		orderConfirmActivity.getServiceAddressTV().setText(serviceAddress);

		//被护理人
		String patientName = m_dRepeatOrderFlow.getPatientName();
		orderConfirmActivity.getPatientNameTV().setText(patientName);
		//被护理人状态
		EnumConfig.PatientState patientState = m_dRepeatOrderFlow.getPatientState();
		if (patientState == null)
		{
			TipsDialog.GetInstance().setMsg("patientState == null");
			TipsDialog.GetInstance().show();
			return;
		}
		orderConfirmActivity.getPatientStateTV().setText(patientState.getName());

		//金额
		updatePrice();

		return;

	}

	//07. 同步价格到UI
	public void updatePrice()
	{
		OrderConfirmActivity orderConfirmActivity = (OrderConfirmActivity)m_context;

		//金额
		int allNum       = m_dRepeatOrderFlow.getAllNum();
		int chargePerAll = m_dRepeatOrderFlow.getChargePerAll();
		if (allNum != 0)
		{
			String strAllNum = String.valueOf(allNum);
			orderConfirmActivity.getAllNumTV().setText(strAllNum + UNIT_DAY);
			orderConfirmActivity.getChargePerAllTV().setText(String.valueOf(chargePerAll) + UNIT_YUAN);
			orderConfirmActivity.getAllCoeffTV().setText(strAllNum);
			orderConfirmActivity.getAllRegionLL().setVisibility(View.VISIBLE);
		}
		else
		{
			orderConfirmActivity.getAllRegionLL().setVisibility(View.GONE);
		}

		int dayNum       = m_dRepeatOrderFlow.getDayNum();
		int chargePerDay = m_dRepeatOrderFlow.getChargePerDay();
		if (dayNum != 0)
		{
			String strDayNum = String.valueOf(dayNum);
			orderConfirmActivity.getDayNumTV().setText(strDayNum + UNIT_DAY);
			orderConfirmActivity.getChargePerDayTV().setText(String.valueOf(chargePerDay) + UNIT_YUAN);
			orderConfirmActivity.getDayCoeffTV().setText(strDayNum);
			orderConfirmActivity.getDayRegionLL().setVisibility(View.VISIBLE);
		}
		else
		{
			orderConfirmActivity.getDayRegionLL().setVisibility(View.GONE);
		}


		int nightNum       = m_dRepeatOrderFlow.getNightNum();
		int chargePerNight = m_dRepeatOrderFlow.getChargePerNight();
		if (nightNum != 0)
		{
			String strNightNum = String.valueOf(nightNum);
			orderConfirmActivity.getNightNumTV().setText(strNightNum + UNIT_DAY);
			orderConfirmActivity.getChargePerNightTV().setText(String.valueOf(chargePerNight) + UNIT_YUAN);
			orderConfirmActivity.getNightCoeffTV().setText(strNightNum);
			orderConfirmActivity.getNightRegionLL().setVisibility(View.VISIBLE);
		}
		else
		{
			orderConfirmActivity.getNightRegionLL().setVisibility(View.GONE);
		}

		int totalCharge = m_dRepeatOrderFlow.getTotalChargeDisplay();
		orderConfirmActivity.getTotalChargeTV().setText(String.valueOf(totalCharge));

		return;
	}

	//08. 设置被护理人的状态
	public void setPatientState(EnumConfig.PatientState patientState)
	{
		if (patientState == null)
		{
			TipsDialog.GetInstance().setMsg("patientState == null", m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		//01. 同步数据到DAppiontmentNursing
		m_dRepeatOrderFlow.setPatientState(patientState);

		//02. 更新UI显示
		OrderConfirmActivity orderConfirmActivity = (OrderConfirmActivity)m_context;
		orderConfirmActivity.getPatientStateTV().setText(patientState.getName());

		//03. 更新价格
		updatePrice();

		return;
	}


}
