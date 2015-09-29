/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.Event.net.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/9		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.msg_handler;


import com.module.event.BaseNetEvent;
import com.module.event.EventID;

public class RequestMedicalListEvent extends BaseNetEvent
{
	public RequestMedicalListEvent()
	{
		super(EventID.QUEST_MEDICAL_LIST);
	}
}
