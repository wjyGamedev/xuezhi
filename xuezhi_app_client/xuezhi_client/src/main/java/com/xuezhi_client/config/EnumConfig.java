/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/28		WangJY		1.0.0		create
 */

package com.xuezhi_client.config;

import com.module.data.DGlobal;
import com.xuzhi_client.xuzhi_app_client.R;

public class EnumConfig
{
	//01. 药品单位：片/毫克
	public enum MedicalUnit
	{
		SHEET(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.medical_unit_sheet), 1),
		MILLIGRAM(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.medical_unit_milligram), 2);

		private String m_name = null;
		private int    m_id   = 1;

		private MedicalUnit(String name, int id)
		{
			m_name = name;
			m_id = id;
		}

		public String getName()
		{
			return m_name;
		}

		public int getId()
		{
			return m_id;
		}

		@Override
		public String toString()
		{
			return (m_name + ":" + m_id);
		}

		public static MedicalUnit valueOf(int id)
		{
			switch (id)
			{
			case 1:
				return SHEET;
			case 2:
				return MILLIGRAM;
			default:
				return null;
			}
		}
	}

}
