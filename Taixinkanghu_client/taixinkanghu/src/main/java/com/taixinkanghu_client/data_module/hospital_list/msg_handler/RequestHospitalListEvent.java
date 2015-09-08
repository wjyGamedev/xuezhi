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

package com.taixinkanghu_client.data_module.hospital_list.msg_handler;


import com.module.event.EventID;
import com.taixinkanghu_client.net.event.BaseNetEvent;

public class RequestHospitalListEvent extends BaseNetEvent
{
	public RequestHospitalListEvent()
	{
		super(EventID.QUEST_HOSPITAL_LIST);
	}
}
