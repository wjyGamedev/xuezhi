/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.appointment_nursing.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/16		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_list.msg_handler;


import android.text.TextUtils;

import com.module.event.EventID;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.net.config.NurseBasicListConfig;
import com.taixinkanghu_client.net.event.BaseNetEvent;

import java.util.HashMap;

public class RequestNurseBasicListEvent extends BaseNetEvent
{
	private String m_name      = null;
	private String m_mobileNum = null;
	private int    m_genderID  = DataConfig.DEFAULT_VALUE;
	private String m_ageRage = null;
	private String m_weightRage = null;
	private int m_hospitalID = DataConfig.DEFAULT_VALUE;
	private int m_departmentID = DataConfig.DEFAULT_VALUE;
	private int m_patientStateID = DataConfig.DEFAULT_VALUE;
	private String m_schedualAll = null;
	private String m_schedualDay = null;
	private String m_schedualNight = null;

	public RequestNurseBasicListEvent()
	{
		super(EventID.QUEST_APPPINTMENT_NURSING);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> dataHashMap = new HashMap<String, String>();

		//选填项
		if (!TextUtils.isEmpty(m_name))
			dataHashMap.put(NurseBasicListConfig.NAME, m_name);

		if (!TextUtils.isEmpty(m_mobileNum))
			dataHashMap.put(NurseBasicListConfig.PHONE_NUM, m_mobileNum);

		if (m_genderID != DataConfig.DEFAULT_VALUE)
			dataHashMap.put(NurseBasicListConfig.SEX_ID, String.valueOf(m_genderID));

		if (!TextUtils.isEmpty(m_ageRage))
			dataHashMap.put(NurseBasicListConfig.AGE, m_ageRage);

		if (!TextUtils.isEmpty(m_weightRage))
			dataHashMap.put(NurseBasicListConfig.WEIGHT, m_weightRage);

		//必填项
		dataHashMap.put(NurseBasicListConfig.HOSPITAL_ID, String.valueOf(m_hospitalID));
		dataHashMap.put(NurseBasicListConfig.DEPARTMENT_NAME, String.valueOf(m_departmentID));
		dataHashMap.put(NurseBasicListConfig.PATIENT_STATE_ID, String.valueOf(m_patientStateID));

		//下面三个中至少有一个
		if (!TextUtils.isEmpty(m_schedualAll))
			dataHashMap.put(NurseBasicListConfig.SCHEDULE_ALL, m_schedualAll);

		if (!TextUtils.isEmpty(m_schedualDay))
			dataHashMap.put(NurseBasicListConfig.SCHEDULE_DAY, m_schedualDay);

		if (!TextUtils.isEmpty(m_schedualNight))
			dataHashMap.put(NurseBasicListConfig.SCHEDULE_NIGHT, m_schedualNight);

		//过滤条件
		dataHashMap.put(NurseBasicListConfig.STRICT, String.valueOf(0));
		return dataHashMap;

	}

	public void setName(String name)
	{
		m_name = name;
	}

	public void setMobileNum(String mobileNum)
	{
		m_mobileNum = mobileNum;
	}

	public void setGenderID(int genderID)
	{
		m_genderID = genderID;
	}

	public void setAgeRage(String ageRage)
	{
		m_ageRage = ageRage;
	}


	public void setWeightRage(String weightRage)
	{
		m_weightRage = weightRage;
	}

	public void setHospitalID(int hospitalID)
	{
		m_hospitalID = hospitalID;
	}

	public void setDepartmentID(int departmentID)
	{
		m_departmentID = departmentID;
	}

	public void setPatientStateID(int patientStateID)
	{
		m_patientStateID = patientStateID;
	}

	public void setSchedualAll(String schedualAll)
	{
		m_schedualAll = schedualAll;
	}

	public void setSchedualDay(String schedualDay)
	{
		m_schedualDay = schedualDay;
	}

	public void setSchedualNight(String schedualNight)
	{
		m_schedualNight = schedualNight;
	}
}
