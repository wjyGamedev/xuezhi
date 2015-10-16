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

	//02. 血脂类别
	public enum AssayDetectionType
	{
		//0:所有类型
		LIPID_BIOCHEMICAL_ALL(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_biochemical_all), 0),

		//血脂类型
		LIPID_ALL(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_all), 1),
		TG(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_type_tg), 2),
		TCHO(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_type_tcho), 3),
		LOLC(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_type_lolc), 4),
		HDLC(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_type_hdlc), 5),

		//生化类型
		BIOCHEMICAL_ALL(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_all), 6),
		ATL(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_atl), 7),
		AST(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_ast), 8),
		CK(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_ck), 9),
		GLU(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_glu), 10),
		HBA1C(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_hba1c), 11),
		SCR(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_scr), 12);

		private String m_name = null;
		private int    m_id   = 1;

		private AssayDetectionType(String name, int id)
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

		public static AssayDetectionType valueOf(int id)
		{
			switch (id)
			{
			//01. 血脂生化
			case 0:
				return LIPID_BIOCHEMICAL_ALL;
			//生化
			case 1:
				return LIPID_ALL;
			case 2:
				return TG;
			case 3:
				return TCHO;
			case 4:
				return LOLC;
			case 5:
				return HDLC;
			//血脂
			case 6:
				return BIOCHEMICAL_ALL;
			case 7:
				return ATL;
			case 8:
				return AST;
			case 9:
				return CK;
			case 10:
				return GLU;
			case 11:
				return HBA1C;
			case 12:
				return SCR;

			default:
				return LIPID_BIOCHEMICAL_ALL;
			}
		}
	}




}
