/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/22		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_order_list.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;

public class AnswerCommentNurseOrderEvent extends BaseNetEvent
{
	public AnswerCommentNurseOrderEvent()
	{
		super(EventID.ANSWER_COMMENT_NURSE_ORDER);
	}
}
