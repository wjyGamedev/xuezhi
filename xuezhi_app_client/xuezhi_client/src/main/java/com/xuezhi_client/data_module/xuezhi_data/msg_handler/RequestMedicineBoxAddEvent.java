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
import com.xuezhi_client.net.config.MedicineBoxConfig;

import java.util.HashMap;

public class RequestMedicineBoxAddEvent extends BaseNetEvent
{
	private String  m_UID       = null;    //用户ID
	private String  m_MID       = null;    //药品ID
	private double  m_remainNum = DataConfig.DEFAULT_VALUE;    //剩余药品数量
	private double  m_waringNum = DataConfig.DEFAULT_VALUE;    //警报药品数量
	private boolean m_valid     = true;    //开启状态

	public RequestMedicineBoxAddEvent()
	{
		super(EventID.QUEST_MEDICAL_STOCK_ADD);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicineBoxConfig.UID, m_UID);
		sendData.put(MedicineBoxConfig.MID, m_MID);
		sendData.put(MedicineBoxConfig.WARNING, String.valueOf(m_waringNum));
		sendData.put(MedicineBoxConfig.REMAIN, String.valueOf(m_remainNum));
		sendData.put(MedicineBoxConfig.VALID, String.valueOf(m_valid));

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

	public void setValid(boolean valid)
	{
		m_valid = valid;
	}
}
