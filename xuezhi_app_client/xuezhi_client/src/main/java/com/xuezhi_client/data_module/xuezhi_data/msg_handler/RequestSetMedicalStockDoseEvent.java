package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;
import com.xuezhi_client.net.config.MedicalStockListConfig;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/10/20.
 */
public class RequestSetMedicalStockDoseEvent extends BaseNetEvent
{
	private String m_RPID = null;
	private String m_UID = null;
	private String m_Dose = null;

	public RequestSetMedicalStockDoseEvent()
	{
		super(EventID.QUEST_MEDICAL_STOCK_SET_DOSE);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicalStockListConfig.RPID, m_RPID);
		sendData.put(MedicalStockListConfig.UID, m_UID);
		sendData.put(MedicalStockListConfig.DOSE, m_Dose);

		return sendData;
	}

	public void setRPID(String RPID)
	{
		m_RPID = RPID;
	}

	public void setUID(String UID)
	{
		m_UID = UID;
	}

	public void setDose(String dose)
	{
		m_Dose = dose;
	}
}
