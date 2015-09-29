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
import com.xuezhi_client.config.EnumConfig;
import com.xuezhi_client.net.config.MedicalListConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class DMedical
{
	private int    m_ID   = DataConfig.DEFAULT_VALUE;
	private String m_name = null;
	//TODO:等待添加
	private EnumConfig.MedicalUnit m_medicalUnit = EnumConfig.MedicalUnit.MILLIGRAM;    //药品单位

	public boolean serialization(JSONObject response) throws JSONException
	{
		m_ID = response.getInt(MedicalListConfig.ID);
		m_name = response.getString(MedicalListConfig.NAME);
		return true;
	}

	public int getID()
	{
		return m_ID;
	}

	public void setID(int ID)
	{
		m_ID = ID;
	}

	public String getName()
	{
		return m_name;
	}

	public void setName(String name)
	{
		m_name = name;
	}
}
