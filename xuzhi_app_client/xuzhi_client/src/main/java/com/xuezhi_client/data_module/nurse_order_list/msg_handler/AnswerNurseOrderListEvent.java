/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.event.recv.${type_name}
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


import com.module.event.BaseNetEvent;
import com.module.event.EventID;

public class AnswerNurseOrderListEvent extends BaseNetEvent
{
	public AnswerNurseOrderListEvent()
	{
		super(EventID.FINISHED_NURSE_ORDER_CONFIRM);
	}


}
