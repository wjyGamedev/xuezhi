/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.net.config.MedicalListConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RequestMedicalHistoryListEvent extends BaseNetEvent
{
	private String   m_UID  = null;    //用户ID
	private Calendar m_currMonth = null;    //月份

	private SimpleDateFormat m_ymdSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	public RequestMedicalHistoryListEvent()
	{
		super(EventID.QUEST_MEDICAL_HISTORY_LIST);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicalListConfig.UID, m_UID);
		String display = m_ymdSDF.format(m_currMonth.getTime());
		sendData.put(MedicalListConfig.DATE, display);

		return sendData;
	}

	public void setUID(String UID)
	{
		m_UID = UID;
	}

	public void setCurrMonth(Calendar time)
	{
		m_currMonth = time;
	}
}
