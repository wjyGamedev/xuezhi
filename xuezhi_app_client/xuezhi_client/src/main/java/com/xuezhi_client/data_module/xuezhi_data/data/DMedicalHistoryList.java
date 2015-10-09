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

import com.module.data.DGlobal;
import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.util.logcal.LogicalUtil;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.net.config.MedicalHistoryListConfig;
import com.xuezhi_client.net.config.ProtocalConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DMedicalHistoryList
{
	private int                                            m_Status                = ProtocalConfig.HTTP_OK;
	private HashMap<Calendar, DMedicalHistoryListPerMonth> m_medicalHistoryHashMap = new HashMap<>();

	private SimpleDateFormat m_yearMonthDaySDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	private final String NET_ERROR_JSON_SERILIZATION = DGlobal.GetInstance().getAppContext().getString(R.string
																											   .net_error_json_serilization);

	public DMedicalHistoryList()
	{
	}

	public synchronized boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		//01. 不需要清空容器。

		//02. http is ok
		m_Status = response.getInt(ProtocalConfig.HTTP_STATUS);

		if (!LogicalUtil.IsHttpSuccess(m_Status))
		{
			String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
			throw new JsonSerializationException(errorMsg);
		}

		//03. 当前月份
		JSONArray jsonArray   = response.getJSONArray(MedicalHistoryListConfig.LIST);
		for (int index = 0; index < jsonArray.length(); index++)
		{
			JSONObject jsonObject = (JSONObject)jsonArray.get(index);
			String     dateDisplay  = jsonObject.getString(MedicalHistoryListConfig.DATE);
			Date       currDate     = m_yearMonthDaySDF.parse(dateDisplay);
			Calendar   currCalendar = Calendar.getInstance();
			currCalendar.setTime(currDate);
			int currYear = currCalendar.get(Calendar.YEAR);
			int currMonth = currCalendar.get(Calendar.MONTH);
			int currDay = currCalendar.get(Calendar.DAY_OF_MONTH);

			//04. 添加/更新
			DMedicalHistoryListPerMonth medicalHistoryListPerMonth = null;
			//更新
			Iterator<Map.Entry<Calendar, DMedicalHistoryListPerMonth>> iterator = m_medicalHistoryHashMap.entrySet().iterator();
			int year = 0;
			int month = 0;
			int day = 0;
			boolean findFlag = false;
			while (iterator.hasNext())
			{
				Map.Entry<Calendar, DMedicalHistoryListPerMonth> entry = iterator.next();
				Calendar tmpCalendar = entry.getKey();
				year = tmpCalendar.get(Calendar.YEAR);
				month = tmpCalendar.get(Calendar.MONTH);
				day = tmpCalendar.get(Calendar.DAY_OF_MONTH);

				if (year == currYear && month == currMonth && day == currDay)
				{
					findFlag = true;
					medicalHistoryListPerMonth = entry.getValue();
				}
			}
			//add new
			if (!findFlag)
			{
				medicalHistoryListPerMonth = new DMedicalHistoryListPerMonth();
				m_medicalHistoryHashMap.put(currCalendar, medicalHistoryListPerMonth);
			}

			//		if (m_medicalHistoryHashMap.containsKey(currCalendar))
			//		{
			//			medicalHistoryListPerMonth = m_medicalHistoryHashMap.get(currCalendar);
			//		}
			//		//add new
			//		else
			//		{
			//			medicalHistoryListPerMonth = new DMedicalHistoryListPerMonth();
			//			m_medicalHistoryHashMap.put(currCalendar, medicalHistoryListPerMonth);
			//		}

			medicalHistoryListPerMonth.serialization(jsonObject, currCalendar);
		}


		return true;

	}

	public HashMap<Calendar, DMedicalHistoryListPerMonth> getMedicalHistoryHashMap()
	{
		return m_medicalHistoryHashMap;
	}

	public synchronized DMedicalHistoryListPerMonth getMedicalHistoryByMonth(Calendar month)
	{
		if (m_medicalHistoryHashMap.containsKey(month))
		{
			return m_medicalHistoryHashMap.get(month);
		}
		return null;
	}

}
