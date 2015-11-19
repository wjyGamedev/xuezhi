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
 * 2015/11/5		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.data;

import com.module.data.DGlobal;
import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.xuezhi_client.net.config.NoTakeMedicineConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class DNoTakeMedicinePerDay
{
	private       ArrayList<DNoTakeMedicine> mNoTakeMedicines            = new ArrayList<>();
	private       Calendar                   m_selectedPerDay            = Calendar.getInstance();
	private final String                     NET_ERROR_JSON_SERILIZATION = DGlobal.GetInstance().getAppContext().getString(R.string
																																   .net_error_json_serilization);

	public synchronized boolean serialization(JSONObject response, Calendar selectedPerDay) throws JSONException, ParseException
	{
		mNoTakeMedicines.clear();

		m_selectedPerDay = selectedPerDay;

		JSONArray jsonArray = response.getJSONArray(NoTakeMedicineConfig.NO_TAKE_LIST);
		if (jsonArray == null)
		{
			throw new JsonSerializationException(NET_ERROR_JSON_SERILIZATION + ":" + NoTakeMedicineConfig.NO_TAKE_LIST);
		}

		JSONObject    jsonObject   = null;
		DNoTakeMedicine noTakeMedicine = null;
		for (int index = 0; index < jsonArray.length(); index++)
		{
			jsonObject = (JSONObject)jsonArray.get(index);
			noTakeMedicine = new DNoTakeMedicine();
			noTakeMedicine.serialization(jsonObject);

			mNoTakeMedicines.add(noTakeMedicine);
		}
		return true;

	}

	public ArrayList<DNoTakeMedicine> getNoTakeMedicines()
	{
		return mNoTakeMedicines;
	}

	public Calendar getSelectedPerDay()
	{
		return m_selectedPerDay;
	}
}
