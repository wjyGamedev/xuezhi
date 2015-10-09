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
import com.xuezhi_client.net.config.MedicalStockListConfig;

import java.util.HashMap;

public class RequestAddMedicalStockEvent extends BaseNetEvent
{
	private String  m_UID       = null;    //用户ID
	private String  m_MID       = null;    //药品ID
	private double  m_remainNum = DataConfig.DEFAULT_VALUE;    //剩余药品数量
	private String  m_unitID    = null;    //药品单位ID
	private double  m_waringNum = DataConfig.DEFAULT_VALUE;    //警报药品数量
	private boolean m_status    = true;    //开启状态

	public RequestAddMedicalStockEvent()
	{
		super(EventID.QUEST_MEDICAL_STOCK_ADD);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicalStockListConfig.UID, m_UID);
		sendData.put(MedicalStockListConfig.MID, m_MID);
		sendData.put(MedicalStockListConfig.REMAIN, String.valueOf(m_remainNum));
		sendData.put(MedicalStockListConfig.UINTID, m_unitID);
		sendData.put(MedicalStockListConfig.WARNING, String.valueOf(m_waringNum));
		sendData.put(MedicalStockListConfig.STATUS, String.valueOf(m_status));

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

	public String getMID()
	{
		return m_MID;
	}

	public void setMID(String MID)
	{
		m_MID = MID;
	}

	public double getRemainNum()
	{
		return m_remainNum;
	}

	public void setRemainNum(double remainNum)
	{
		m_remainNum = remainNum;
	}

	public String getUnitID()
	{
		return m_unitID;
	}

	public void setUnitID(String unitID)
	{
		m_unitID = unitID;
	}

	public double getWaringNum()
	{
		return m_waringNum;
	}

	public void setWaringNum(double waringNum)
	{
		m_waringNum = waringNum;
	}

	public boolean isStatus()
	{
		return m_status;
	}

	public void setStatus(boolean status)
	{
		m_status = status;
	}
}
