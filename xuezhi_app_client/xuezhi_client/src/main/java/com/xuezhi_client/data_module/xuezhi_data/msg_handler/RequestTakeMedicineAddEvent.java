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
import com.xuezhi_client.net.config.TakeMedicineConfig;

import java.util.HashMap;

public class RequestTakeMedicineAddEvent extends BaseNetEvent
{
	private String m_UID    = null;    //用户ID
	private String m_MID   = null;    //库存ID
	private String m_PID   = null;    //用药提醒ID
	private double m_dose   = DataConfig.DEFAULT_VALUE;    //药品剂量

	public RequestTakeMedicineAddEvent()
	{
		super(EventID.QUEST_TAKE_MEDICAL_EVENT);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(TakeMedicineConfig.UID, m_UID);
		sendData.put(TakeMedicineConfig.MID, m_MID);
		sendData.put(TakeMedicineConfig.PID, m_PID);
		sendData.put(TakeMedicineConfig.DOSE, String.valueOf(m_dose));

		return sendData;
	}

	public void setUID(String UID)
	{
		m_UID = UID;
	}

	public void setMID(String MID)
	{
		m_MID = MID;
	}

	public void setPID(String PID)
	{
		m_PID = PID;
	}

	public void setDose(double dose)
	{
		m_dose = dose;
	}
}
