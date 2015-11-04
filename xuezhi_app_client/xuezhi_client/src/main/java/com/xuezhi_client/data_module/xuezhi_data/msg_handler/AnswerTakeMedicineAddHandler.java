/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/30		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.net.IResponseListener;
import com.module.util.logcal.LogicalUtil;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.net.config.ProtocalConfig;
import com.xuezhi_client.net.config.TakeMedicineConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerTakeMedicineAddHandler extends IResponseListener
{
	@Override
	public void onResponse(JSONObject response)
	{
		int httpStatus = DataConfig.DEFAULT_VALUE;
		int MID = DataConfig.DEFAULT_ID;
		String errorMsg = "";
		try
		{
			//02. http is ok
			httpStatus = response.getInt(ProtocalConfig.HTTP_STATUS);
			if (!LogicalUtil.IsHttpSuccess(httpStatus))
			{
				errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
				MID = response.getInt(TakeMedicineConfig.MID);
			}
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}

		//解析成功，发送event
		AnswerTakeMedicineAddEvent event = new AnswerTakeMedicineAddEvent();
		event.setHttpStatus(httpStatus);
		event.setErrorMsg(errorMsg);
		event.setMID(MID);
		DBusinessMsgHandler.GetInstance().answerTakeMedicineAddAction(event);
		return;
	}
}
