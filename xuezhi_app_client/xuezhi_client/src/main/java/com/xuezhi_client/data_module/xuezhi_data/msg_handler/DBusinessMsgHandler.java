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

	private AnswerMedicineListHandler        m_answerMedicineListHandler        = new AnswerMedicineListHandler();
	private AnswerMedicineUnitGetListHandler m_answerMedicineUnitGetListHandler = new AnswerMedicineUnitGetListHandler();

	//我的药箱相关
	private AnswerMedicineBoxGetListHandler m_answerMedicineBoxGetListHandler = new AnswerMedicineBoxGetListHandler();
	private AnswerMedicineBoxAddHandler     m_answerMedicineBoxAddHandler     = new AnswerMedicineBoxAddHandler();
	private AnswerMedicineBoxDeleteHandler  m_answerMedicineBoxDeleteHandler  = new AnswerMedicineBoxDeleteHandler();
	private AnswerMedicineBoxSetHandler     m_answerMedicineBoxSetHandler     = new AnswerMedicineBoxSetHandler();

	//用药提醒相关
	private AnswerMedicinePromptGetListHandler m_answerMedicinePromptGetListHandler = new AnswerMedicinePromptGetListHandler();
	private AnswerMedicinePromptAddHandler     m_answerMedicinePromptAddHandler     = new AnswerMedicinePromptAddHandler();
	private AnswerMedicinePromptDeleteHandler  m_answerMedicinePromptDeleteHandler  = new AnswerMedicinePromptDeleteHandler();
	private AnswerMedicinePromptSetHandler     m_answerMedicinePromptSetHandler     = new AnswerMedicinePromptSetHandler();

	//化验检查相关
	private AnswerAssayDetectionGetListHandler m_answerAssayDetectionGetListHandler = new AnswerAssayDetectionGetListHandler();
	private AnswerAssayDetectionAddHandler     m_answerAssayDetectionAddHandler     = new AnswerAssayDetectionAddHandler();
	private AnswerAssayDetectionSetHandler     m_answerAssayDetectionSetHandler     = new AnswerAssayDetectionSetHandler();

	//用药相关
	private AnswerTakeMedicineGetHistoryListHandler m_answerTakeMedicineGetHistoryListHandler = new
			AnswerTakeMedicineGetHistoryListHandler();

	private AnswerTakeMedicineAddHandler m_answerTakeMedicineAddHandler = new AnswerTakeMedicineAddHandler();

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

	//01. 不需要用户ID和密码
	//0101. 获取支持药品的下拉列表
	public void requestMedicineGetListAction()
	{
		RequestMedicineGetListEvent requestMedicineGetListEvent = new RequestMedicineGetListEvent();
		m_eventBus.post(requestMedicineGetListEvent);
		return;
	}

	public void onEventAsync(RequestMedicineGetListEvent event)
	{
		JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
														NetConfig.S_NORMAL_MEDICINE_GET_LIST,
														null,
														m_answerMedicineListHandler,
														m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicineGetListAction()
	{
		AnswerMedicineGetListEvent answerMedicineGetListEvent = new AnswerMedicineGetListEvent();
		m_eventBus.post(answerMedicineGetListEvent);
		return;
	}

	//0102. 药品单位列表
	public void requestMedicineUnitGetListAction()
	{
		RequestMedicineUnitGetListEvent event = new RequestMedicineUnitGetListEvent();
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicineUnitGetListEvent event)
	{
		JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
														NetConfig.S_NORMAL_MEDICAL_UNIT_GET_LIST,
														null, m_answerMedicineUnitGetListHandler,
														m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicineUnitGetListAction()
	{
		AnswerMedicineUnitGetListHandler answerMedicalListEvent = new AnswerMedicineUnitGetListHandler();
		m_eventBus.post(answerMedicalListEvent);
		return;
	}

	//02. 需要用户名和密码的
	//0201. 我的药箱相关
	//020101.获取我的药箱列表
	public void requestMedicineBoxGetListAction(RequestMedicineBoxGetListEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicineBoxGetListEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICINE_BOX_GET_LIST,
																sendData, m_answerMedicineBoxGetListHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicineBoxGetListAction()
	{
		AnswerMedicineBoxGetListEvent event = new AnswerMedicineBoxGetListEvent();
		m_eventBus.post(event);
		return;
	}

	//020102.添加药品到药箱中，如果添加ID相同，就累加remain的数量
	public void requestMedicineBoxAddAction(RequestMedicineBoxAddEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicineBoxAddEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICINE_BOX_ADD,
																sendData, m_answerMedicineBoxAddHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicineBoxAddAction(AnswerMedicineBoxAddEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//020103.从药箱中删除药品
	public void requestMedicineBoxDeleteAction(RequestMedicineBoxDeleteEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicineBoxDeleteEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICINE_BOX_DELETE,
																sendData, m_answerMedicineBoxDeleteHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicineBoxDeleteAction(AnswerMedicineBoxDeleteEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//020104.设置药箱中的药品
	public void requestMedicineBoxSetAction(RequestMedicineBoxSetEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicineBoxSetEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICINE_BOX_SET,
																sendData, m_answerMedicineBoxSetHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicineBoxSetAction(AnswerMedicineBoxSetEvent event)
	{
		m_eventBus.post(event);
		return;
	}



	//0202. 用药提醒相关
	//020201.获取用药提醒列表
	public void requestMedicinePromptGetListAction()
	{
		RequestMedicinePromptGetListEvent event = new RequestMedicinePromptGetListEvent();
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicinePromptGetListEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICINE_PROMPT_GET_LIST,
																sendData, m_answerMedicinePromptGetListHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicinePromptGetListAction()
	{
		AnswerMedicinePromptGetListEvent event = new AnswerMedicinePromptGetListEvent();
		m_eventBus.post(event);
		return;
	}

	//020202.添加用药提醒
	public void requestMedicinePromptAddAction(RequestMedicinePromptAddEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicinePromptAddEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICINE_PROMPT_ADD,
																sendData, m_answerMedicinePromptAddHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicinePromptAddAction(AnswerMedicinePromptAddEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//020203.删除用药提醒
	public void requestMedicinePromptDeleteAction(RequestMedicinePromptDeleteEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicinePromptDeleteEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICINE_PROMPT_DELETE,
																sendData, m_answerMedicinePromptDeleteHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicinePromptDeleteAction(AnswerMedicinePromptDeleteEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//020204.设置用药提醒
	public void requestMedicinePromptSetAction(RequestMedicinePromptSetEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestMedicinePromptSetEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_MEDICINE_PROMPT_SET,
																sendData, m_answerMedicinePromptSetHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerMedicinePromptSetAction(AnswerMedicinePromptSetEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//0203. 化验检查相关(血脂/生化)
	//020301. 化验检查信息列表
	public void requestAssayDetectionGetListAction(RequestAssayDetectionGetListEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestAssayDetectionGetListEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_ASSAY_DETECTION_GET_LIST,
																sendData, m_answerAssayDetectionGetListHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerAssayDetectionGetListAction(AnswerAssayDetectionGetListEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//020302. 添加用药检查列表
	public void requestAssayDetectionAddAction(RequestAssayDetectionAddEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestAssayDetectionAddEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_ASSAY_DETECTION_ADD,
																sendData, m_answerAssayDetectionAddHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerAssayDetectionAddAction(AnswerAssayDetectionAddEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//020303. 设置用药检查列表
	public void requestAssayDetectionSetAction(RequestAssayDetectionSetEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestAssayDetectionSetEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_ASSAY_DETECTION_SET,
																sendData, m_answerAssayDetectionSetHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerAssayDetectionSetAction(AnswerAssayDetectionSetEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	//0204. 用药相关
	//020401. 用药记录列表
	public void requestTakeMedicineGetHistoryListAction(RequestTakeMedicineGetHistoryListEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestTakeMedicineGetHistoryListEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_TAKE_MEDICINE_GET_HISTORY_LIST,
																sendData, m_answerTakeMedicineGetHistoryListHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerTakeMedicineGetHistoryListAction()
	{
		AnswerTakeMedicineGetHistoryListEvent event = new AnswerTakeMedicineGetHistoryListEvent();
		m_eventBus.post(event);
		return;
	}

	//020402.吃药
	public void requestTakeMedicineAddAction(RequestTakeMedicineAddEvent event)
	{
		m_eventBus.post(event);
		return;
	}

	public void onEventAsync(RequestTakeMedicineAddEvent event)
	{
		HashMap<String, String> sendData = event.getHashMap();

		JsonObjectRequestForm myReq = new JsonObjectRequestForm(Request.Method.POST,
																NetConfig.S_NORMAL_TAKE_MEDICINE_ADD,
																sendData, m_answerTakeMedicineAddHandler,
																m_baseErrorListener
		);

		m_requestQueue.add(myReq);
	}

	public void answerTakeMedicineAddAction(AnswerTakeMedicineAddEvent event)
	{
		m_eventBus.post(event);
		return;
	}

}
