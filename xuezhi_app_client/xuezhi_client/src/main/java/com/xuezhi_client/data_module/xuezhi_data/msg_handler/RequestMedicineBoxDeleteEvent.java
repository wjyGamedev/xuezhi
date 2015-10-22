package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;
import com.xuezhi_client.net.config.MedicineBoxConfig;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/10/16.
 */
public class RequestMedicineBoxDeleteEvent extends BaseNetEvent
{
	private String m_UID  = null;
	private String m_MBID = null;

	public RequestMedicineBoxDeleteEvent()
	{
		super(EventID.QUEST_MEDICAL_STOCK_DEL);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicineBoxConfig.UID, m_UID);
		sendData.put(MedicineBoxConfig.ID, m_MBID);

		return sendData;
	}

	public void setUID(String UID)
	{
		m_UID = UID;
	}

	public void setMBID(String MBID)
	{
		m_MBID = MBID;
	}
}
