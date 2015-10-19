package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.net.IResponseListener;
import com.module.util.logcal.LogicalUtil;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.net.config.ProtocalConfig;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/10/16.
 */
public class AnswerDelMedicalStockHandler extends IResponseListener
{
	@Override
	public void onResponse(JSONObject response)
	{
		try
		{
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
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}

		//解析成功，发送event
		AnswerDelMedicalStockEvent event = new AnswerDelMedicalStockEvent();
		DBusinessMsgHandler.GetInstance().answerDelMedicalStockAction(event);
		return;
	}
}
