/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.register_flow.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/2		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.register_account.msg_handler;

import com.module.event.BaseEvent;
import com.module.event.EventID;

public class RequestSupportedCountriesEvent extends BaseEvent
{
	public RequestSupportedCountriesEvent()
	{
		super(EventID.QUEST_SUPPORTED_COUNTRIES_LIST);
	}
}
