/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.data;

import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.net.config.MedicinePromptConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DMedicinePrompt
{
	private int m_ID   = DataConfig.DEFAULT_VALUE;        //用药提醒ID
	private int m_UID = DataConfig.DEFAULT_VALUE;        //用户ID
	private int m_MID = DataConfig.DEFAULT_VALUE;
	private double   m_dose         = DataConfig.DEFAULT_VALUE;    //服用剂量
	//TODO:目前是每天用药，HH:mm:ss
	private Calendar m_takeCalendar = Calendar.getInstance();	//服用时间
	private boolean m_valid = true;
	private Calendar m_addCalendar = Calendar.getInstance();	//添加提醒时间
	private String m_precaution = null;	//注意事项

	private SimpleDateFormat m_hmsSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE_SECOND);
	private SimpleDateFormat m_allSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		m_ID = response.getInt(MedicinePromptConfig.ID);
		m_UID = response.getInt(MedicinePromptConfig.UID);
		m_MID = response.getInt(MedicinePromptConfig.MID);
		m_dose = response.getDouble(MedicinePromptConfig.DOSE);

		String tmpTakeDate = response.getString(MedicinePromptConfig.TIME);
		Date takeDate       = m_hmsSDF.parse(tmpTakeDate);
		m_takeCalendar.setTime(takeDate);

		int tmpValid = response.getInt(MedicinePromptConfig.VALID);
		if (tmpValid == 1)
		{
			m_valid = true;
		}
		else
		{
			m_valid = false;
		}

		String tmpAddTime = response.getString(MedicinePromptConfig.ADDTIME);
		Date addDate       = m_allSDF.parse(tmpAddTime);
		m_addCalendar.setTime(addDate);

		m_precaution = response.getString(MedicinePromptConfig.PRECAUTION);

		return true;
	}

	public int getID()
	{
		return m_ID;
	}

	public int getUID()
	{
		return m_UID;
	}

	public int getMID()
	{
		return m_MID;
	}

	public double getDose()
	{
		return m_dose;
	}

	public Calendar getTakeCalendar()
	{
		return m_takeCalendar;
	}

	public boolean isValid()
	{
		return m_valid;
	}

	public Calendar getAddCalendar()
	{
		return m_addCalendar;
	}

	public String getPrecaution()
	{
		return m_precaution;
	}
}
