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

package com.xuezhi_client.data_module.xuezhi_data.msg_handler;


import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.net.IResponseListener;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicalList;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerMedicalListHandler extends IResponseListener
{
	@Override
	public void onResponse(JSONObject response)
	{
		try
		{
			DMedicalList medicalList = DBusinessData.GetInstance().getMedicalList();
			medicalList.serialization(response);
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

		//解析成功，发送event
		DBusinessMsgHandler.GetInstance().answerMedicalListAction();
		return;

	}
}
