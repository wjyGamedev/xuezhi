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

public class AnswerMedicinePromptAddEvent extends BaseNetEvent
{
	private int m_MID = DataConfig.DEFAULT_ID;

	public AnswerMedicinePromptAddEvent()
	{
		super(EventID.ANSWER_ADD_MEDICAL_PROMPT);
	}

	public int getMID()
	{
		return m_MID;
	}

	public void setMID(int MID)
	{
		m_MID = MID;
	}
}

