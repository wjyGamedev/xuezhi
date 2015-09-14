/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/8		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_order_list.msg_handler;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequestForm;
import com.module.net.NetConfig;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.AnswerNurseSeniorListHandler;
import com.taixinkanghu_client.data_module.register_account.data.DAccount;
import com.taixinkanghu_client.net.BaseMsgHandler;

import java.util.HashMap;

public class NurseOrderListHandler extends BaseMsgHandler
{
	private static NurseOrderListHandler s_nurseOrderListHandler = new NurseOrderListHandler();

	private AnswerNurseOrderListHandler                 m_answerNurseOrderListHandler                 = new AnswerNurseOrderListHandler();
	private AnswerNurseOrderConfirmInNormalHandler      m_answerNurseOrderConfirmInNormalHandler      = new
			AnswerNurseOrderConfirmInNormalHandler();
	private AnswerNurseOrderConfirmInChangeNurseHandler m_answerNurseOrderConfirmInChangeNurseHandler = new
			AnswerNurseOrderConfirmInChangeNurseHandler();

	private AnswerNurseOrderCheckHandler m_answerNurseOrderCheckHandler = new AnswerNurseOrderCheckHandler();

	private AnswerNurseOrderCancelInWaitPayHandler m_answerNurseOrderCancelInWaitPayHandler = new AnswerNurseOrderCancelInWaitPayHandler();

	private AnswerNurseOrderCancelInServiceHandler m_answerNurseOrderCancelInServiceHandler = new AnswerNurseOrderCancelInServiceHandler();

	private AnswerNurseOrderPayMoreHandler m_answerNurseOrderPayMoreHandler = new AnswerNurseOrderPayMoreHandler();

	//添加评论后的返回处理函数
	private AnswerNurseSeniorListHandler m_answerNurseSeniorListHandler = new AnswerNurseSeniorListHandler();

	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	private NurseOrderListHandler()
	{
		super();
	}

	public static NurseOrderListHandler GetInstance()
	{
		return s_nurseOrderListHandler;
	}

	//01. nurse order confirm in normal
	//0101. 订单确认，在预约陪护的情况下(正常情况)
	public void requestNurseOrderConfirmInNormalAction(RequestNurseOrderConfirmInNormalEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//0102. 发送nurse order confirm in normal消息
	public void onEventAsync(RequestNurseOrderConfirmInNormalEvent event)
	{
		HashMap<String, String> nurseOrderConfirmMap = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.s_nurseOrderConfirmAddress,
																nurseOrderConfirmMap,
																m_answerNurseOrderConfirmInNormalHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	//0103. 发送nurse order confirm in normal接收成功的event, 需要在相关activity的msg中，对于该event在主thread上进行处理。
	public void answerNurseOrderConfirmInNormalAction(AnswerNurseOrderConfirmInNormalEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//02. nurse order confirm in change nurse
	//0201. 订单确认，在更换护理员的情况下
	public void requestNurseOrderConfirmInChangeNurseAction(RequestNurseOrderConfirmInChangeNurse event)
	{
		m_eventBus.post(event);
		return;
	}

	//0202. 发送nurse order confirm in change nurse消息
	public void onEventAsync(RequestNurseOrderConfirmInChangeNurse event)
	{
		HashMap<String, String> nurseOrderConfirmMap = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.s_changeNurseAddress,
																nurseOrderConfirmMap,
																m_answerNurseOrderConfirmInChangeNurseHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	//0203. 发送nurse order confirm in change nurse接收成功的event, 需要在相关activity的msg中，对于该event在主thread上进行处理。
	public void answerNurseOrderConfirmInChangeNurseAction(AnswerNurseOrderConfirmInChangeNurseEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//03. nurse order check
	//0301. 发送nurse order check请求event
	public void requestNurseOrderCheckAction(RequestNurseOrderCheckEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//0302. 发送nurse order check消息
	public void onEventAsync(RequestNurseOrderCheckEvent event)
	{
		HashMap<String, String> nurseOrderCheck = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.s_nurseOrderCheckAddress,
																nurseOrderCheck,
																m_answerNurseOrderCheckHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	//0303. 发送nurse order check接收的event, 需要在相关activity的msg中，对于该event在主thread上进行处理。
	//注意：这里有两种状态有待检测。成功/占用中。
	public void answerNurseOrderCheckAction(AnswerNurseOrderCheckEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//04. alipay
	//0401. 发送nurse order alipay event
	public void requestNurseOrderAlipayAction(RequestNurseOrderAlipayEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//0402. 发送nurse order alipay check消息
	public void onEventAsync(RequestNurseOrderAlipayEvent event)
	{
		String payInfo = event.getPayInfo();
		Activity activity = event.getActivity();
		//01. 构造PayTask 对象
		PayTask alipay = new PayTask(activity);

		//02. 调用支付接口，获取支付结果
		String result = alipay.pay(payInfo);

		//03. 将支付结果发送到原来的页面中。
		AnswerNurseOrderAlipayEvent answerNurseOrderAlipayEvent = new AnswerNurseOrderAlipayEvent();
		answerNurseOrderAlipayEvent.setResult(result);
		m_eventBus.post(answerNurseOrderAlipayEvent);
		return;
	}

	//05. nurse order list
	//0501. 发送nurse order list event
	public void requestNurseOrderListAction()
	{
		RequestNurseOrderListEvent reqNurseOrderListEvent = new RequestNurseOrderListEvent();
		String                     userID                 = DAccount.GetInstance().getId();
		reqNurseOrderListEvent.setUserID(userID);
		m_eventBus.post(reqNurseOrderListEvent);
		return;
	}

	//0502. 发送nurse order list消息
	public void onEventAsync(RequestNurseOrderListEvent event)
	{
		HashMap<String, String> nurseOrderList = event.getHashMap();


		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST, NetConfig.s_nurseOrderListAddress, nurseOrderList,
																m_answerNurseOrderListHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	//0503. 发送nurse order list接收成功的event, 需要在相关activity的msg中，对于该event在主thread上进行处理。
	public void answerNurseOrderListAction()
	{
		AnswerNurseOrderListEvent finishedHospitalListEvent = new AnswerNurseOrderListEvent();
		m_eventBus.post(finishedHospitalListEvent);
		return;
	}

	//06. nurse order cancel in waitpay
	//0601. 发送nurse order cancel in waitpay event
	public void requestNurseOrderCancelInWaitPayAction(RequestNurseOrderCancelInWaitPayEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//0602. 发送nurse order cancel in waitpay消息
	public void onEventAsync(RequestNurseOrderCancelInWaitPayEvent event)
	{
		HashMap<String, String> nurseOrderCancel = event.getHashMap();


		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.s_nurseOrderCancel,
																nurseOrderCancel,
																m_answerNurseOrderCancelInWaitPayHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	//0603. 发送nurse order list接收成功的event, update nurse order list
	public void answerNurseOrderCancelInWaitPayAction()
	{
		RequestNurseOrderListEvent event  = new RequestNurseOrderListEvent();
		String                 userID = DAccount.GetInstance().getId();
		event.setUserID(userID);
		m_eventBus.post(event);
		return;
	}

	//07. nurse order cancel in service
	public void requestNurseOrderCancelInService(RequestNurseOrderCancelInServiceEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestNurseOrderCancelInServiceEvent event)
	{
		HashMap<String, String> nurseOrderCancel = event.getHashMap();


		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.s_nurseOrderCancelService,
																nurseOrderCancel,
																m_answerNurseOrderCancelInServiceHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerNurseOrderCancelInServiceAction()
	{
		RequestNurseOrderListEvent event  = new RequestNurseOrderListEvent();
		String                 userID = DAccount.GetInstance().getId();
		event.setUserID(userID);
		m_eventBus.post(event);
		return;
	}

	//08. 补差价, 计算时间是否OK
	public void requestNurseOrderPayMoreAction(RequestNurseOrderPayMoreEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestNurseOrderPayMoreEvent event)
	{
		HashMap<String, String> nurseOrderCancel = event.getHashMap();


		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.s_nurseOrderPayMoreAddress,
																nurseOrderCancel,
																m_answerNurseOrderPayMoreHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerNurseOrderPayMoreAction(AnswerNurseOrderPayMoreEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//09. 请求发送comment nurse order
	//因为是在nurse senior list中，所以监听AnswerNurseSeniorListEvent
	public void requestCommentNurseOrderAction(RequestCommentNurseOrderEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestCommentNurseOrderEvent event)
	{
		HashMap<String, String> dataHashMap = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.s_commentNurseOrder,
																dataHashMap,
																m_answerNurseSeniorListHandler, m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}


}
