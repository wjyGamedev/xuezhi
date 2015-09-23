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
 * 2015/8/19		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_order_list.msg_handler;

import com.module.data.DGlobal;
import com.module.net.IResponseListener;
import com.module.util.logcal.LogicalUtil;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.net.config.NurseOrderConfig;
import com.taixinkanghu_client.net.config.ProtocalConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AnswerNurseOrderConfirmInChangeNurseHandler extends IResponseListener
{
	private int    m_Status         = ProtocalConfig.HTTP_OK;
	private int    m_nurseID        = 0;
	private int    m_orderID        = 0;
	private String m_orderSerialNum = null;
	private int    m_totalPrice     = 0;

	@Override
	public void onResponse(JSONObject response)
	{
		try
		{
			m_Status = response.getInt(ProtocalConfig.HTTP_STATUS);

			if (!LogicalUtil.IsHttpSuccess(m_Status))
			{
				String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
				TipsDialog.GetInstance().setMsg(errorMsg);
				TipsDialog.GetInstance().show();
				return;
			}

			JSONArray jsonArray = response.getJSONArray(NurseOrderConfig.NURSE_ORDER_LIST);
			if (jsonArray == null)
			{
				String errMsg = DGlobal.GetInstance().getAppContext().getString(R.string.net_error_json_serilization);
				TipsDialog.GetInstance().setMsg(errMsg + ":" + NurseOrderConfig.NURSE_ORDER_LIST);
				TipsDialog.GetInstance().show();
				return;
			}
			if (jsonArray.length() != 1)
			{
				TipsDialog.GetInstance().setMsg("jsonArray.length() != 1");
				TipsDialog.GetInstance().show();
				return;
			}

			JSONObject jsonObject = (JSONObject)jsonArray.get(0);
			m_nurseID = jsonObject.getInt(NurseOrderConfig.NURSE_ID);
			m_orderID = jsonObject.getInt(NurseOrderConfig.ORDER_ID);
			m_orderSerialNum = jsonObject.getString(NurseOrderConfig.ORDER_SERIAL_NUM);
			m_totalPrice = jsonObject.getInt(NurseOrderConfig.ORDER_USER_PAY);

		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}

		//02. 接收成功，
		AnswerNurseOrderConfirmInChangeNurseEvent event = new AnswerNurseOrderConfirmInChangeNurseEvent();
		event.setNurseID(m_nurseID);
		event.setOrderID(m_orderID);
		event.setOrderSerialNum(m_orderSerialNum);
		event.setTotalPrice(m_totalPrice);

		NurseOrderListHandler.GetInstance().answerNurseOrderConfirmInChangeNurseAction(event);

		return;

	}
}
