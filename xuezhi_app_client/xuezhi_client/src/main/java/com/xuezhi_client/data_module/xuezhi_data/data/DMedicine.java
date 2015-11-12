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
import com.xuezhi_client.net.config.MedicineConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class DMedicine
{
	private int    m_ID          = DataConfig.DEFAULT_VALUE;
	private String m_name        = null;
	private int    m_MUID        = DataConfig.DEFAULT_VALUE;//药品单位ID
	private double m_dose        = 0f;
	private String m_precautions = null;

	public boolean serialization(JSONObject response) throws JSONException
	{
		m_ID = response.getInt(MedicineConfig.MID);
		m_name = response.getString(MedicineConfig.NAME);
		m_MUID = response.getInt(MedicineConfig.UNIT);
		m_dose = response.getDouble(MedicineConfig.DOSE);
		m_precautions = response.getString(MedicineConfig.PRECAUTION);
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

	public double getDose()
	{
		return m_dose;
	}

	public String getPrecautions()
	{
		return m_precautions;
	}
}
