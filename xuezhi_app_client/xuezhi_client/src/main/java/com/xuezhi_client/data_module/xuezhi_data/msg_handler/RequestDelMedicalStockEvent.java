package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;
import com.xuezhi_client.net.config.MedicalStockListConfig;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/10/16.
 */
public class RequestDelMedicalStockEvent extends BaseNetEvent
{
	private String m_UID = null;
	private String m_RPID = null;

	public RequestDelMedicalStockEvent()
	{
		super(EventID.QUEST_MEDICAL_STOCK_DEL);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicalStockListConfig.UID, m_UID);
		sendData.put(MedicalStockListConfig.RPID, m_RPID);

		return sendData;
	}

	public void setUID(String UID)
	{
		m_UID = UID;
	}

	public void setRPID(String RPID)
	{
		m_RPID = RPID;
	}
}
