/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.data;

import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.net.config.AssayDetectionInfoConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DAssayDetection
{

	private int m_ID = DataConfig.DEFAULT_VALUE;
	private Calendar m_recordCalendar = null;
	private double   m_tgValue        = DataConfig.DEFAULT_VALUE;
	private double   m_tchoValue      = DataConfig.DEFAULT_VALUE;
	private double   m_lolcValue      = DataConfig.DEFAULT_VALUE;
	private double   m_hdlcValue      = DataConfig.DEFAULT_VALUE;

	private double m_atlValue   = DataConfig.DEFAULT_VALUE;
	private double m_astValue   = DataConfig.DEFAULT_VALUE;
	private double m_ckValue    = DataConfig.DEFAULT_VALUE;
	private double m_gluValue   = DataConfig.DEFAULT_VALUE;
	private double m_hba1cValue = DataConfig.DEFAULT_VALUE;
	private double m_scrValue = DataConfig.DEFAULT_VALUE;

	private SimpleDateFormat m_allSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);


	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		m_ID = response.getInt(AssayDetectionInfoConfig.TG);

		String tmpAddDate = response.getString(AssayDetectionInfoConfig.RECORD_DATE);
		Date   date       = m_allSDF.parse(tmpAddDate);
		m_recordCalendar.setTime(date);

		m_tgValue = response.getDouble(AssayDetectionInfoConfig.TG);
		m_tchoValue = response.getDouble(AssayDetectionInfoConfig.TCHO);
		m_lolcValue = response.getDouble(AssayDetectionInfoConfig.LOLC);
		m_hdlcValue = response.getDouble(AssayDetectionInfoConfig.HDLC);

		m_atlValue = response.getDouble(AssayDetectionInfoConfig.ALT);
		m_astValue = response.getDouble(AssayDetectionInfoConfig.AST);
		m_ckValue = response.getDouble(AssayDetectionInfoConfig.CK);
		m_gluValue = response.getDouble(AssayDetectionInfoConfig.GLU);
		m_hba1cValue = response.getDouble(AssayDetectionInfoConfig.HBA1C);
		m_scrValue = response.getDouble(AssayDetectionInfoConfig.SCR);
		return true;
	}

	public int getID()
	{
		return m_ID;
	}

	public Calendar getRecordCalendar()
	{
		return m_recordCalendar;
	}

	public double getTgValue()
	{
		return m_tgValue;
	}

	public double getTchoValue()
	{
		return m_tchoValue;
	}

	public double getLolcValue()
	{
		return m_lolcValue;
	}

	public double getHdlcValue()
	{
		return m_hdlcValue;
	}

	public double getAtlValue()
	{
		return m_atlValue;
	}

	public double getAstValue()
	{
		return m_astValue;
	}

	public double getCkValue()
	{
		return m_ckValue;
	}

	public double getGluValue()
	{
		return m_gluValue;
	}

	public double getHba1cValue()
	{
		return m_hba1cValue;
	}

	public double getScrValue()
	{
		return m_scrValue;
	}
}
