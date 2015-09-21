/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.data_module.nurse_list.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/7		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_list.msg_handler;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequestForm;
import com.module.net.NetConfig;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseBasics;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseList;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.AnswerCommentNurseOrderEvent;
import com.taixinkanghu_client.net.BaseMsgHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class NurseListHandler extends BaseMsgHandler
{
	private static NurseListHandler s_nurseListHandler = new NurseListHandler();

	private AnswerNurseBasicListHandler m_answerNurseBasicListHandler = new AnswerNurseBasicListHandler();
	private AnswerNurseSeniorListHandler m_answerNurseSeniorListHandler = new AnswerNurseSeniorListHandler();

	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	private NurseListHandler()
	{
		super();
	}

	public static NurseListHandler GetInstance()
	{
		return s_nurseListHandler;
	}

	//01. 请求nurse basic list
	public void requestNurseBasicListAction(RequestNurseBasicListEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//02. 发送消息，请求nurse basic list
	public void onEventAsync(RequestNurseBasicListEvent event)
	{
		HashMap<String, String> dataHashMap = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.s_appointmentNursingAddress,
																dataHashMap,
																m_answerNurseBasicListHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);

	}

	//03. 发送nurse basic list接收成功的event, 需要在相关activity的msg中，对于该event在主thread上进行处理。
	public void answerNurseBasicListAction()
	{
		AnswerNurseBasicListEvent answerNurseBasicListEvent = new AnswerNurseBasicListEvent();
		m_eventBus.post(answerNurseBasicListEvent);
		return;
	}

	//04. 求情nurse senior list
	public void requestNurseSeniorListAction()
	{
		RequestNurseSeniorListEvent reqNurseSeniorListEvent = new RequestNurseSeniorListEvent();

		ArrayList<DNurseBasics> nurseBasicsArrayList = DNurseList.GetInstance().getNurseBasicList();
		if (nurseBasicsArrayList == null || nurseBasicsArrayList.size() == 0)
		{
			TipsDialog.GetInstance().setMsg("nurseBasicsArrayList == null || nurseBasicsArrayList.size() == 0" + "nurseBasicsArrayList.size()：=" +
													nurseBasicsArrayList.size() + "");
			TipsDialog.GetInstance().show();
			return;
		}

		ArrayList<Integer> nurseIDList = new ArrayList<>();
		for (DNurseBasics nurseBasics : nurseBasicsArrayList)
		{
			if (nurseBasics == null)
				continue;

			nurseIDList.add(nurseBasics.getID());
		}
		reqNurseSeniorListEvent.setNurseIDList(nurseIDList);
		m_eventBus.post(reqNurseSeniorListEvent);


	}

	//05. 发送消息，请求nurse basic list
	public void onEventAsync(RequestNurseSeniorListEvent event)
	{
		HashMap<String, String> dataHashMap = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.s_nurseSeniorListAddress,
																dataHashMap,
																m_answerNurseSeniorListHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);

	}

	//06. 发送 nurse senior list接收成功的event, 需要在相关activity的msg中，对于该event在主thread上进行处理。
	public void answerCommentNurseOrderAction()
	{
		AnswerCommentNurseOrderEvent answerCommentNurseOrderEvent = new AnswerCommentNurseOrderEvent();
		m_eventBus.post(answerCommentNurseOrderEvent);
		return;
	}
	
}
