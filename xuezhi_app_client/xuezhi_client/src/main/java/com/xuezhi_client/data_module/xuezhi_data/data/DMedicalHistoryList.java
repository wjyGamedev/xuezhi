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
import com.module.util.logcal.LogicalUtil;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.net.config.MedicalListConfig;
import com.xuezhi_client.net.config.ProtocalConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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
		String tmpCurrDate = response.getString(MedicalListConfig.NAME);
		Date   currDate    = m_yearMonthDaySDF.parse(tmpCurrDate);
		Calendar currCalendar = Calendar.getInstance();
		currCalendar.setTime(currDate);

		//04. 添加/更新
		DMedicalHistoryListPerMonth medicalHistoryListPerMonth = null;
		//更新
		if (m_medicalHistoryHashMap.containsKey(currCalendar))
		{
			medicalHistoryListPerMonth = m_medicalHistoryHashMap.get(currCalendar);
		}
		//add new
		else
		{
			medicalHistoryListPerMonth = new DMedicalHistoryListPerMonth();
			m_medicalHistoryHashMap.put(currCalendar, medicalHistoryListPerMonth);
		}

		medicalHistoryListPerMonth.serialization(response);
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
