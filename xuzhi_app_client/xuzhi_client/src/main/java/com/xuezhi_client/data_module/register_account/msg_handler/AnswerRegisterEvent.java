/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.register_page.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/3		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.register_account.msg_handler;


import com.module.event.BaseNetEvent;
import com.module.event.EventID;

public class AnswerRegisterEvent extends BaseNetEvent
{
	public AnswerRegisterEvent()
	{
		super(EventID.RESPONSE_REGISTER);
	}

}
