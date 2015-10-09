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

import com.module.data.DGlobal;
import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.net.config.MedicalHistoryListConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DMedicalHistoryListPerMonth
{
	private Calendar                   m_currCalendar            = null;
	private ArrayList<DMedicalHistory> m_medicalHistoryPerMonths = new ArrayList<>();

	private SimpleDateFormat m_yearMonthDaySDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	private final String NET_ERROR_JSON_SERILIZATION = DGlobal.GetInstance().getAppContext().getString(R.string
																											   .net_error_json_serilization);

	public DMedicalHistoryListPerMonth()
	{
	}

	public synchronized boolean serialization(JSONObject response, Calendar month) throws JSONException, ParseException
	{
		//01. 清空原来容器
		clearup();

		//02. 当前月份
		m_currCalendar = Calendar.getInstance();
		m_currCalendar.setTime(month.getTime());

		//03. 序列化json
		JSONArray jsonArray = response.getJSONArray(MedicalHistoryListConfig.TAKE_LIST);
		if (jsonArray == null)
		{
			throw new JsonSerializationException(NET_ERROR_JSON_SERILIZATION + ":" + MedicalHistoryListConfig.TAKE_LIST);
		}

		JSONObject      jsonObject             = null;
		DMedicalHistory medicalHistoryPerMonth = null;
		for (int index = 0; index < jsonArray.length(); index++)
		{
			jsonObject = (JSONObject)jsonArray.get(index);
			medicalHistoryPerMonth = new DMedicalHistory();
			medicalHistoryPerMonth.serialization(jsonObject);

			m_medicalHistoryPerMonths.add(medicalHistoryPerMonth);
		}
		return true;

	}

	private void clearup()
	{
		m_currCalendar = null;
		m_medicalHistoryPerMonths.clear();
	}

	public Calendar getCurrCalendar()
	{
		return m_currCalendar;
	}

	public ArrayList<DMedicalHistory> getMedicalHistoryPerMonths()
	{
		return m_medicalHistoryPerMonths;
	}
}
