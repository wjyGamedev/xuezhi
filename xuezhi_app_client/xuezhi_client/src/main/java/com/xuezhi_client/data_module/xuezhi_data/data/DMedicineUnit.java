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

import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.net.config.MedicineUnitConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class DMedicineUnit
{
	private int    m_ID   = DataConfig.DEFAULT_VALUE;
	private String m_unitName = null;

	public boolean serialization(JSONObject response) throws JSONException
	{
		m_ID = response.getInt(MedicineUnitConfig.ID);
		m_unitName = response.getString(MedicineUnitConfig.UINT);
		return true;
	}

	public int getID()
	{
		return m_ID;
	}

	public String getUnitName()
	{
		return m_unitName;
	}
}
