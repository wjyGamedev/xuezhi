/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/16		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_list.msg_handler;

import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.net.IResponseListener;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.nurse_list.data.DNurseContainer;

import org.json.JSONException;
import org.json.JSONObject;

import event.EventBus;

public class AnswerNurseBasicListHandler extends IResponseListener
{
	private EventBus m_eventBus = EventBus.getDefault();

	@Override
	public void onResponse(JSONObject response)
	{
		//01. 反序列化json
		try
		{
			DNurseContainer.GetInstance().serialBasiclist(response);
		}
		catch (JsonSerializationException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}

		//02. 解析成功，发送event，到SelectNurseActivity
		NurseListHandler.GetInstance().answerNurseBasicListAction();

		//03. 发送event，请求nurse senior消息
		NurseListHandler.GetInstance().requestNurseSeniorListAction();


		return;

	}
}
