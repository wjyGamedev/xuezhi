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
 * 2015/8/19		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_order_list.msg_handler;

import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurserOrderList;
import com.taixinkanghu_client.net.IResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;


public class AnswerNurseOrderListHandler extends IResponseListener
{
	@Override
	public void onResponse(JSONObject response)
	{
		try
		{
			DNurserOrderList.GetInstance().serialization(response);
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
		NurseOrderListHandler.GetInstance().answerNurseOrderListAction();
		return;
	}
}
