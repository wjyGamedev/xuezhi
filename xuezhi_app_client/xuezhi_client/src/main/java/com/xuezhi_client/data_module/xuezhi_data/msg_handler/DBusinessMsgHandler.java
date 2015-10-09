/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.department_list.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/7		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonObjectRequestForm;
import com.module.net.BaseMsgHandler;
import com.xuezhi_client.net.config.NetConfig;

import java.util.HashMap;

public class DBusinessMsgHandler extends BaseMsgHandler
{
	private static DBusinessMsgHandler s_DBusinessMsgHandler = new DBusinessMsgHandler();

	private AnswerMedicalUnitListHandler  m_answerMedicalUnitListHandler  = new AnswerMedicalUnitListHandler();
	private AnswerMedicalListHandler      m_answerMedicalListHandler      = new AnswerMedicalListHandler();
	private AnswerMedicalStockListHandler m_answerMedicalStockListHandler = new AnswerMedicalStockListHandler();
	private AnswerAddMedicalStockHandler  m_answerAddMedicalStockHandler  = new AnswerAddMedicalStockHandler();

	private AnswerMedicalHistoryListHandler m_answerMedicalHistoryListHandler = new AnswerMedicalHistoryListHandler();
	private AnswerMedicalPromptListHandler  m_answerMedicalPromptListHandler  = new AnswerMedicalPromptListHandler();
	private AnswerAddMedicalPromptHandler   m_answerAddMedicalPromptHandler   = new AnswerAddMedicalPromptHandler();

	private AnswerAddAssayDetectionInfoHandler m_answerAddAssayDetectionInfoHandler = new AnswerAddAssayDetectionInfoHandler();
	private AnswerAssayDetectionListHandler    m_answerAssayDetectionListHandler    = new AnswerAssayDetectionListHandler();

	private AnswerTakeMedicalHandler m_answerTakeMedicalHandler = new AnswerTakeMedicalHandler();

	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	private DBusinessMsgHandler()
	{
		super();
	}

	public static DBusinessMsgHandler GetInstance()
	{
		return s_DBusinessMsgHandler;
	}

	//01. 药品单位
	public void requestMedicalUnitListAction()
	{
		RequestMedicalUnitListEvent event = new RequestMedicalUnitListEvent();
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicalUnitListEvent event)
	{
		JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
														NetConfig.S_NORMAL_MEDICAL_UNIT_LIST_ADDRESS,
														null,
														m_answerMedicalUnitListHandler,
														m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicalUnitListAction()
	{
		AnswerMedicalUnitListHandler answerMedicalListEvent = new AnswerMedicalUnitListHandler();
		m_eventBus.post(answerMedicalListEvent);
		return;
	}

	//01. 药品列表
	public void requestMedicalListAction()
	{
		RequestMedicalListEvent requestMedicalListEvent = new RequestMedicalListEvent();
		m_eventBus.post(requestMedicalListEvent);
		return;
	}

	public void onEventAsync(RequestMedicalListEvent event)
	{
		JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
														NetConfig.S_NORMAL_MEDICAL_LIST_ADDRESS,
														null,
														m_answerMedicalListHandler,
														m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicalListAction()
	{
		AnswerMedicalListEvent answerMedicalListEvent = new AnswerMedicalListEvent();
		m_eventBus.post(answerMedicalListEvent);
		return;
	}

	//02. 用药库存
	//用户库存列表
	public void requestMedicalStockListAction(RequestMedicalStockListEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicalStockListEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICAL_REMAIN_ADDRESS,
																sendData,
																m_answerMedicalStockListHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicalStockListAction()
	{
		AnswerMedicalStockListEvent event = new AnswerMedicalStockListEvent();
		m_eventBus.post(event);
		return;
	}

	//添加药品库存
	public void requestAddMedicalStockAction(RequestAddMedicalStockEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestAddMedicalStockEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_ADD_MEDICAL_STOCK_ADDRESS,
																sendData,
																m_answerAddMedicalStockHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerAddMedicalStockAction(AnswerAddMedicalStockEvent event)
	{
		m_eventBus.post(event);
		return;
	}


	//03. 用药记录
	public void requestMedicalHistoryListAction(RequestMedicalHistoryListEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicalHistoryListEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICAL_HISTORY_ADDRESS,
																sendData,
																m_answerMedicalHistoryListHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicalHistoryListAction()
	{
		AnswerMedicalHistoryListEvent event = new AnswerMedicalHistoryListEvent();
		m_eventBus.post(event);
		return;
	}

	//04. 用药提醒
	//用药提醒
	public void requestMedicalPromptListAction()
	{
		RequestMedicalPromptListEvent event = new RequestMedicalPromptListEvent();
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicalPromptListEvent event)
	{
		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICAL_PROMPT_LIST_ADDRESS,
																null,
																m_answerMedicalPromptListHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicalPromptListAction()
	{
		AnswerMedicalPromptListEvent event = new AnswerMedicalPromptListEvent();
		m_eventBus.post(event);
		return;
	}

	//添加用药提醒
	public void requestAddMedicalPromptAction(RequestAddMedicalPromptEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestAddMedicalPromptEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICAL_ADD_PROMPT_ADDRESS,
																sendData,
																m_answerAddMedicalPromptHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerAddMedicalPromptAction(AnswerAddMedicalPromptEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//05. 添加化验检查信息
	public void requestAddAssayDetectionInfoAction(RequestAddAssayDetectionInfoEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestAddAssayDetectionInfoEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_ADD_ASSAY_DETECTION_INFO_ADDRESS,
																sendData,
																m_answerAddAssayDetectionInfoHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerAddAssayDetectionInfoAction(AnswerAddAssayDetectionInfoEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//06. 化验检查信息列表
	public void requestAssayDetectionListAction(RequestAssayDetectionListEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestAssayDetectionListEvent event)
	{
		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.GET,
																NetConfig.S_NORMAL_ASSAY_DETECTION_LIST_ADDRESS,
																null,
																m_answerAssayDetectionListHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerAssayDetectionListAction(AnswerAssayDetectionListEvent event)
	{
		m_eventBus.post(event);
		return;
	}


	//吃药
	public void requestTakeMedicalAction(RequestTakeMedicalEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestTakeMedicalEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_TAKE_MEDICAL_ADDRESS,
																sendData,
																m_answerTakeMedicalHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerTakeMedicalAction(AnswerTakeMedicalEvent event)
	{
		m_eventBus.post(event);
		return;
	}

}
