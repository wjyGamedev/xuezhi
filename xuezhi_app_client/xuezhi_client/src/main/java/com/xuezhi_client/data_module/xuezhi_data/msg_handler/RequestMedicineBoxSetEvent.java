package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.net.config.MedicineBoxConfig;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/10/20.
 */
public class RequestMedicineBoxSetEvent extends BaseNetEvent
{
	private String  m_MBID      = null;    //药箱中的药品ID
	private String  m_UID       = null;    //用户ID
	private double  m_remainNum = DataConfig.DEFAULT_VALUE;    //剩余药品数量
	private double  m_waringNum = DataConfig.DEFAULT_VALUE;    //警报药品数量
	private boolean m_valid     = true;    //开启状态

	public RequestMedicineBoxSetEvent()
	{
		super(EventID.QUEST_MEDICAL_STOCK_SET_DOSE);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicineBoxConfig.ID, m_MBID);
		sendData.put(MedicineBoxConfig.UID, m_UID);
		sendData.put(MedicineBoxConfig.REMAIN, String.valueOf(m_remainNum));
		sendData.put(MedicineBoxConfig.WARNING, String.valueOf(m_waringNum));
		int tmpValid = m_valid == true ? 1 : 0;
		sendData.put(MedicineBoxConfig.VALID, String.valueOf(tmpValid));

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
