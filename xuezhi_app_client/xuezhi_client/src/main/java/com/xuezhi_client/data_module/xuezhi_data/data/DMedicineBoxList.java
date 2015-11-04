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
import com.xuezhi_client.net.config.MedicineBoxConfig;
import com.xuezhi_client.net.config.ProtocalConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class DMedicineBoxList
{
	private int                     m_Status       = ProtocalConfig.HTTP_OK;
	private ArrayList<DMedicineBox> m_medicineBoxs = new ArrayList<>();

	private final String NET_ERROR_JSON_SERILIZATION = DGlobal.GetInstance().getAppContext().getString(R.string
																											   .net_error_json_serilization);

	public DMedicineBoxList()
	{
	}

	public synchronized boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		//01. 清空原来容器
		if (m_medicineBoxs != null && m_medicineBoxs.size() != 0)
		{
			m_medicineBoxs.clear();
		}

		//02. http is ok
		m_Status = response.getInt(ProtocalConfig.HTTP_STATUS);

		if (!LogicalUtil.IsHttpSuccess(m_Status))
		{
			String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
			throw new JsonSerializationException(errorMsg);
		}

		//03. 序列化json
		JSONArray jsonArray = response.getJSONArray(MedicineBoxConfig.LIST);
		if (jsonArray == null)
		{
			throw new JsonSerializationException(NET_ERROR_JSON_SERILIZATION + ":" + MedicineBoxConfig.LIST);
		}

		JSONObject   jsonObject   = null;
		DMedicineBox medicalStock = null;
		for (int index = 0; index < jsonArray.length(); index++)
		{
			jsonObject = (JSONObject)jsonArray.get(index);
			medicalStock = new DMedicineBox();
			medicalStock.serialization(jsonObject);

			m_medicineBoxs.add(medicalStock);
		}
		return  true;

	}

	public synchronized int getStatus()
	{
		return m_Status;
	}

	public synchronized ArrayList<DMedicineBox> getMedicineBoxs()
	{
		return m_medicineBoxs;
	}

	public synchronized DMedicineBox getMedicineBoxByID(int id)
	{
		for (DMedicineBox medicalStock : m_medicineBoxs)
		{
			if (medicalStock.getID() == id)
			{
				return medicalStock;
			}
		}
		return null;
	}

	public synchronized DMedicineBox getMedicineBoxByMID(int mid)
	{
		for (DMedicineBox medicalStock : m_medicineBoxs)
		{
			if (medicalStock.getMID() == mid)
			{
				return medicalStock;
			}
		}
		return null;
	}

}
