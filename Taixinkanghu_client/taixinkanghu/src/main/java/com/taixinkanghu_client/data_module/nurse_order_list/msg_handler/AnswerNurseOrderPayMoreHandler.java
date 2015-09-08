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
 * 2015/8/25		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_order_list.msg_handler;

import com.module.util.logcal.LogicalUtil;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.net.IResponseListener;
import com.taixinkanghu_client.net.config.NurseOrderConfig;
import com.taixinkanghu_client.net.config.ProtocalConfig;

import org.json.JSONException;
import org.json.JSONObject;

import event.EventBus;

public class AnswerNurseOrderPayMoreHandler extends IResponseListener
{
	private int    m_Status         = ProtocalConfig.HTTP_OK;
	private String m_orderID        = null;
	private String m_orderSerialNum = null;
	private int    m_price          = DataConfig.DEFAULT_VALUE;

	private EventBus m_eventBus = EventBus.getDefault();


	@Override
	public void onResponse(JSONObject response)
	{
		try
		{
			//02. http is ok
			m_Status = response.getInt(ProtocalConfig.HTTP_STATUS);

			if (!LogicalUtil.IsHttpSuccess(m_Status))
			{
				String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
				TipsDialog.GetInstance().setMsg(errorMsg);
				TipsDialog.GetInstance().show();
				return;
			}

			JSONObject payMoreObjecst = response.getJSONObject(NurseOrderConfig.ORDER_PAY_MORE_OBJECT);
			m_orderID = payMoreObjecst.getString(NurseOrderConfig.ORDER_ID);
			m_orderSerialNum = payMoreObjecst.getString(NurseOrderConfig.ORDER_SERIAL_NUM);
			m_price = payMoreObjecst.getInt(NurseOrderConfig.ORDER_PAY_MORE_PRICE);

		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.getMessage().toString());
			TipsDialog.GetInstance().show();
			return;
		}

		//02. 取消订单成功，则需要update nurse list
		AnswerNurseOrderPayMoreEvent event = new AnswerNurseOrderPayMoreEvent();
		event.setOrderID(Integer.valueOf(m_orderID));
		event.setOrderSerialNum(m_orderSerialNum);
		event.setPrice(m_price);

		NurseOrderListHandler.GetInstance().answerNurseOrderPayMoreAction(event);
		return;
	}
}
