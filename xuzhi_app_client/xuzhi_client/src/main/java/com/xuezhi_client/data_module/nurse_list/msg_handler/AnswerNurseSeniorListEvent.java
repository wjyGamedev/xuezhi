/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.event.net.recv.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/17		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_list.msg_handler;


import com.module.event.BaseNetEvent;
import com.module.event.EventID;

public class AnswerNurseSeniorListEvent extends BaseNetEvent
{
	public AnswerNurseSeniorListEvent()
	{
		super(EventID.FINISHED_NURSE_SENIOR_LIST);
	}
}
