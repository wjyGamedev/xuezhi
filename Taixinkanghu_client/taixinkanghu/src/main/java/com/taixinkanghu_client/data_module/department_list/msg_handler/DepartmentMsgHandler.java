/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.data_module.department_list.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/7		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.department_list.msg_handler;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.taixinkanghu_client.net.config.NetConfig;
import com.module.net.BaseMsgHandler;

public class DepartmentMsgHandler extends BaseMsgHandler
{
	private static DepartmentMsgHandler s_departmentMsgHandler = new DepartmentMsgHandler();

	private AnswerDepartmentListHandler m_answerDepartmentListHandler = new AnswerDepartmentListHandler();

	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	private DepartmentMsgHandler()
	{
		super();
	}

	public static DepartmentMsgHandler GetInstance()
	{
		return s_departmentMsgHandler;
	}

	//01. 发送请求科室的事件
	public void requestDepartmentListAction()
	{
		RequestDepartmentListEvent event = new RequestDepartmentListEvent();
		m_eventBus.post(event);
	}

	//02. 发送请求科室的消息
	public void onEventAsync(RequestDepartmentListEvent event)
	{
		JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
														NetConfig.S_NORMAL_DEPARTMENTLIST_ADDRESS,
														null,
														m_answerDepartmentListHandler,
														m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	//03. 发送科室接收成功的event, 需要在相关activity的msg中，对于该event在主thread上进行处理。
	public void answerDepartmentListAction()
	{
		AnswerDepartmentListEvent answerDepartmentListEvent = new AnswerDepartmentListEvent();
		m_eventBus.post(answerDepartmentListEvent);
		return;
	}

}

