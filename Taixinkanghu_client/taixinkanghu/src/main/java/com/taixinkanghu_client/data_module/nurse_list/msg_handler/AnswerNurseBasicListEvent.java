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
 * 2015/8/16		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_list.msg_handler;


import com.module.event.EventID;
import com.module.event.BaseNetEvent;

public class AnswerNurseBasicListEvent extends BaseNetEvent
{
	public AnswerNurseBasicListEvent()
	{
		super(EventID.FINISHED_NURSE_BASIC_LIST);
	}
}
