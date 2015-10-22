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
import com.xuezhi_client.net.config.MedicinePromptConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RequestMedicinePromptAddEvent extends BaseNetEvent
{
	private String   m_UID  = null;    //用户ID
	private String   m_MID  = null;
	private Calendar m_time = Calendar.getInstance();
	private boolean m_valid = true;
	private double   m_dose   = DataConfig.DEFAULT_VALUE;    //药品剂量
	private String m_precaution = null;

	private SimpleDateFormat m_hmsSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE_SECOND);

	public RequestMedicinePromptAddEvent()
	{
		super(EventID.QUEST_ADD_MEDICAL_PROMPT);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicinePromptConfig.UID, m_UID);
		sendData.put(MedicinePromptConfig.MID, m_MID);

		String display = m_hmsSDF.format(m_time.getTime());
		sendData.put(MedicinePromptConfig.TIME, display);

		int tmpValid = m_valid == true ? 1 : 0;
		sendData.put(MedicinePromptConfig.VALID, String.valueOf(tmpValid));

		sendData.put(MedicinePromptConfig.DOSE, String.valueOf(m_dose));
		sendData.put(MedicinePromptConfig.PRECAUTION, String.valueOf(m_precaution));

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

	public void setTime(Calendar time)
	{
		m_time = time;
	}

	public void setValid(boolean valid)
	{
		m_valid = valid;
	}

	public void setDose(double dose)
	{
		m_dose = dose;
	}

	public void setPrecaution(String precaution)
	{
		m_precaution = precaution;
	}
}
