package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.net.config.MedicalStockListConfig;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/10/20.
 */
public class RequestSetMedicalStockDoseEvent extends BaseNetEvent
{
	private String  m_UID       = null;    //用户ID
	private String  m_MID       = null;    //药品ID
	private double  m_remainNum = DataConfig.DEFAULT_VALUE;    //剩余药品数量
	private double  m_waringNum = DataConfig.DEFAULT_VALUE;    //警报药品数量
	private boolean m_status    = true;    //开启状态

	public RequestSetMedicalStockDoseEvent()
	{
		super(EventID.QUEST_MEDICAL_STOCK_SET_DOSE);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicalStockListConfig.UID, m_UID);
		sendData.put(MedicalStockListConfig.MID, m_MID);
		sendData.put(MedicalStockListConfig.REMAIN, String.valueOf(m_remainNum));
		sendData.put(MedicalStockListConfig.WARNING, String.valueOf(m_waringNum));
		sendData.put(MedicalStockListConfig.VALID, String.valueOf(m_status));

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

	public void setRemainNum(double remainNum)
	{
		m_remainNum = remainNum;
	}

	public void setWaringNum(double waringNum)
	{
		m_waringNum = waringNum;
	}

	public void setStatus(boolean status)
	{
		m_status = status;
	}
}
