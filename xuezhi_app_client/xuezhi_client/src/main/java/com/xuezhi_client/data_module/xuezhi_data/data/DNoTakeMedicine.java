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
import com.xuezhi_client.net.config.NoTakeMedicineConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DNoTakeMedicine implements Serializable
{
	private int      mID           = DataConfig.DEFAULT_VALUE;
	private int      mMID          = DataConfig.DEFAULT_VALUE;    //药品ID
	private int mPID = DataConfig.DEFAULT_VALUE;    //药品提醒ID
	private Calendar mTakeCalendar = Calendar.getInstance();    //服药时间

	private SimpleDateFormat m_allSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		mID = response.getInt(NoTakeMedicineConfig.ID);
		mMID = response.getInt(NoTakeMedicineConfig.MID);
		mPID = response.getInt(NoTakeMedicineConfig.PID);
		String tmpTakeDate = response.getString(NoTakeMedicineConfig.NO_TAKE_DATE);
		Date   takeDate       = m_allSDF.parse(tmpTakeDate);
		mTakeCalendar.setTime(takeDate);

		return true;
	}

	public int getID()
	{
		return mID;
	}

	public int getMID()
	{
		return mMID;
	}

	public int getPID()
	{
		return mPID;
	}

	public Calendar getTakeCalendar()
	{
		return mTakeCalendar;
	}
}
