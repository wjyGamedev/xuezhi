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
import com.xuezhi_client.net.config.MedicalListConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DMedicalPrompt
{
	private int m_ID   = DataConfig.DEFAULT_VALUE;        //用药提醒ID
	private int m_RPID = DataConfig.DEFAULT_VALUE;    //药品库存ID

	//用药时间
	//TODO:目前是每天用药，HH:mm:ss
	private Calendar m_takeCalendar = null;	//服用时间
	private double   m_dose         = DataConfig.DEFAULT_VALUE;    //服用剂量
	private String m_mark = null;	//备注

	private SimpleDateFormat m_allSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		m_ID = response.getInt(MedicalListConfig.NAME);
		m_RPID = response.getInt(MedicalListConfig.NAME);

		String tmpTakeDate = response.getString(MedicalListConfig.NAME);
		Date takeDate       = m_allSDF.parse(tmpTakeDate);
		m_takeCalendar.setTime(takeDate);

		m_dose = response.getDouble(MedicalListConfig.NAME);
		m_mark = response.getString(MedicalListConfig.NAME);
		return true;
	}


	public int getID()
	{
		return m_ID;
	}

	public int getRPID()
	{
		return m_RPID;
	}

	public Calendar getTakeCalendar()
	{
		return m_takeCalendar;
	}

	public double getDose()
	{
		return m_dose;
	}

	public String getMark()
	{
		return m_mark;
	}
}
