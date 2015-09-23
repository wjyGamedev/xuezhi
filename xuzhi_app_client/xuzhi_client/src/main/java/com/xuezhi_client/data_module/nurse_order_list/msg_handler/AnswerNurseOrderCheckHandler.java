/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/20		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_order_list.msg_handler;

import com.module.net.IResponseListener;
import com.module.util.logcal.LogicalUtil;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.net.config.config.NurseOrderConfig;
import com.xuezhi_client.net.config.config.ProtocalConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerNurseOrderCheckHandler extends IResponseListener
{
	@Override
	public void onResponse(JSONObject response)
	{
		int httpStatus = DataConfig.DEFAULT_VALUE;
		try
		{
			httpStatus = response.getInt(ProtocalConfig.HTTP_STATUS);

			//01. 失败,但不是占用的情况，我们提示出来。
			if (!LogicalUtil.IsHttpSuccess(httpStatus))
			{
				//0101. 占用情况，在相应的UI的msg进行处理，这里不处理
				if (httpStatus == NurseOrderConfig.NURSE_IN_SERVICE)
				{
				}
				//0102. 其他错误情况，不跳转，直接显示失败信息。
				else
				{
					String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
					TipsDialog.GetInstance().setMsg(errorMsg);
					TipsDialog.GetInstance().show();
					return;
				}
			}
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.getMessage().toString());
			TipsDialog.GetInstance().show();
			return;
		}

		//02. 发送event
		AnswerNurseOrderCheckEvent event = new AnswerNurseOrderCheckEvent();
		event.setHttpStatus(httpStatus);
		NurseOrderListHandler.GetInstance().answerNurseOrderCheckAction(event);
		return;

	}
}
