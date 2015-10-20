package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;

/**
 * Created by Administrator on 2015/10/20.
 */
public class AnswerSetMedicalStockDoseEvent extends BaseNetEvent
{
	public AnswerSetMedicalStockDoseEvent()
	{
		super(EventID.ANSWER_MEDICAL_STOCK_ADD_DOSE);
	}
}
