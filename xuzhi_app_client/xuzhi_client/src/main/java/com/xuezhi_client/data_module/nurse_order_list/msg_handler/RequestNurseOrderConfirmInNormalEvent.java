/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.event.send.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/19		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_order_list.msg_handler;

import android.text.TextUtils;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;
import com.taixinkanghu_client.net.config.NurseOrderConfig;

import java.util.HashMap;

public class RequestNurseOrderConfirmInNormalEvent extends BaseNetEvent
{
	private final int DEFAULT_VALUE = -1;

	private int    m_hospitalID       = DEFAULT_VALUE;
	private String m_userID           = null;
	private int m_nurseID = DEFAULT_VALUE;
	private int m_nurseDepartmentID = DEFAULT_VALUE;
	private String m_phoneNum         = null;
	private String m_patientName      = null;
	private int    m_genderID         = DEFAULT_VALUE;
	private String m_patientAge       = null;
	private String m_patientWeight    = null;
	private int    m_patientStatusID  = DEFAULT_VALUE;
	private String m_patientRemark    = null;
	private int    m_totalCharge      = DEFAULT_VALUE;
	private String m_allDescription   = null;
	private String m_dayDescription   = null;
	private String m_nightDescription = null;

	public RequestNurseOrderConfirmInNormalEvent()
	{
		super(EventID.QUEST_NURSE_ORDER_CONFIRM);
	}

	public int getHospitalID()
	{
		return m_hospitalID;
	}

	public void setHospitalID(int hospitalID)
	{
		m_hospitalID = hospitalID;
	}

	public String getUserID()
	{
		return m_userID;
	}

	public void setUserID(String userID)
	{
		m_userID = userID;
	}

	public int getNurseID()
	{
		return m_nurseID;
	}

	public void setNurseID(int nurseID)
	{
		m_nurseID = nurseID;
	}

	public int getNurseDepartmentID()
	{
		return m_nurseDepartmentID;
	}

	public void setNurseDepartmentID(int nurseDepartmentID)
	{
		m_nurseDepartmentID = nurseDepartmentID;
	}

	public String getPhoneNum()
	{
		return m_phoneNum;
	}

	public void setPhoneNum(String phoneNum)
	{
		m_phoneNum = phoneNum;
	}

	public String getPatientName()
	{
		return m_patientName;
	}

	public void setPatientName(String patientName)
	{
		m_patientName = patientName;
	}

	public int getGenderID()
	{
		return m_genderID;
	}

	public void setGenderID(int genderID)
	{
		m_genderID = genderID;
	}

	public String getPatientAge()
	{
		return m_patientAge;
	}

	public void setPatientAge(String patientAge)
	{
		m_patientAge = patientAge;
	}

	public String getPatientWeight()
	{
		return m_patientWeight;
	}

	public void setPatientWeight(String patientWeight)
	{
		m_patientWeight = patientWeight;
	}

	public int getPatientStatusID()
	{
		return m_patientStatusID;
	}

	public void setPatientStatusID(int patientStatusID)
	{
		m_patientStatusID = patientStatusID;
	}

	public String getPatientRemark()
	{
		return m_patientRemark;
	}

	public void setPatientRemark(String patientRemark)
	{
		m_patientRemark = patientRemark;
	}

	public int getTotalCharge()
	{
		return m_totalCharge;
	}

	public void setTotalCharge(int totalCharge)
	{
		m_totalCharge = totalCharge;
	}

	public String getAllDescription()
	{
		return m_allDescription;
	}

	public void setAllDescription(String allDescription)
	{
		m_allDescription = allDescription;
	}

	public String getDayDescription()
	{
		return m_dayDescription;
	}

	public void setDayDescription(String dayDescription)
	{
		m_dayDescription = dayDescription;
	}

	public String getNightDescription()
	{
		return m_nightDescription;
	}

	public void setNightDescription(String nightDescription)
	{
		m_nightDescription = nightDescription;
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> nurseOrderConfirm = new HashMap<String, String>();

		if (m_hospitalID != DEFAULT_VALUE)
		{
			nurseOrderConfirm.put(NurseOrderConfig.HOSPITAL_ID, String.valueOf(m_hospitalID));
		}
		if (!TextUtils.isEmpty(m_userID))
		{
			nurseOrderConfirm.put(NurseOrderConfig.USER_ID, m_userID);
		}
		if (m_nurseID != DEFAULT_VALUE)
		{
			nurseOrderConfirm.put(NurseOrderConfig.NURSE_ID, String.valueOf(m_nurseID));
		}
		if (m_nurseDepartmentID != DEFAULT_VALUE)
		{
			nurseOrderConfirm.put(NurseOrderConfig.DEPARTMENT_ID, String.valueOf(m_nurseDepartmentID));
		}
		if (!TextUtils.isEmpty(m_phoneNum))
		{
			nurseOrderConfirm.put(NurseOrderConfig.USER_PHONE_NUM, m_phoneNum);
		}
		if (!TextUtils.isEmpty(m_patientName))
		{
			nurseOrderConfirm.put(NurseOrderConfig.PATIENT_NAME, m_patientName);
		}
		if (m_genderID != DEFAULT_VALUE)
		{
			nurseOrderConfirm.put(NurseOrderConfig.PATIENT_GENDER, String.valueOf(m_genderID));
		}
		if (!TextUtils.isEmpty(m_patientAge))
		{
			nurseOrderConfirm.put(NurseOrderConfig.PATIENT_AGE, m_patientAge);
		}
		if (!TextUtils.isEmpty(m_patientWeight))
		{
			nurseOrderConfirm.put(NurseOrderConfig.PATIENT_WEIGHT, m_patientWeight);
		}
		if (m_patientStatusID != DEFAULT_VALUE)
		{
			nurseOrderConfirm.put(NurseOrderConfig.PATIENT_STATUS, String.valueOf(m_patientStatusID));
		}
		if (!TextUtils.isEmpty(m_patientRemark))
		{
			nurseOrderConfirm.put(NurseOrderConfig.PATIENT_REMARK, m_patientRemark);
		}
		if (m_totalCharge != DEFAULT_VALUE)
		{
			nurseOrderConfirm.put(NurseOrderConfig.ORDER_TOTAL_CHARGE, String.valueOf(m_totalCharge));
		}
		if (!TextUtils.isEmpty(m_allDescription))
		{
			nurseOrderConfirm.put(NurseOrderConfig.SCHEDULE_ALL, m_allDescription);
		}
		if (!TextUtils.isEmpty(m_dayDescription))
		{
			nurseOrderConfirm.put(NurseOrderConfig.SCHEDULE_DAY, m_dayDescription);
		}
		if (!TextUtils.isEmpty(m_nightDescription))
		{
			nurseOrderConfirm.put(NurseOrderConfig.SCHEDULE_NIGHT, m_nightDescription);
		}

		return nurseOrderConfirm;
	}

}
