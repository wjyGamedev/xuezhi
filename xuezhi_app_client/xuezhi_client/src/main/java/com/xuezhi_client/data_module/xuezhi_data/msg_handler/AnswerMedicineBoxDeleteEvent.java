package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;

/**
 * Created by Administrator on 2015/10/16.
 */
public class AnswerMedicineBoxDeleteEvent extends BaseNetEvent
{
	public AnswerMedicineBoxDeleteEvent()
	{
		super(EventID.ANSWER_MEDICAL_STOCK_DEL);
	}
}
