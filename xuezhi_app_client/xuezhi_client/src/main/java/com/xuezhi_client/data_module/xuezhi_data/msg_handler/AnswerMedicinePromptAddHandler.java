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
import com.xuezhi_client.net.config.MedicinePromptConfig;
import com.xuezhi_client.net.config.ProtocalConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnswerMedicinePromptAddHandler extends IResponseListener
{
	@Override
	public void onResponse(JSONObject response)
	{
		int MID = DataConfig.DEFAULT_ID;
		try
		{
			//02. http is ok
			int httpStatus = response.getInt(ProtocalConfig.HTTP_STATUS);

			if (!LogicalUtil.IsHttpSuccess(httpStatus))
			{
				String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
				TipsDialog.GetInstance().setMsg(errorMsg);
				TipsDialog.GetInstance().show();
				return;
			}

			JSONArray jsonArray = response.getJSONArray(MedicinePromptConfig.LIST);
			if (jsonArray != null)
			{
				if (jsonArray.length() > 0)
				{
					JSONObject jsonObject = (JSONObject)jsonArray.get(0);
					if (jsonObject != null)
					{
						MID = jsonObject.getInt(MedicinePromptConfig.MID);
					}
				}
			}

		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}

		//解析成功，发送event
		AnswerMedicinePromptAddEvent event = new AnswerMedicinePromptAddEvent();
		event.setMID(MID);
		DBusinessMsgHandler.GetInstance().answerMedicinePromptAddAction(event);
		return;
	}
}
