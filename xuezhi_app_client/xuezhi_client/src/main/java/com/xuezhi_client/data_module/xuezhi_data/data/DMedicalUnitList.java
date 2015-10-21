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
 * 2015/9/30		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.data;

import com.module.data.DGlobal;
import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.util.logcal.LogicalUtil;
import com.xuezhi_client.net.config.MedicalUnitListConfig;
import com.xuezhi_client.net.config.ProtocalConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DMedicalUnitList
{
	private int                 m_Status   = ProtocalConfig.HTTP_OK;
	private ArrayList<DMedicalUnit> m_medicalUnits = new ArrayList<>();

	private final String NET_ERROR_JSON_SERILIZATION = DGlobal.GetInstance().getAppContext().getString(R.string
																											   .net_error_json_serilization);

	public DMedicalUnitList()
	{
	}

	public synchronized boolean serialization(JSONObject response) throws JSONException
	{
		//01. 清空原来容器
		if (m_medicalUnits != null && m_medicalUnits.size() != 0)
		{
			m_medicalUnits.clear();
		}

		//02. http is ok
		m_Status = response.getInt(ProtocalConfig.HTTP_STATUS);

		if (!LogicalUtil.IsHttpSuccess(m_Status))
		{
			String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
			throw new JsonSerializationException(errorMsg);
		}

		//03. 序列化json
		JSONArray jsonArray = response.getJSONArray(MedicalUnitListConfig.LIST);
		if (jsonArray == null)
		{
			throw new JsonSerializationException(NET_ERROR_JSON_SERILIZATION + ":" + MedicalUnitListConfig.LIST);
		}

		JSONObject jsonObject = null;
		DMedicalUnit medicalUnit = null;
		for (int index = 0; index < jsonArray.length(); index++)
		{
			jsonObject=(JSONObject)jsonArray.get(index);
			medicalUnit = new DMedicalUnit();
			medicalUnit.serialization(jsonObject);

			m_medicalUnits.add(medicalUnit);
		}
		return  true;

	}

	public synchronized int getStatus()
	{
		return m_Status;
	}

	public synchronized ArrayList<DMedicalUnit> getMedicalUnits()
	{
		return m_medicalUnits;
	}

	public synchronized DMedicalUnit getMedicalUnitByID(int id)
	{
		for (DMedicalUnit medicalUnit : m_medicalUnits)
		{
			if (medicalUnit.getID() == id)
			{
				return medicalUnit;
			}
		}
		return null;
	}
}
