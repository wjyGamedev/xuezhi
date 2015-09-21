/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.event.net.send.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/17		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.department_list.msg_handler;


import com.module.event.EventID;
import com.module.event.BaseNetEvent;

public class RequestDepartmentListEvent extends BaseNetEvent
{
	public RequestDepartmentListEvent()
	{
		super(EventID.QUEST_HOSPITAL_LIST);
	}
}
