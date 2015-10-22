package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;

/**
 * Created by Administrator on 2015/10/20.
 */
public class AnswerMedicineBoxSetEvent extends BaseNetEvent
{
	public AnswerMedicineBoxSetEvent()
	{
		super(EventID.ANSWER_MEDICAL_STOCK_ADD_DOSE);
	}
}
