/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.department_list.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/7		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.hospital_list.msg_handler;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.taixinkanghu_client.net.config.NetConfig;
import com.module.net.BaseMsgHandler;

public class HospitalMsgHandler extends BaseMsgHandler
{
	private static HospitalMsgHandler s_hospitalMsgHandler = new HospitalMsgHandler();

	private AnswerHospitalListHandler m_answerHospitalListHandler = new AnswerHospitalListHandler();

	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	private HospitalMsgHandler()
	{
		super();
	}

	public static HospitalMsgHandler GetInstance()
	{
		return s_hospitalMsgHandler;
	}

	//01. 请求医院列表event
	public void requestHospitalListAction()
	{
		RequestHospitalListEvent requestHospitalListEvent = new RequestHospitalListEvent();
		m_eventBus.post(requestHospitalListEvent);
		return;
	}

	//02. 发送请求医院列表
	public void onEventAsync(RequestHospitalListEvent event)
	{
		JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
														NetConfig.S_NORMAL_HOSPITALLIST_ADDRESS,
														null,
														m_answerHospitalListHandler,
														m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	//03. 发送医院列表接收成功的event, 需要在相关activity的msg中，对于该event在主thread上进行处理。
	public void answerHospitalListAction()
	{
		AnswerHospitalListEvent answerHospitalListEvent = new AnswerHospitalListEvent();
		m_eventBus.post(answerHospitalListEvent);
		return;
	}


}
