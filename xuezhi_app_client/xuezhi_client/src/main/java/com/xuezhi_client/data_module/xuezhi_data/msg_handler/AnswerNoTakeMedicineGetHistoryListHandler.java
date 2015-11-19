/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.net.IResponseListener;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DNoTakeMedicineList;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class AnswerNoTakeMedicineGetHistoryListHandler extends IResponseListener
{
	@Override
	public void onResponse(JSONObject response)
	{
		try
		{
			DNoTakeMedicineList noTakeMedicineList = DBusinessData.GetInstance().getNoTakeMedicineList();
			noTakeMedicineList.serialization(response);
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
		DBusinessMsgHandler.GetInstance().answerNoTakeMedicineGetHistoryListAction();
		return;
	}
}
