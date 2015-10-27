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
 * 2015/9/28		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.data;

import com.module.data.DGlobal;
import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.util.logcal.LogicalUtil;
import com.xuezhi_client.net.config.MedicineConfig;
import com.xuezhi_client.net.config.ProtocalConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DMedicineList
{
	private int                  m_Status   = ProtocalConfig.HTTP_OK;
	private ArrayList<DMedicine> m_medicals = new ArrayList<>();

	private final String NET_ERROR_JSON_SERILIZATION = DGlobal.GetInstance().getAppContext().getString(R.string
																											   .net_error_json_serilization);

	public DMedicineList()
	{
	}

	public synchronized boolean serialization(JSONObject response) throws JSONException
	{
		//01. 清空原来容器
		if (m_medicals != null && m_medicals.size() != 0)
		{
			m_medicals.clear();
		}

		//02. http is ok
		m_Status = response.getInt(ProtocalConfig.HTTP_STATUS);

		if (!LogicalUtil.IsHttpSuccess(m_Status))
		{
			String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
			throw new JsonSerializationException(errorMsg);
		}

		//03. 序列化json
		JSONArray jsonArray = response.getJSONArray(MedicineConfig.LIST);
		if (jsonArray == null)
		{
			throw new JsonSerializationException(NET_ERROR_JSON_SERILIZATION + ":" + MedicineConfig.LIST);
		}

		JSONObject jsonObject = null;
		DMedicine  hospital   = null;
		for (int index = 0; index < jsonArray.length(); index++)
		{
			jsonObject = (JSONObject)jsonArray.get(index);
			hospital = new DMedicine();
			hospital.serialization(jsonObject);

			m_medicals.add(hospital);
		}
		return  true;

	}

	public synchronized int getStatus()
	{
		return m_Status;
	}

	public synchronized ArrayList<DMedicine> getMedicals()
	{
		return m_medicals;
	}

	public synchronized DMedicine getMedicineByID(int id)
	{
		for (DMedicine medical : m_medicals)
		{
			if (medical.getID() == id)
			{
				return medical;
			}
		}
		return null;
	}


}
