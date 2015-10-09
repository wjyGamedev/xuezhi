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
import com.xuezhi_client.net.config.MedicalListConfig;

import java.util.HashMap;

public class RequestTakeMedicalEvent extends BaseNetEvent
{
	private String m_UID    = null;    //用户ID
	private String m_RPID   = null;    //库存ID
	private double m_dose   = DataConfig.DEFAULT_VALUE;    //药品剂量
	private String m_unitID = null;    //用药剂量

	public RequestTakeMedicalEvent()
	{
		super(EventID.QUEST_TAKE_MEDICAL_EVENT);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicalListConfig.UID, m_UID);
		sendData.put(MedicalListConfig.RPID, m_RPID);
		sendData.put(MedicalListConfig.DOSE, String.valueOf(m_dose));
		sendData.put(MedicalListConfig.UNIT, m_unitID);

		return sendData;
	}


	public String getUID()
	{
		return m_UID;
	}

	public void setUID(String UID)
	{
		m_UID = UID;
	}

	public String getRPID()
	{
		return m_RPID;
	}

	public void setRPID(String RPID)
	{
		m_RPID = RPID;
	}

	public double getDose()
	{
		return m_dose;
	}

	public void setDose(double dose)
	{
		m_dose = dose;
	}

	public String getUnitID()
	{
		return m_unitID;
	}

	public void setUnitID(String unitID)
	{
		m_unitID = unitID;
	}
}
