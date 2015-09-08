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

package com.taixinkanghu_client.data_module.nurse_order_list.msg_handler;

import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.net.IResponseListener;
import com.taixinkanghu_client.net.config.ProtocalConfig;

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

//			//01. 失败
//			if (!LogicalUtil.IsHttpSuccess(httpStatus))
//			{
//				String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
//				TipsDialog.GetInstance().setMsg(errorMsg);
//				TipsDialog.GetInstance().show();
//
//				//0101. 占用情况，需跳转UI界面。
//				if (httpStatus == NurseOrderConfig.NURSE_IN_SERVICE)
//				{
//					FailedNurseOrderCheckEvent event = new FailedNurseOrderCheckEvent();
//					m_eventBus.post(event);
//					return;
//				}
//				//0102. 其他错误情况，不跳转，直接显示失败信息。
//				else
//				{
//					return;
//				}
//			}
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
