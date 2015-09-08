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
 * 2015/8/17		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.department_list.msg_handler;


import com.module.event.EventID;
import com.taixinkanghu_client.net.event.BaseNetEvent;

public class AnswerDepartmentListEvent extends BaseNetEvent
{
	public AnswerDepartmentListEvent()
	{
		super(EventID.FINISHED_DEPARTMENT_LIST);
	}
}
