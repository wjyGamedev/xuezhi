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
 * 2015/7/19		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_list.msg_handler;

import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.net.IResponseListener;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseContainer;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class AnswerNurseSeniorListHandler extends IResponseListener
{
	@Override
	public void onResponse(JSONObject response)
	{
		try
		{
			DNurseContainer.GetInstance().serialSeniorList(response);
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
		catch (ParseException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}

		//解析成功，发送event
		NurseListHandler.GetInstance().answerCommentNurseOrderAction();
		return;
	}
}
