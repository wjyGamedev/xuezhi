/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/22		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_order_list.msg_handler;

import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.net.IResponseListener;
import com.module.util.logcal.LogicalUtil;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.NurseListHandler;
import com.taixinkanghu_client.net.config.ProtocalConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerCommentNurseOrderHandler extends IResponseListener
{
	@Override
	public void onResponse(JSONObject response)
	{
		try
		{
			//02. http is ok
			int httpStatus = response.getInt(ProtocalConfig.HTTP_STATUS);

			if (!LogicalUtil.IsHttpSuccess(httpStatus))
			{
				String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
				TipsDialog.GetInstance().setMsg(errorMsg);
				TipsDialog.GetInstance().show();
				//无论成功失败，都发送event
			}
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
		NurseListHandler.GetInstance().answerCommentNurseOrderAction();
		return;
	}
}
