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
import com.xuezhi_client.net.config.MedicalListConfig;
import com.xuezhi_client.net.config.ProtocalConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class DMedicalPromptList
{
	private int                 m_Status   = ProtocalConfig.HTTP_OK;
	private ArrayList<DMedicalPrompt> m_medicalPrompts = new ArrayList<>();

	private final String NET_ERROR_JSON_SERILIZATION = DGlobal.GetInstance().getAppContext().getString(R.string
																											   .net_error_json_serilization);

	public DMedicalPromptList()
	{
	}

	public synchronized boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		//01. 清空原来容器
		if (m_medicalPrompts != null && m_medicalPrompts.size() != 0)
		{
			m_medicalPrompts.clear();
		}

		//02. http is ok
		m_Status = response.getInt(ProtocalConfig.HTTP_STATUS);

		if (!LogicalUtil.IsHttpSuccess(m_Status))
		{
			String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
			throw new JsonSerializationException(errorMsg);
		}

		//03. 序列化json
		JSONArray jsonArray = response.getJSONArray(MedicalListConfig.LIST);
		if (jsonArray == null)
		{
			throw new JsonSerializationException(NET_ERROR_JSON_SERILIZATION + ":" + MedicalListConfig.LIST);
		}

		JSONObject jsonObject = null;
		DMedicalPrompt medicalPrompt = null;
		for (int index = 0; index < jsonArray.length(); index++)
		{
			jsonObject=(JSONObject)jsonArray.get(index);
			medicalPrompt = new DMedicalPrompt();
			medicalPrompt.serialization(jsonObject);

			m_medicalPrompts.add(medicalPrompt);
		}
		return  true;

	}

	public synchronized int getStatus()
	{
		return m_Status;
	}

	public synchronized ArrayList<DMedicalPrompt> getMedicalPrompts()
	{
		return m_medicalPrompts;
	}

	public synchronized DMedicalPrompt getMedicalPromptByID(int id)
	{
		for (DMedicalPrompt medicalPrompt : m_medicalPrompts)
		{
			if (medicalPrompt.getID() == id)
			{
				return medicalPrompt;
			}
		}
		return null;
	}
}
