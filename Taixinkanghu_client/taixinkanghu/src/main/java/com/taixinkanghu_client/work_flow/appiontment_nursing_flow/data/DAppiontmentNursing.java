/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/8		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.data;

import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.EnumConfig;

public class DAppiontmentNursing
{
	private static DAppiontmentNursing s_appiontmentNursing = new DAppiontmentNursing();

	//姓名
	private String m_name     = null;
	private Object m_syncName = new Object();

	//手机号码：
	private String m_phone     = null;
	private Object m_syncPhone = new Object();

	//性别：男，女
	private EnumConfig.GenderStatus m_genderStatus = null;
	private Object                  m_syncGender   = new Object();

	//年龄
	private EnumConfig.AgeRage m_ageRage     = null;
	private Object             m_syncAgeRage = new Object();

	//体重
	private EnumConfig.WeightRage m_weightRage     = null;
	private Object                m_syncWeightRage = new Object();

	//医院
	private int    m_hospitalID     = DataConfig.DEFAULT_VALUE;
	private Object m_syncHospitalID = new Object();

	//科室
	private int    m_departmenetID     = DataConfig.DEFAULT_VALUE;
	private Object m_syncDepartmenetID = new Object();

	//病人状态
	private EnumConfig.PatientState m_patientState     = null;
	private Object                  m_syncPatientState = new Object();

	//护理时间
	private DNursingDate m_nursingDate     = null;
	private Object       m_syncNursingDate = new Object();


	private DAppiontmentNursing()
	{}

	public static DAppiontmentNursing GetInstance()
	{
		return s_appiontmentNursing;
	}

	public void clearup()
	{
		synchronized (m_syncName)
		{
			m_name = null;
		}

		synchronized (m_syncPhone)
		{
			m_phone = null;
		}

		synchronized (m_syncGender)
		{
			m_genderStatus = null;
		}

		synchronized (m_syncAgeRage)
		{
			m_ageRage = null;
		}

		synchronized (m_syncWeightRage)
		{
			m_weightRage = null;
		}

		synchronized (m_syncHospitalID)
		{
			m_hospitalID = DataConfig.DEFAULT_VALUE;
		}

		synchronized (m_syncDepartmenetID)
		{
			m_departmenetID = DataConfig.DEFAULT_VALUE;
		}

		synchronized (m_syncPatientState)
		{
			m_patientState = null;
		}

		synchronized (m_syncNursingDate)
		{
			m_nursingDate = null;
		}
	}

	//function:get/set
	public String getName()
	{
		synchronized (m_syncName)
		{
			return m_name;
		}
	}

	public void setName(String name)
	{
		synchronized (m_syncName)
		{
			m_name = name;
		}
	}

	public String getPhone()
	{
		synchronized (m_syncPhone)
		{
			return m_phone;
		}
	}

	public void setPhone(String phone)
	{
		synchronized (m_syncPhone)
		{
			m_phone = phone;
		}
	}

	public EnumConfig.GenderStatus getGenderStatus()
	{
		synchronized (m_syncGender)
		{
			return m_genderStatus;
		}
	}

	public void setGenderStatus(EnumConfig.GenderStatus genderStatus)
	{
		synchronized (m_syncGender)
		{
			m_genderStatus = genderStatus;
		}
	}

	public EnumConfig.AgeRage getAgeRage()
	{
		synchronized (m_syncAgeRage)
		{
			return m_ageRage;
		}
	}

	public void setAgeRage(EnumConfig.AgeRage ageRage)
	{
		synchronized (m_syncAgeRage)
		{
			m_ageRage = ageRage;
		}
	}

	public EnumConfig.WeightRage getWeightRage()
	{
		synchronized (m_syncWeightRage)
		{
			return m_weightRage;
		}
	}

	public void setWeightRage(EnumConfig.WeightRage weightRage)
	{
		synchronized (m_syncWeightRage)
		{
			m_weightRage = weightRage;
		}
	}

	public int getHospitalID()
	{
		synchronized (m_syncHospitalID)
		{
			return m_hospitalID;
		}
	}

	public void setHospitalID(int hospitalID)
	{
		synchronized (m_syncHospitalID)
		{
			m_hospitalID = hospitalID;
		}
	}

	public int getDepartmenetID()
	{
		synchronized (m_syncDepartmenetID)
		{
			return m_departmenetID;
		}
	}

	public void setDepartmenetID(int departmenetID)
	{
		synchronized (m_syncDepartmenetID)
		{
			m_departmenetID = departmenetID;
		}
	}

	public EnumConfig.PatientState getPatientState()
	{
		synchronized (m_syncPatientState)
		{
			return m_patientState;
		}
	}

	public void setPatientState(EnumConfig.PatientState patientState)
	{
		synchronized (m_syncPatientState)
		{
			m_patientState = patientState;
		}
	}

	public DNursingDate getNursingDate()
	{
		synchronized (m_syncNursingDate)
		{
			return m_nursingDate;
		}
	}

	public void setNursingDate(DNursingDate nursingDate)
	{
		synchronized (m_syncNursingDate)
		{
			m_nursingDate = nursingDate;
		}
	}


}
