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
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.net.config.MedicalListConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RequestAddMedicalPromptEvent extends BaseNetEvent
{
	private String   m_UID  = null;    //用户ID
	private String   m_RPID = null;    //库存ID
	private Calendar m_time = null;    //提醒时间
	private double   m_dose   = DataConfig.DEFAULT_VALUE;    //药品剂量
	private String   m_remark = "";    //备注

	private SimpleDateFormat m_hmsSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE_SECOND);


	public RequestAddMedicalPromptEvent()
	{
		super(EventID.QUEST_ADD_MEDICAL_PROMPT);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicalListConfig.UID, m_UID);
		sendData.put(MedicalListConfig.RPID, m_RPID);

		String display = m_hmsSDF.format(m_time.getTime());
		sendData.put(MedicalListConfig.TIME, display);
		sendData.put(MedicalListConfig.DOSE, String.valueOf(m_dose));
		sendData.put(MedicalListConfig.REMARK, m_remark);

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

	public void setTime(Calendar time)
	{
		m_time = time;
	}

	public void setDose(double dose)
	{
		this.m_dose = dose;
	}

	public void setRemark(String remark)
	{
		this.m_remark = remark;
	}

}
