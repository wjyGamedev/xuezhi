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
import com.xuezhi_client.net.config.TakeMedicineConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DTakeMedicine
{
	private int      m_ID           = DataConfig.DEFAULT_VALUE;
	private int      m_UID           = DataConfig.DEFAULT_VALUE;
	private int      m_MID          = DataConfig.DEFAULT_VALUE;    //药品ID
	private Calendar m_takeCalendar = Calendar.getInstance();    //服药时间
	private double   m_dose         = DataConfig.DEFAULT_VALUE;    //服用剂量

	private SimpleDateFormat m_allSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		m_ID = response.getInt(TakeMedicineConfig.ID);
		m_UID = response.getInt(TakeMedicineConfig.UID);
		m_MID = response.getInt(TakeMedicineConfig.MID);

		String tmpTakeDate = response.getString(TakeMedicineConfig.TAKETIME);
		Date   takeDate       = m_allSDF.parse(tmpTakeDate);
		m_takeCalendar.setTime(takeDate);

		m_dose = response.getDouble(TakeMedicineConfig.DOSE);
		return true;
	}


	public int getID()
	{
		return m_ID;
	}

	public int getMID()
	{
		return m_MID;
	}

	public Calendar getTakeCalendar()
	{
		return m_takeCalendar;
	}

	public double getDose()
	{
		return m_dose;
	}
}
