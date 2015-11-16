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

import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.net.config.MedicineCompanyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class DMedicineCompany
{
	private int mID = DataConfig.DEFAULT_VALUE;        //库存ID
	private String mName = "";

	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		mID = response.getInt(MedicineCompanyConfig.ID);
		mName = response.getString(MedicineCompanyConfig.NAME);
		return true;
	}

	public int getID()
	{
		return mID;
	}

	public String getName()
	{
		return mName;
	}
}
