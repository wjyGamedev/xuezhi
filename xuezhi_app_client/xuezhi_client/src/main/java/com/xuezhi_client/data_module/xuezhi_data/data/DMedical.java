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

import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.net.config.MedicalListConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class DMedical
{
	private int    m_ID   = DataConfig.DEFAULT_VALUE;
	private String m_name = null;
	private int m_MUID = DataConfig.DEFAULT_VALUE;//药品单位ID
	public boolean serialization(JSONObject response) throws JSONException
	{
		m_ID = response.getInt(MedicalListConfig.MID);
		m_name = response.getString(MedicalListConfig.NAME);
		m_MUID = response.getInt(MedicalListConfig.UNIT);
		return true;
	}

	public int getID()
	{
		return m_ID;
	}

	public String getName()
	{
		return m_name;
	}

	public int getMUID()
	{
		return m_MUID;
	}
}
