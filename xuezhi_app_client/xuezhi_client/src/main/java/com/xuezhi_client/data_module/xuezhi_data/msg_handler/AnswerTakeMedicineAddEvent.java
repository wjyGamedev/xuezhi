/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/30		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;
import com.xuezhi_client.config.DataConfig;

public class AnswerTakeMedicineAddEvent extends BaseNetEvent
{
	private int httpStatus = DataConfig.DEFAULT_VALUE;
	private int MID = DataConfig.DEFAULT_ID;
	private String errorMsg = "";

	public AnswerTakeMedicineAddEvent()
	{
		super(EventID.ANSWER_TAKE_MEDICAL_EVENT);
	}

	public int getHttpStatus()
	{
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus)
	{
		this.httpStatus = httpStatus;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}

	public int getMID()
	{
		return MID;
	}

	public void setMID(int MID)
	{
		this.MID = MID;
	}
}
