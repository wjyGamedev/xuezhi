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
 * 2015/8/23		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_order_list.msg_handler;


import com.module.util.logcal.LogicalUtil;
import com.module.widget.dialog.TipsDialog;
import com.module.net.IResponseListener;
import com.taixinkanghu_client.net.config.ProtocalConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerNurseOrderCancelInWaitPayHandler extends IResponseListener
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
				return;
			}
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.getMessage().toString());
			TipsDialog.GetInstance().show();
			return;
		}

		//02. 取消订单成功，则需要update nurse list
		NurseOrderListHandler.GetInstance().answerNurseOrderCancelInWaitPayAction();
		return;
	}
}
