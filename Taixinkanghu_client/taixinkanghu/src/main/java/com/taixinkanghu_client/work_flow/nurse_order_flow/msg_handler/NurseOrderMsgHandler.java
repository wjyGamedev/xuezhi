/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.nurse_order_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.nurse_order_flow.msg_handler;

import android.content.Intent;

import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurseOrder;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.AnswerNurseOrderListEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.NurseOrderListHandler;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestNurseOrderCancelInServiceEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestNurseOrderCancelInWaitPayEvent;
import com.taixinkanghu_client.data_module.register_account.data.DAccount;
import com.taixinkanghu_client.net.config.NurseBasicListConfig;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_list_page.ui.SelectNurseActivity;
import com.taixinkanghu_client.work_flow.comment_nurse_order_flow.ui.CommentNurseOrderActivity;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;
import com.taixinkanghu_client.work_flow.nurse_order_flow.BaseNurseOrderFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.nurse_order_flow.ui.NurseOrderActivity;
import com.taixinkanghu_client.work_flow.nurse_order_pay_in_wait_pay_flow.ui.NurseOrderPayInWaitPayActivity;
import com.taixinkanghu_client.work_flow.nurse_order_pay_more_flow.nurse_order_pay_more_page.ui.NurseOrderPayMoreActivity;
import com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.ui.RepeatOrderApoitNursingActivity;

import java.util.Calendar;

public class NurseOrderMsgHandler extends BaseNurseOrderFlowUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public NurseOrderMsgHandler(NurseOrderActivity activity)
	{
		super(activity);
	}

	//01. 跳转到订单详细信息页面
	public void go2NurseInfoAction(int nurseOrderID)
	{
		//跳转到详细订单页面。
		//		Intent intent = new Intent(nurseOrderActivity, NurseInfoActivity.class);
		//		nurseOrderActivity.startActivity(intent);
		return;

	}

	//02. 跳转到主页面
	public void go2MainAction()
	{
		m_dNurseOrderFlow.clearupAll();

		NurseOrderActivity activity = (NurseOrderActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}

	//03. 接收nurse order list的返回消息
	public void onEventMainThread(AnswerNurseOrderListEvent event)
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;

		//01. 关闭等待对话框
		activity.stopWaitDialog();

		//02. update UI
		activity.updateContent();
		return;
	}

	//04. get/set selected nurse id
	public int getSelectedNurseOrderID()
	{
		return m_dNurseOrderFlow.getSelectedNurseOrderID();
	}

	public void setSelectedNurseOrderID(int selectedNurseOrderID)
	{
		m_dNurseOrderFlow.setSelectedNurseOrderID(selectedNurseOrderID);
		return;
	}

	//05. nurse order的业务流程
	//0501. 取消订单在待支付的情况下
	public void cancelOrderInWaitPayAction()
	{
		//弹出提示框
		NurseOrderActivity activity = (NurseOrderActivity)m_context;
		activity.popDialog_CancelOrderInWaitPay();
		return;
	}

	//0502. 在待支付情况下，跳转到支付页面
	public void readyPayOrderInWaitPayAction()
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;
		activity.startActivity(new Intent(activity, NurseOrderPayInWaitPayActivity.class));
		return;
	}

	//0503. 在订单处于完成的状态下，进行评论。
	public void commentAction()
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;
		activity.startActivity(new Intent(activity, CommentNurseOrderActivity.class));
		return;
	}

	//0504. 续订
	public void repeatOrderAction()
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;

		//01. 关闭当前页面
		activity.finish();

		//02. 跳转到续订的预约陪护页面
		Intent intent = new Intent(activity, RepeatOrderApoitNursingActivity.class);
		int    selectedNurseOrderID = m_dNurseOrderFlow.getSelectedNurseOrderID();
		intent.putExtra(NurseBasicListConfig.ORDER_ID, selectedNurseOrderID);
		activity.startActivity(intent);

		//03. 清楚数据
		m_dNurseOrderFlow.clearupAll();

		return;

	}

	//0505. 更换护理员
	public void changeNurseAction()
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;

		//01. 检查有效性
		//今天必须在要更换护理员的服务时间之内
		DNurseOrder nurseOrder = m_dNurseOrderFlow.getDNurseOrder();
		if (nurseOrder == null)
		{
			activity.popErrorDialog("nurseOrder == null");
			return;
		}

		Calendar selectedBeginCalendar = Calendar.getInstance();
		selectedBeginCalendar.setTime(nurseOrder.getBeginDate());
		int beginYear = selectedBeginCalendar.get(Calendar.YEAR);
		int beginMonth = selectedBeginCalendar.get(Calendar.MONTH);
		int beginDay = selectedBeginCalendar.get(Calendar.DAY_OF_MONTH);

		Calendar selectedEndCalendar = Calendar.getInstance();
		selectedEndCalendar.setTime(nurseOrder.getEndDate());
		int endYear = selectedEndCalendar.get(Calendar.YEAR);
		int endMonth = selectedEndCalendar.get(Calendar.MONTH);
		int endDay = selectedEndCalendar.get(Calendar.DAY_OF_MONTH);

		Calendar todayCalendar = Calendar.getInstance();
		int todayYear = todayCalendar.get(Calendar.YEAR);
		int todayMonth = todayCalendar.get(Calendar.MONTH);
		int todayDay = todayCalendar.get(Calendar.DAY_OF_MONTH);

		//今天要大于等于开始日期
		if (todayYear < beginYear	||
				(todayYear == beginYear && todayMonth < beginMonth)	||
				(todayYear == beginYear && todayMonth == beginMonth && todayDay < beginDay)
				)
		{
			activity.popErrorDialog(activity.getString(R.string.tips_selected_nurse_not_in_service));
			return;
		}

		//今天要小于等于结束日期
		if (todayYear > endYear	||
				(todayYear == endYear && todayMonth > endMonth)	||
				(todayYear == endYear && todayMonth == endMonth && todayDay > endDay)
				)
		{
			activity.popErrorDialog(activity.getString(R.string.tips_selected_nurse_not_in_service));
			return;
		}


		//01. 关闭当前页面
		activity.finish();

		//02. 跳转到选择护理员的页面
		Intent intent = new Intent(activity, SelectNurseActivity.class);
		int orderID = m_dNurseOrderFlow.getSelectedNurseOrderID();
		intent.putExtra(NurseBasicListConfig.ORDER_ID, orderID);
		activity.startActivity(intent);

		//03. 清楚数据
		m_dNurseOrderFlow.clearupAll();

		return;
	}

	//0506. 在服务状态下，取消订单
	public void cancelOrderInServiceAction()
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;
		activity.popDialog_CancelOrderInService();
		return;
	}

	//0507. 补差价
	public void payMoreAction()
	{
		NurseOrderActivity activity = (NurseOrderActivity)m_context;
		activity.startActivity(new Intent(activity, NurseOrderPayMoreActivity.class));
		return;
	}

	//06. 发送 nurse order cancel in wait_pay
	public void requestNurseOrderCancelInWaitPayAction()
	{
		RequestNurseOrderCancelInWaitPayEvent event = m_dNurseOrderFlow.constructRequestNurseOrderCancelInWaitPayEvent();
		NurseOrderListHandler.GetInstance().requestNurseOrderCancelInWaitPayAction(event);
		return;
	}

	//07. 发送 nurse order cancel in service
	public void requestNurseOrderCancelInService()
	{
		RequestNurseOrderCancelInServiceEvent event = new RequestNurseOrderCancelInServiceEvent();
		event.setUserID(DAccount.GetInstance().getId());
		int nurseOrderID = m_dNurseOrderFlow.getSelectedNurseOrderID();
		event.setOrderID(String.valueOf(nurseOrderID));
		NurseOrderListHandler.GetInstance().requestNurseOrderCancelInService(event);
		return;

	}
}
