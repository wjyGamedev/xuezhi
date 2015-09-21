/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/16		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.hospital_list.msg_handler;


import com.module.event.EventID;
import com.module.event.BaseNetEvent;

public class AnswerHospitalListEvent extends BaseNetEvent
{
	public AnswerHospitalListEvent()
	{
		super(EventID.FINISHED_HOSPITAL_LIST);
	}
}
