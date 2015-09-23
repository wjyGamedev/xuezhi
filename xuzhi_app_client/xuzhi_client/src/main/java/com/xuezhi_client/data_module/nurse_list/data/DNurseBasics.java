/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/18		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_list.data;


import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.nurse_list.util.NurseUtil;
import com.taixinkanghu_client.net.config.NurseBasicListConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DNurseBasics implements Serializable
{
	/**
	 * 数据区
	 */
	private int    m_ID           = DataConfig.DEFAULT_VALUE;          //ID
	private int    m_hospitalID   = 0;             //hospital ID
	private String m_name         = null;      //姓名
	private String m_gender       = null;      //性别
	private int    m_starLevel    = 0;            //星级
	private int    m_age          = 0;         //年龄
	private String m_homeTown     = null;    //籍贯
	private String m_nursingExp   = null;        //护理经验
	private String m_nursingLevel = null;    //护理级别

	private int m_serviceChargePerAllCare      = 0;            //24小时，可自理
	private int m_serviceChargePerAllHalfCare  = 0;        //24小时，半自理
	private int m_serviceChargePerAllCanntCare = 0;        //24小时，不可自理

	private int m_serviceChargePerDayCare      = 0;            //12白，可自理
	private int m_serviceChargePerDayHalfCare  = 0;        //12白，半自理
	private int m_serviceChargePerDayCanntCare = 0;        //12白，不可自理

	private int m_serviceChargePerNightCare      = 0;            //12黑，可自理
	private int m_serviceChargePerNightHalfCare  = 0;        //12黑，半自理
	private int m_serviceChargePerNightCanntCare = 0;    //黑，不可自理

	private String m_serviceStatus = null;    //服务状态


	public boolean serialization(JSONObject response) throws JSONException
	{
		m_ID = response.getInt(NurseBasicListConfig.ID);
		m_hospitalID = response.getInt(NurseBasicListConfig.HOSPITAL_ID);
		m_name = response.getString(NurseBasicListConfig.NAME);
		int                     genderType   = response.getInt(NurseBasicListConfig.GENDER);
		EnumConfig.GenderStatus genderStatus = EnumConfig.GenderStatus.valueOf(genderType);
		if (genderStatus != null)
		{
			m_gender = genderStatus.getName();
		}

		m_starLevel = response.getInt(NurseBasicListConfig.STAR_LEVEL);
		m_age = response.getInt(NurseBasicListConfig.AGE);
		m_homeTown = response.getString(NurseBasicListConfig.HOMETOWN);

		int itmp = response.getInt(NurseBasicListConfig.NURING_EXP);
		m_nursingExp = NurseUtil.GetServiceExpByInteger(itmp);

		itmp = response.getInt(NurseBasicListConfig.NURING_LEVEL);
		m_nursingLevel = NurseUtil.GetNursingLevelByInteger(itmp);

		m_serviceChargePerAllCare = response.getInt(NurseBasicListConfig.SERVICE_CHARGE_PER_ALL_CARE);
		m_serviceChargePerAllHalfCare = response.getInt(NurseBasicListConfig.SERVICE_CHARGE_PER_ALL_HALF_CARE);
		m_serviceChargePerAllCanntCare = response.getInt(NurseBasicListConfig.SERVICE_CHARGE_PER_ALL_CANNT_CARE);

		m_serviceChargePerDayCare = response.getInt(NurseBasicListConfig.SERVICE_CHARGE_PER_DAY_CARE);
		m_serviceChargePerDayHalfCare = response.getInt(NurseBasicListConfig.SERVICE_CHARGE_PER_DAY_HALF_CARE);
		m_serviceChargePerDayCanntCare = response.getInt(NurseBasicListConfig.SERVICE_CHARGE_PER_DAY_CANNT_CARE);

		m_serviceChargePerNightCare = response.getInt(NurseBasicListConfig.SERVICE_CHARGE_PER_NIGHT_CARE);
		m_serviceChargePerNightHalfCare = response.getInt(NurseBasicListConfig.SERVICE_CHARGE_PER_NIGHTL_HALF_CARE);
		m_serviceChargePerNightCanntCare = response.getInt(NurseBasicListConfig.SERVICE_CHARGE_PER_NIGHT_CANNT_CARE);

		itmp = response.getInt(NurseBasicListConfig.SERVICE_STATUS);
		EnumConfig.NurseServiceStatus nurseServiceStatus = EnumConfig.NurseServiceStatus.valueOf(itmp);
		if (nurseServiceStatus != null)
		{
			m_serviceStatus = nurseServiceStatus.getName();
		}

		//同步到全局容器中
		DNurseList.GetInstance().syncNurseBasics(m_ID, this);

		return true;
	}

	public void copyfrom(DNurseBasics nurseBasics) throws JSONException
	{
		if (nurseBasics == null)
		{
			throw new JSONException("nurseBasics == null");
		}

		m_ID = nurseBasics.getID();
		m_hospitalID = nurseBasics.getHospitalID();
		m_name = nurseBasics.getName();
		m_gender = nurseBasics.getGender();
		m_starLevel = nurseBasics.getStarLevel();
		m_age = nurseBasics.getAge();
		m_homeTown = nurseBasics.getHomeTown();
		m_nursingExp = nurseBasics.getNursingExp();
		m_nursingLevel = nurseBasics.getNursingLevel();
		m_serviceChargePerAllCare = nurseBasics.getServiceChargePerAllCare();
		m_serviceChargePerAllHalfCare = nurseBasics.getServiceChargePerAllHalfCare();
		m_serviceChargePerAllCanntCare = nurseBasics.getServiceChargePerAllCanntCare();
		m_serviceChargePerDayCare = nurseBasics.getServiceChargePerDayCare();
		m_serviceChargePerDayHalfCare = nurseBasics.getServiceChargePerDayHalfCare();
		m_serviceChargePerDayCanntCare = nurseBasics.getServiceChargePerDayCanntCare();
		m_serviceChargePerNightCare = nurseBasics.getServiceChargePerNightCare();
		m_serviceChargePerNightHalfCare = nurseBasics.getServiceChargePerNightHalfCare();
		m_serviceChargePerNightCanntCare = nurseBasics.getServiceChargePerNightCanntCare();

		m_serviceStatus = nurseBasics.getServiceStatus();
	}


	public int getID()
	{
		return m_ID;
	}

	public int getHospitalID()
	{
		return m_hospitalID;
	}

	public String getName()
	{
		return m_name;
	}

	public String getGender()
	{
		return m_gender;
	}

	public int getStarLevel()
	{
		return m_starLevel;
	}

	public int getAge()
	{
		return m_age;
	}

	public String getHomeTown()
	{
		return m_homeTown;
	}

	public String getNursingExp()
	{
		return m_nursingExp;
	}

	public String getNursingLevel()
	{
		return m_nursingLevel;
	}

	public int getServiceChargePerAllCare()
	{
		return m_serviceChargePerAllCare;
	}

	public int getServiceChargePerAllHalfCare()
	{
		return m_serviceChargePerAllHalfCare;
	}

	public int getServiceChargePerAllCanntCare()
	{
		return m_serviceChargePerAllCanntCare;
	}

	public int getServiceChargePerDayCare()
	{
		return m_serviceChargePerDayCare;
	}

	public int getServiceChargePerDayHalfCare()
	{
		return m_serviceChargePerDayHalfCare;
	}

	public int getServiceChargePerDayCanntCare()
	{
		return m_serviceChargePerDayCanntCare;
	}

	public int getServiceChargePerNightCare()
	{
		return m_serviceChargePerNightCare;
	}

	public int getServiceChargePerNightHalfCare()
	{
		return m_serviceChargePerNightHalfCare;
	}

	public int getServiceChargePerNightCanntCare()
	{
		return m_serviceChargePerNightCanntCare;
	}

	public int getServiceCharge(int dayType, EnumConfig.PatientState patientState)
	{
		if (dayType < DataConfig.SELECT_DAY_TYEP_ALL || dayType > DataConfig.SELECT_DAY_TYEP_NIGHT)
		{
			TipsDialog.GetInstance().setMsg("dayType is invalid![dayType:=" + dayType + "]");
			TipsDialog.GetInstance().show();
			return 0;
		}

		if (dayType == DataConfig.SELECT_DAY_TYEP_ALL)
		{
			switch (patientState)
			{
			case PATIENT_STATE_CARE_MYSELF:
			{
				return getServiceChargePerAllCare();
			}
			case PATIENT_STATE_HALF_CARE_MYSELF:
			{
				return getServiceChargePerAllHalfCare();
			}
			case PATIENT_STATE_CANNT_CARE_MYSELF:
			{
				return getServiceChargePerAllCanntCare();
			}
			default:
			{
				TipsDialog.GetInstance().setMsg("patientState is invalid!" + patientState.toString());
				TipsDialog.GetInstance().show();
				return 0;
			}
			}
		}
		else if (dayType == DataConfig.SELECT_DAY_TYEP_DAY)
		{
			switch (patientState)
			{
			case PATIENT_STATE_CARE_MYSELF:
			{
				return getServiceChargePerDayCare();
			}
			case PATIENT_STATE_HALF_CARE_MYSELF:
			{
				return getServiceChargePerDayHalfCare();
			}
			case PATIENT_STATE_CANNT_CARE_MYSELF:
			{
				return getServiceChargePerDayCanntCare();
			}
			default:
			{
				TipsDialog.GetInstance().setMsg("patientState is invalid!" + patientState.toString());
				TipsDialog.GetInstance().show();
				return 0;
			}
			}
		}
		else
		{
			switch (patientState)
			{
			case PATIENT_STATE_CARE_MYSELF:
			{
				return getServiceChargePerNightCare();
			}
			case PATIENT_STATE_HALF_CARE_MYSELF:
			{
				return getServiceChargePerNightHalfCare();
			}
			case PATIENT_STATE_CANNT_CARE_MYSELF:
			{
				return getServiceChargePerNightCanntCare();
			}
			default:
			{
				TipsDialog.GetInstance().setMsg("patientState is invalid!" + patientState.toString());
				TipsDialog.GetInstance().show();
				return 0;
			}
			}
		}

	}


	public String getServiceStatus()
	{
		return m_serviceStatus;
	}


}
