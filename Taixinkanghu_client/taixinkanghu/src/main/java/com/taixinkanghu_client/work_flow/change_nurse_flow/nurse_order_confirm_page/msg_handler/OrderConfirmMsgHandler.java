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

package com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_order_confirm_page.msg_handler;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;

import com.module.data.DGlobal;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DateConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurseOrder;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.AnswerNurseOrderConfirmInChangeNurseEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.NurseOrderListHandler;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestNurseOrderConfirmInChangeNurseEvent;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data.DNursingDate;
import com.taixinkanghu_client.work_flow.change_nurse_flow.BaseChangeNurseFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_order_confirm_page.ui.OrderConfirmActivity;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_order_confirm_page.ui.SelectDateFragment;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_order_pay_page.ui.NurseOrderPayInChangeNurseActivity;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderConfirmMsgHandler extends BaseChangeNurseFlowUIMsgHandler
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


	//01. 同步数据到UI
	public void updateNewNurseContent()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;

		//01. 订单内容
		int headerImgResID = m_dChangeNurseFlow.getNewNurseHeaderImgResID();
		activity.getNurseHeadImgIV().setImageResource(headerImgResID);

		String name = m_dChangeNurseFlow.getNewNurseName();
		activity.getNameTV().setText(name);

		String jobNum = m_dChangeNurseFlow.getNewNurseJobNum();
		activity.getJobNumTV().setText(jobNum);

		String nursingLevel = m_dChangeNurseFlow.getNewNursingLevel();
		activity.getNuringLevelTV().setText(nursingLevel);

		String serviceDate = m_dChangeNurseFlow.getNewServiceDateDisplay();
		activity.getServiceDateTV().setText(serviceDate);

		String serviceAddress = m_dChangeNurseFlow.getServiceAddress();
		activity.getServiceAddressTV().setText(serviceAddress);

		//被护理人
		String patientName = m_dChangeNurseFlow.getPatientName();
		activity.getPatientNameTV().setText(patientName);
		//被护理人状态
		EnumConfig.PatientState patientState = m_dChangeNurseFlow.getPatientState();
		if (patientState == null)
		{
			TipsDialog.GetInstance().setMsg("patientState == null");
			TipsDialog.GetInstance().show();
			return;
		}
		activity.getPatientStateTV().setText(patientState.getName());

		//02. 价格
		updateNewPrice();

		return;

	}

	public void updateOldNurseContent()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;

		//01. 订单信息
		int headerImgResID = m_dChangeNurseFlow.getOldNurseHeaderImgResID();
		activity.getNurseHeadImgIV().setImageResource(headerImgResID);

		String name = m_dChangeNurseFlow.getOldNurseName();
		activity.getNameTV().setText(name);

		String jobNum = m_dChangeNurseFlow.getOldNurseJobNum();
		activity.getJobNumTV().setText(jobNum);

		String nursingLevel = m_dChangeNurseFlow.getOldNursingLevel();
		activity.getNuringLevelTV().setText(nursingLevel);

		String serviceDate = m_dChangeNurseFlow.getOldServiceDateDisplay();
		activity.getServiceDateTV().setText(serviceDate);

		String serviceAddress = m_dChangeNurseFlow.getServiceAddress();
		activity.getServiceAddressTV().setText(serviceAddress);

		//被护理人
		String patientName = m_dChangeNurseFlow.getPatientName();
		activity.getPatientNameTV().setText(patientName);
		//被护理人状态
		EnumConfig.PatientState patientState = m_dChangeNurseFlow.getPatientState();
		if (patientState == null)
		{
			TipsDialog.GetInstance().setMsg("patientState == null");
			TipsDialog.GetInstance().show();
			return;
		}
		activity.getPatientStateTV().setText(patientState.getName());

		//02. 金额信息
		int allNum       = m_dChangeNurseFlow.getOldAllNum();
		int chargePerAll = m_dChangeNurseFlow.getOldChargePerAll();
		if (allNum != 0)
		{
			String strAllNum = String.valueOf(allNum);
			activity.getAllNumTV().setText(strAllNum + UNIT_DAY);
			activity.getChargePerAllTV().setText(String.valueOf(chargePerAll) + UNIT_YUAN);
			activity.getAllCoeffTV().setText(strAllNum);
			activity.getAllRegionLL().setVisibility(View.VISIBLE);
		}
		else
		{
			activity.getAllRegionLL().setVisibility(View.GONE);
		}

		int dayNum       = m_dChangeNurseFlow.getOldDayNum();
		int chargePerDay = m_dChangeNurseFlow.getOldChargePerDay();
		if (dayNum != 0)
		{
			String strDayNum = String.valueOf(dayNum);
			activity.getDayNumTV().setText(strDayNum + UNIT_DAY);
			activity.getChargePerDayTV().setText(String.valueOf(chargePerDay) + UNIT_YUAN);
			activity.getDayCoeffTV().setText(strDayNum);
			activity.getDayRegionLL().setVisibility(View.VISIBLE);
		}
		else
		{
			activity.getDayRegionLL().setVisibility(View.GONE);
		}


		int nightNum       = m_dChangeNurseFlow.getOldNightNum();
		int chargePerNight = m_dChangeNurseFlow.getOldChargePerNight();
		if (nightNum != 0)
		{
			String strNightNum = String.valueOf(nightNum);
			activity.getNightNumTV().setText(strNightNum + UNIT_DAY);
			activity.getChargePerNightTV().setText(String.valueOf(chargePerNight) + UNIT_YUAN);
			activity.getNightCoeffTV().setText(strNightNum);
			activity.getNightRegionLL().setVisibility(View.VISIBLE);
		}
		else
		{
			activity.getNightRegionLL().setVisibility(View.GONE);
		}

		int totalCharge = m_dChangeNurseFlow.getOldTotalChargeDisplay();
		activity.getTotalChargeTV().setText(String.valueOf(totalCharge));

		return;

	}

	public void updateContent()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;

		if (activity.isNewNurseSelected())
		{
			updateNewNurseContent();
		}
		else
		{
			updateOldNurseContent();
		}

		return;
	}

	//02. 同步价格到UI
	public void updateNewPrice()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;

		int allNum       = m_dChangeNurseFlow.getNewAllNum();
		int chargePerAll = m_dChangeNurseFlow.getNewChargePerAll();
		if (allNum != 0)
		{
			String strAllNum = String.valueOf(allNum);
			activity.getAllNumTV().setText(strAllNum + UNIT_DAY);
			activity.getChargePerAllTV().setText(String.valueOf(chargePerAll) + UNIT_YUAN);
			activity.getAllCoeffTV().setText(strAllNum);
			activity.getAllRegionLL().setVisibility(View.VISIBLE);
		}
		else
		{
			activity.getAllRegionLL().setVisibility(View.GONE);
		}

		int dayNum       = m_dChangeNurseFlow.getNewDayNum();
		int chargePerDay = m_dChangeNurseFlow.getNewChargePerDay();
		if (dayNum != 0)
		{
			String strDayNum = String.valueOf(dayNum);
			activity.getDayNumTV().setText(strDayNum + UNIT_DAY);
			activity.getChargePerDayTV().setText(String.valueOf(chargePerDay) + UNIT_YUAN);
			activity.getDayCoeffTV().setText(strDayNum);
			activity.getDayRegionLL().setVisibility(View.VISIBLE);
		}
		else
		{
			activity.getDayRegionLL().setVisibility(View.GONE);
		}


		int nightNum       = m_dChangeNurseFlow.getNewNightNum();
		int chargePerNight = m_dChangeNurseFlow.getNewChargePerNight();
		if (nightNum != 0)
		{
			String strNightNum = String.valueOf(nightNum);
			activity.getNightNumTV().setText(strNightNum + UNIT_DAY);
			activity.getChargePerNightTV().setText(String.valueOf(chargePerNight) + UNIT_YUAN);
			activity.getNightCoeffTV().setText(strNightNum);
			activity.getNightRegionLL().setVisibility(View.VISIBLE);
		}
		else
		{
			activity.getNightRegionLL().setVisibility(View.GONE);
		}

		//同步价格，为了判断
		int newTotalCharge =  m_dChangeNurseFlow.getNewTotalChargeDisplay();
		int deltaPrice     = m_dChangeNurseFlow.getNewNeedPayTotalChargeDisplay();
		m_dChangeNurseFlow.setClientTotalPrice(deltaPrice);

		String priceDesc  = null;
		if (deltaPrice >= 0)
		{
			priceDesc = String.valueOf(newTotalCharge) + "(+" + deltaPrice + ")";
		}
		else
		{
			deltaPrice = Math.abs(deltaPrice);
			priceDesc = String.valueOf(newTotalCharge) + "(-" + deltaPrice + ")";
		}
		activity.getTotalChargeTV().setText(priceDesc);
		return;
	}

	//03. 点击新护工按钮
	public void switchNewNurseDisplay()
	{
		//01. 设置状态
		OrderConfirmActivity orderConfirmActivity = (OrderConfirmActivity)m_context;
		orderConfirmActivity.getNewNurseRBtn().setChecked(true);

		//02. updateContent
		updateNewNurseContent();

		return;
	}

	//04. 点击老护工按钮
	public void switchOldNurseDisplay()
	{
		//01. 设置状态
		OrderConfirmActivity orderConfirmActivity = (OrderConfirmActivity)m_context;
		orderConfirmActivity.getOldNurseRBtn().setChecked(true);

		//02. updateContent
		updateOldNurseContent();

		return;

	}

	//05. 选择日期
	public void selectBeginDateAction()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;

		SelectDateFragment selectDateFragment = new SelectDateFragment();
		FragmentTransaction transaction        = activity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.order_confirm_page, selectDateFragment, selectDateFragment.getClass().getName());
		transaction.addToBackStack(null);
		transaction.commit();

		return;
	}

	//06. 获取可选择的日期范围
	//从明天开始到结束
	public ArrayList<Calendar> getSelectedDateRegion()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;

		ArrayList<Calendar> calendarArrayList = new ArrayList<>();

		//01. 原来的开始/结束日期
		DNursingDate nursingDate = m_dChangeNurseFlow.getOldNursingDate();
		if (nursingDate == null	)
		{
			TipsDialog.GetInstance().setMsg("nursingDate == null", activity);
			TipsDialog.GetInstance().show();
			return calendarArrayList;
		}

		Calendar oldBeginDate = Calendar.getInstance();
		oldBeginDate.setTime(nursingDate.getBeginDate());
		int oldBeginYear = oldBeginDate.get(Calendar.YEAR);
		int oldBeginMonth = oldBeginDate.get(Calendar.MONTH);
		int oldBeginDay = oldBeginDate.get(Calendar.DAY_OF_MONTH);

		Calendar oldEndDate = Calendar.getInstance();
		oldEndDate.setTime(nursingDate.getEndDate());
		int oldEndYear = oldEndDate.get(Calendar.YEAR);
		int oldEndMonth = oldEndDate.get(Calendar.MONTH);
		int oldEndDay = oldEndDate.get(Calendar.DAY_OF_MONTH);


		Calendar todayCalendar = Calendar.getInstance();
		int todayYear = todayCalendar.get(Calendar.YEAR);
		int todayMonth = todayCalendar.get(Calendar.MONTH);
		int todayDay = todayCalendar.get(Calendar.DAY_OF_MONTH);
		int maxDay = todayCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int maxMonth = todayCalendar.getActualMaximum(Calendar.MONTH);

		//今天要大于等于开始日期
		if (todayYear < oldBeginYear	||
				(todayYear == oldBeginYear && todayMonth < oldBeginMonth)	||
				(todayYear == oldBeginYear && todayMonth == oldBeginMonth && todayDay < oldBeginDay)
		)
		{
			return calendarArrayList;
		}

		//今天要小于等于结束日期
		if (todayYear > oldEndYear	||
				(todayYear == oldEndYear && todayMonth > oldEndMonth)	||
				(todayYear == oldEndYear && todayMonth == oldEndMonth && todayDay > oldEndDay)
				)
		{
			return calendarArrayList;
		}

		//TODO:当前的规则：在换护工的日期选择中，仅允许从明天开始选择。
		int beginNursingDay = todayDay + 1;
		int beginNursingMonth = todayMonth;
		int beginNursingYear = todayYear;
		if (beginNursingDay > maxDay)
		{
			beginNursingDay = 1;
			beginNursingMonth = todayMonth + 1;
		}
		if (beginNursingMonth > maxMonth)
		{
			beginNursingMonth = 0;
			beginNursingYear = todayYear + 1;
		}

		Calendar endNursingCalendar = Calendar.getInstance();
		endNursingCalendar.setTime(nursingDate.getEndDate());

		int indexDay = 0;
		int endDay = 0;
		for (int indexYear = beginNursingYear; indexYear <= endNursingCalendar.get(Calendar.YEAR); indexYear++)
		{
			for (int indexMonth = beginNursingMonth; indexMonth <= endNursingCalendar.get(Calendar.MONTH); indexMonth++)
			{
				if (indexMonth == beginNursingMonth)
				{
					indexDay = beginNursingDay;
				}
				else
				{
					indexDay = 1;
				}
				if (indexMonth == endNursingCalendar.get(Calendar.MONTH))
				{
					endDay = endNursingCalendar.get(Calendar.DAY_OF_MONTH);
				}
				else
				{
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.MONTH, indexMonth);
					endDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				}

				for (; indexDay <= endDay; indexDay++)
				{
					Calendar tmpCalendar = Calendar.getInstance();
					tmpCalendar.set(indexYear, indexMonth, indexDay);
					calendarArrayList.add(tmpCalendar);
				}

			}
		}
		return calendarArrayList;

	}

	//07. 跳转到主页面
	public void go2MainPage()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));

		m_dChangeNurseFlow.clearupAll();

		return;
	}

	//08. 退出选择日期操作
	public void cancelSelectDateAction()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;
		FragmentManager fgManager = activity.getFragmentManager();
		Fragment        fragment = fgManager.findFragmentByTag(SelectDateFragment.class.getName());
		FragmentTransaction fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();

		return;

	}

	//09. 设置选择日期
	public void selectNursingDate(DNursingDate nursingDate)
	{
		//01. 同步数据
		m_dChangeNurseFlow.setNewSelectDate(nursingDate);

		//02. update price
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;
		if (activity.isNewNurseSelected())
		{
			updateNewPrice();
		}

		//03. update ui
		activity.getSelectBeginDateTV().setText(nursingDate.getDateDescription());

		return;
	}

	//10. 请求发送nurse order confirm action
//	public void requestNurseOrderConfirmAction()
//	{
//		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;
//		//01. 不需要支付差价
//		if (m_dChangeNurseFlow.getClientTotalPrice() == 0)
//		{
//			activity.popErrorDialog(activity.getString(R.string.tips_delta_price_0));
//			return;
//		}
//		//02. 需要支付差价
//		else if (m_dChangeNurseFlow.getClientTotalPrice() < 0)
//		{
//
//		}
//
//	}

	//11. 发送nurse order confirm event
	public void requestNurseOrderConfirmAction()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;

		RequestNurseOrderConfirmInChangeNurseEvent event = new RequestNurseOrderConfirmInChangeNurseEvent();
		int newNurseID = m_dChangeNurseFlow.getNewNurseID();
		event.setNurseID(String.valueOf(newNurseID));

		DNurseOrder nurseOrder = m_dChangeNurseFlow.getOldNurserOrder();
		if (nurseOrder == null)
		{
			activity.popErrorDialog("nurseOrder == null");
			return;
		}
		int orderID = nurseOrder.getOrderID();
		event.setOrderID(String.valueOf(orderID));

		DNursingDate selectDate = m_dChangeNurseFlow.getNewSelectDate();
		if (selectDate == null)
		{
			activity.popErrorDialog("selectDate == null");
			return;
		}

		Date beginDate = selectDate.getBeginDate();
		SimpleDateFormat m_simpleDateFormat = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);
		event.setBeginDate(m_simpleDateFormat.format(beginDate));

		NurseOrderListHandler.GetInstance().requestNurseOrderConfirmInChangeNurseAction(event);
		return;
	}

	//05. nurse order confirm 返回消息处理：下单成功，跳转到支付页面
	public void onEventMainThread(AnswerNurseOrderConfirmInChangeNurseEvent event)
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;

		//01. 将返回的关键数据同步到DAppiontmentNursing
		m_dChangeNurseFlow.setNewOrderID(event.getOrderID());
		m_dChangeNurseFlow.setNewOrderSerialNum(event.getOrderSerialNum());
		m_dChangeNurseFlow.setTotalPrice(event.getTotalPrice());

		//02. 差价
		int clientDeltaPrice = m_dChangeNurseFlow.getClientTotalPrice();
		int serviceDeltaPrice = event.getTotalPrice();

		if (clientDeltaPrice != serviceDeltaPrice)
		{
			activity.popErrorDialog("clientDeltaPrice != serviceDeltaPrice[clientDeltaPrice:="+clientDeltaPrice+"][serviceDeltaPrice:="+serviceDeltaPrice+"]");
			return;
		}

		if (serviceDeltaPrice == 0)
		{
			activity.popErrorDialog(activity.getString(R.string.tips_delta_price_0));
			return;
		}
		//02. 不需要支付差价
		else if (serviceDeltaPrice < 0)
		{
			activity.popErrorDialog(activity.getString(R.string.tips_delta_price_back));
			return;
		}
		//03. 跳转到支付页面
		else
		{
			Intent intent = new Intent(activity, NurseOrderPayInChangeNurseActivity.class);
			activity.startActivity(intent);
		}

		return;
	}

	//回退
	public void backAction()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)m_context;
		activity.finish();

		m_dChangeNurseFlow.clearupNurseConfirm();

		return;

	}

}
