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
import com.xuezhi_client.net.config.MedicinePromptConfig;

import java.util.HashMap;

public class RequestMedicinePromptDeleteEvent extends BaseNetEvent
{
	private String   m_MPID  = null;
	private String   m_UID  = null;    //用户ID

	public RequestMedicinePromptDeleteEvent()
	{
		super(EventID.QUEST_ADD_MEDICAL_PROMPT);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicinePromptConfig.ID, m_MPID);
		sendData.put(MedicinePromptConfig.UID, m_UID);

		return sendData;
	}

	public void setMPID(String MPID)
	{
		m_MPID = MPID;
	}

	public void setUID(String UID)
	{
		m_UID = UID;
	}
}
