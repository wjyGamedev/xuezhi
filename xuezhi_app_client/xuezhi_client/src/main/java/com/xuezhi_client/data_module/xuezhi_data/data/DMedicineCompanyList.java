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
 * 2015/11/14		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.data;

import com.module.data.DGlobal;
import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.util.logcal.LogicalUtil;
import com.xuezhi_client.net.config.MedicineCompanyConfig;
import com.xuezhi_client.net.config.ProtocalConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class DMedicineCompanyList
{
	private int                         mStatus            = ProtocalConfig.HTTP_OK;
	private ArrayList<DMedicineCompany> mMedicineCompanies = new ArrayList<>();

	private final String NET_ERROR_JSON_SERILIZATION = DGlobal.GetInstance().getAppContext().getString(R.string
																											   .net_error_json_serilization);

	public DMedicineCompanyList()
	{
	}


	public synchronized boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		mMedicineCompanies.clear();

		mStatus = response.getInt(ProtocalConfig.HTTP_STATUS);

		if (!LogicalUtil.IsHttpSuccess(mStatus))
		{
			String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
			throw new JsonSerializationException(errorMsg);
		}

		//03. 序列化json
		JSONArray jsonArray = response.getJSONArray(MedicineCompanyConfig.LIST);
		if (jsonArray == null)
		{
			throw new JsonSerializationException(NET_ERROR_JSON_SERILIZATION + ":" + MedicineCompanyConfig.LIST);
		}

		JSONObject       jsonObject      = null;
		DMedicineCompany medicineCompany = null;
		for (int index = 0; index < jsonArray.length(); index++)
		{
			jsonObject = (JSONObject)jsonArray.get(index);
			medicineCompany = new DMedicineCompany();
			medicineCompany.serialization(jsonObject);

			mMedicineCompanies.add(medicineCompany);
		}
		return true;
	}

	public int getStatus()
	{
		return mStatus;
	}

	public ArrayList<DMedicineCompany> getMedicineCompanies()
	{
		return mMedicineCompanies;
	}

	public DMedicineCompany getMedicineCompanyByID(int id)
	{
		for (DMedicineCompany medicineCompany : mMedicineCompanies)
		{
			if (medicineCompany.getID() == id)
			{
				return medicineCompany;
			}
		}
		return null;
	}

}
